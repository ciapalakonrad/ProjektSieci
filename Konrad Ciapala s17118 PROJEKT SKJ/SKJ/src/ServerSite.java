import java.io.*;
import java.net.*;
import java.util.*;

public class ServerSite {

    static HashMap<String, ArrayList<FilesClass>> torrent = new HashMap<String, ArrayList<FilesClass>>();
    static int hearingPort;

    //initializing deafult construtor
    public ServerSite() {
        //start thread
        serverThread.start();
    }

    public static void main(String[] args) {

        //setting the hearingPort
        hearingPort = Integer.parseInt(args[0]);

        //running the constructor
        new ServerSite();
    }

    Thread serverThread = new Thread(new Runnable() {

        @Override
        public void run() {
            try {

                ServerSocket serverSocket = new ServerSocket(hearingPort);

                //accepting the sockets
                while (true) {
                    Socket socket = serverSocket.accept();

                    //getting the input
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String command = "";
                    String line = "";

                    //going through the commands
                    while ((line = bufferedReader.readLine()) != null)
                        command = command + line;

                    //initializing the method form class ServerMake
                    ServerMake.make(command);

                }
            }
            catch (Exception ex5) {}
        }
    });
}
