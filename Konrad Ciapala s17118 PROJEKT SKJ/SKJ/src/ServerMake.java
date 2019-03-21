import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerMake extends ServerSite{
    protected static void make(String command) {

        //spliting the tab with regex
        String[] tab4 = command.split("\t");

        //setting the senderPort
        int senderPort = Integer.parseInt(tab4[1]);

        //spliting tab with regex
        tab4 = tab4[0].split(" ");

        //switching between instructions
        String instruction2 = tab4[0];
        try {

            switch (instruction2) {

                //list the files from the folder
                case "LIST":

                        //removing senderPort form HashMap
                        torrent.remove(senderPort);

                    //making new ArrayList
                    ArrayList<FilesClass> list2 = new ArrayList<FilesClass>();

                    //going through tab4(comands)
                    for (int i = 1; i < tab4.length; i++) {
                        FilesClass filesClass = new FilesClass(tab4[i], tab4[++i]);
                        list2.add(filesClass);
                    }

                        //putting new sender port to HashMap (ServerSite)
                        torrent.put(senderPort + "", list2);

                    System.out.println(torrent);
                    break;

                    //getting the list of files
                case "GET":

                    //setting up new socket
                        Socket socket = new Socket("localhost", senderPort);

                    //getting output
                    PrintStream printStream = new PrintStream(socket.getOutputStream());
                    printStream.println("GET_RESPONSE" + " " + torrent + "\t" + hearingPort);

                    //closing the socket and stream
                            printStream.flush();
                            printStream.close();
                            socket.close();

                    break;

                    //downloading the file from another client
                case "PULL":
                    //console comunicate
                    System.out.println("ClientSiteRun " + senderPort + " is downloading the file: " + tab4[2] + " from " + tab4[1]);

                    //setting up new socket
                        Socket socket2 = new Socket("localhost", Integer.parseInt(tab4[1]));

                    //getting output
                    PrintStream printStream2 = new PrintStream(socket2.getOutputStream());
                    printStream2.println("FILE_REQUEST" + " " + tab4[2] + "\t" + senderPort);

                    //closing the socket and stream
                            printStream2.flush();
                            printStream2.close();
                            socket2.close();
                    break;

                    //sending the file to another client
                case "PUSH":

                    //console comunicate
                    System.out.println("ClientSiteRun " + senderPort + " is sending: " + tab4[2] + " to " + tab4[1]);

                    //setting up new socket
                        Socket socket4 = new Socket("localhost", Integer.parseInt(tab4[1]));

                    //getting output
                    PrintStream printStream4 = new PrintStream(socket4.getOutputStream());
                    printStream4.println("FILE" + " " + tab4[2] + "\t" + senderPort);

                    //closing the socket and stream
                            printStream4.flush();
                            printStream4.close();
                            socket4.close();
                    break;
            }

        } catch (Exception exception) {
        }
    }
}
