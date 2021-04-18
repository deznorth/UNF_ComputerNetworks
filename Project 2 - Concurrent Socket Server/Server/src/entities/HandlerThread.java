package entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import handlers.*;

public class HandlerThread extends Thread {
  private Socket client;
  private BufferedReader input;
  private RequestType requestType;

  // This Map holds all request handlers keyed by an enum so it's easy to add new handlers
  private HashMap<RequestType, RequestHandler> handlers = new HashMap<RequestType, RequestHandler> (Map.of(
      RequestType.DateTime, new DateTimeHandler(),
      RequestType.Uptime, new UptimeHandler(),
      RequestType.Memory, new MemoryHandler(),
      RequestType.Netstat, new NetstatHandler(),
      RequestType.CurrentUsers, new CurrentUsersHandler(),
      RequestType.RunningProcesses, new RunningProcessesHandler()
  )) ;

  public HandlerThread(Socket client, BufferedReader input, RequestType requestType) {
    this.client = client;
    this.input = input;
    this.requestType = requestType;
  }

  public void run() {
    try (
        PrintWriter output = new PrintWriter(client.getOutputStream(), true);
    ) {
        System.out.printf("%nHandling incoming '%s' request from [%s].%n", requestType.name(), client.getInetAddress());

        // Iterate the handlers HashMap and execute corresponding handler
        for (RequestType type : handlers.keySet()) {
            if (type == requestType) {
                String response = handlers.get(type).resolve() + "%n%n";
                // Submit to client
                output.printf(response);
            }
        }

        input.close();
        output.close();
    } catch (IOException e) {
        System.err.println("Exception caught while handling a connection");
        System.out.println(e.getMessage());
    }
  }
}
