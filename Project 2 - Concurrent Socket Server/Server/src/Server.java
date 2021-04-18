import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import entities.HandlerThread;
import entities.RequestType;

public class Server {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: Java Server <port number>");
            System.exit(1);
        }

        // Variables
        String PROJECT_NAME = "Project 2 - Concurrent Socket Server";
        int PORT = Integer.parseInt(args[0]);

        // Greeting
        System.out.printf("%s %n%nGroup:\tFernando Jimenez Mendez %n\tDavid Rojas Gonzalez %n%n", PROJECT_NAME);

        try {
            // Create listener bound to PORT with a max # of connections of 100
            ServerSocket socket = new ServerSocket(PORT, 100);
            System.out.println("Type CTRL+C to stop the server");

            // Infinite loop
            while (true) {
                try {
                    Socket client = socket.accept();
                    BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    RequestType requestType = RequestType.valueOf(input.readLine());

                    if (requestType == RequestType.Quit) {
                        break;
                    }

                    new HandlerThread(client, input, requestType).start();
                } catch (IOException e) {
                    System.err.println("Exception caught while starting a handler thread a connection");
                    System.out.println(e.getMessage());
                }
            }

            // Exit gracefully
            socket.close();
            System.exit(0);
        } catch (IOException e) {
            System.err.println("Exception caught while trying to listen on port " + PORT);
            System.err.println(e.getMessage());
        }
    }
}
