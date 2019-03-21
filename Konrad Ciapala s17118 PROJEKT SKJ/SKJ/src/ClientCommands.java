import java.io.*;
import java.net.Socket;

public class ClientCommands extends ClientSiteRun {

    //allows to write the commands in client's console that are listened by serwer

    protected static void commandMaking(String command) {
        System.out.println(command);

        //spliting the command with regex
        String[] tab = command.split(" ");

        String tab2 = tab[0];

        //switching beetween commands
        try {
            switch (tab2) {

                //getting the list of files
                case "GET":
                    int serverPort = Integer.parseInt(tab[1]);

                    Socket socket = new Socket("localhost", serverPort);
                    PrintStream printStream = new PrintStream(socket.getOutputStream());
                    printStream.println(tab2 + "\t" + hearingPortNumber);

                    //closing printstream with socket
                            printStream.flush();
                            printStream.close();
                            socket.close();

                    break;

                //pulling the file from another client
                case "PULL":
                    int sendPort = Integer.parseInt(tab[1]);

                    Socket socket2 = new Socket("localhost", serverPortNumber);
                    PrintStream printStream2 = new PrintStream(socket2.getOutputStream());
                    printStream2.println(tab2 + " " + sendPort + " " + tab[2] + "\t" + hearingPortNumber);

                    //closing printstream with socket
                            printStream2.flush();
                            printStream2.close();
                            socket2.close();
                            nameOfClient = tab[2];
                            fileOn = true;

                    break;

                //sendind the file to another client
                case "PUSH":
                    int sendPort2 = Integer.parseInt(tab[1]);
                    Socket socket3 = new Socket("localhost", serverPortNumber);

                    //getting the output
                    PrintStream printStream3 = new PrintStream(socket3.getOutputStream());
                    printStream3.println(tab2 + " " + sendPort2 + " " + tab[2] + "\t" + hearingPortNumber);

                    //closing printstream with sockets
                            printStream3.flush();
                            printStream3.close();
                            socket3.close();

                            //sleeping the thread for 1.5s
                            Thread.sleep(1500);

                    //providing path to the files
                    File file = new File("D:\\TORrent_" + hearingPortNumber + "\\" + tab[2]);

                    //import
                    FileInputStream fileInputStream = new FileInputStream(file);
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                    Socket socket5 = new Socket("localhost", sendPort2);

                    //export
                    OutputStream outputStream = socket5.getOutputStream();

                    byte[] sendingBuffor;
                    long size = file.length();
                    long index = 0;

                    //start to go trough the indexes
                    ClientMake.whileLoop(bufferedInputStream, outputStream, size, index);

                            //closing stream and socket
                            outputStream.flush();
                            socket5.close();
                            nameOfClient = tab[2];

                    //console comunicate
                    System.out.println("The file is sent: " + nameOfClient);

            }
        }
        catch (Exception exception) {}
    }
}
