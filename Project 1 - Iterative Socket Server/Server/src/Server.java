import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import entities.RequestType;
import handlers.*;

public class Server {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: Java Server <port number>");
            System.exit(1);
        }

        // Variables
        String PROJECT_NAME = "Project 1 - Iterative Socket Server";
        int PORT = Integer.parseInt(args[0]);

        // This Map holds all request handlers keyed by an enum so it's easy to add new handlers
        HashMap<RequestType, RequestHandler> handlers = new HashMap<RequestType, RequestHandler> (Map.of(
            RequestType.DateTime, new DateTimeHandler()
        )) ;

        // Greeting
        System.out.printf("%s %n%nGroup:\tFernando Jimenez Mendez %n\tDavid Rojas Gonzalez %n%n", PROJECT_NAME);

        try {
            // Create listener bound to PORT with a max # of connections of 100
            ServerSocket socket = new ServerSocket(PORT, 100);
            System.out.println("Type CTRL+C to stop the server");

            // Infinite loop
            while (true) {
                try (
                    Socket client = socket.accept();
                    PrintWriter output = new PrintWriter(client.getOutputStream(), true);
                    BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                ) {
                    System.out.println("Connection established from: " + client.getInetAddress());
                    RequestType requestType = RequestType.valueOf(input.readLine());

                    if (requestType == RequestType.Quit) {
                        break;
                    }

                    for (RequestType type : handlers.keySet()) {
                        if (type == requestType) {
                            String response = handlers.get(type).resolve() + "%n%n";
                            // Print to local console
                            System.out.printf(response);
                            // Submit to client
                            output.printf(response);
                        }
                    }

                    input.close();
                    output.close();
                    client.close();
                } catch (IOException e) {
                    System.err.println("Exception caught while handling a connection");
                    System.out.println(e.getMessage());
                }
                System.out.println("Waiting for next connection...");
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
