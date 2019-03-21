import java.io.*;
import java.net.*;
import java.security.*;


public class ClientSiteRun {

    static boolean fileOn = false;
    static String nameOfClient;
    static int hearingPortNumber, serverPortNumber = 5000;

    //handling with the threads
    public ClientSiteRun() {

        clientThread.start();
        steeringThread.start();
    }

    public static void main(String[] args) {
        System.out.println("Write the following commands: GET --> PULL/PUSH/EXIT ");
        hearingPortNumber = Integer.parseInt(args[0]);

        //intializing constructor
        new ClientSiteRun();

        //listing the files in folder
        list();
    }

    //listing method
   private static void list() {
        try {
            String filesList = "LIST";

            //setting up new socket
                Socket socket = new Socket("localhost", serverPortNumber);

            //getting output stream
            PrintStream printStream = new PrintStream(socket.getOutputStream());

            //providing the path to the folder with files
                File folder = new File("D:\\TORrent_" + hearingPortNumber);
                File[] listOfFiles = folder.listFiles();

                //going through the files
            for (File f : listOfFiles) {

                //getting message
                String message = "";
                MessageDigest messageDigest = MessageDigest.getInstance("MD5");


                try (DigestInputStream digestInputStream = new DigestInputStream(new FileInputStream(f),
                        messageDigest)) {

                    //condition
                    while (digestInputStream.read() != -1)
                        ;
                    messageDigest = digestInputStream.getMessageDigest();
                }

                //going through the messages and formating console output
                for (byte b : messageDigest.digest()) {
                    message = message + String.format("%02x", b);
                }

                filesList = filesList + " " + f.getName() + " " + message + "\n";
            }

            // closing printstream and sockets
                    printStream.println(filesList + "\t" + hearingPortNumber);
                    printStream.flush();
                    printStream.close();
                    socket.close();

        }
        catch (Exception ex) {}
    }

    Thread clientThread = new Thread(new Runnable() {

        @Override
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(hearingPortNumber);
                while (true) {
                    Socket socket = serverSocket.accept();
                    if (fileOn) {

                        byte[] buffor = new byte[6896];
                        InputStream inputStream = socket.getInputStream();
                        BufferedOutputStream outputBuffor = new BufferedOutputStream(

                                //providing file path
                                new FileOutputStream("D:\\TORrent_" + hearingPortNumber + "\\" + nameOfClient));

                        int bytesRead = 0;
                        while ((bytesRead = inputStream.read(buffor)) != -1) {
                            outputBuffor.write(buffor, 0, bytesRead);
                        };

                        //closing the sockets

                            outputBuffor.flush();
                            outputBuffor.close();


                                socket.close();
                                fileOn = false;

                        //console comunicate
                        System.out.println("You've just downloaded the file " + nameOfClient);

                    }
                    else {
                        BufferedReader bufferedReader = new BufferedReader(
                                new InputStreamReader(socket.getInputStream()));
                        String cmd = bufferedReader.readLine();
                        ClientMake.make(cmd);

                    }

                }
            }
            catch (Exception ex2) {}
        }
    });


    Thread steeringThread = new Thread(new Runnable() {

        @Override
        public void run() {
            try {
                //allowing to write commands to console
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

                while (true) {

                    //handling the commands from ClientCommads clasd
                    String command = bufferedReader.readLine();
                    ClientCommands.commandMaking(command);

                }
            }
            catch (Exception exception) {}
        }
    });


}