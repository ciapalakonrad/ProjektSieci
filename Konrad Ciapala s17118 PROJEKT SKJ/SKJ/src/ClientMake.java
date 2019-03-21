import java.io.*;
import java.net.Socket;

public class ClientMake extends ClientSiteRun {

    //method that allows to deal with requests and responses

    protected static void make(String command) {

        String[] tab3 = command.split("\t");
        int senderPort = Integer.parseInt(tab3[1]);

        //spilting tab elements with regex
        tab3 = tab3[0].split(" ");

        String instruction = tab3[0];
        try {

            switch (instruction) {

                case "GET_RESPONSE":
                    for (int i = 1; i < tab3.length; i++) {
                        System.out.print(tab3[i] + " ");
                    }

                    break;

                case "FILE":
                    nameOfClient = tab3[1];
                    fileOn = true;
                    break;

                case "FILE_REQUEST":

                    Thread.sleep(1000);

                    //providing the path to file

                        File file = new File("D:\\TORrent_" + hearingPortNumber + "\\" + tab3[1]);

                    //import
                    FileInputStream fileInputStream = new FileInputStream(file);
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                    Socket socket = new Socket("localhost", senderPort);

                    //export
                    OutputStream outputStream = socket.getOutputStream();

                    byte[] sendingBuffor2;
                    long size2 = file.length();
                    long index2 = 0;

                    //running the loop that goes through the indexes
                    whileLoop(bufferedInputStream, outputStream, size2, index2);
                    outputStream.flush();

                    socket.close();

                    break;
            }
        } catch (Exception ex3) {
        }
    }

    static void whileLoop(BufferedInputStream bufferedInputStream, OutputStream outputStream, long size2, long index2) throws IOException {

        byte[] sendingBuffor2;

        //going through the loop
        while (index2 != size2) {
            int size = 6896;
            if (size2 - index2 >= size)
                index2 = index2 + size;
            else {
                size = (int) (size2 - index2);
                index2 = size2;
            }

            //writing and reading from sendingBuffor
            sendingBuffor2 = new byte[size];
            bufferedInputStream.read(sendingBuffor2, 0, size);
            outputStream.write(sendingBuffor2);
        }
    }
}
