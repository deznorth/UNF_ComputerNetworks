package handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UptimeHandler implements RequestHandler {
  public String resolve() {
    try {
      Process uptimeProcess = Runtime.getRuntime().exec("uptime");
      BufferedReader reader = new BufferedReader(new InputStreamReader(uptimeProcess.getInputStream()));

      String uptimeResponse = "", line;

      while ((line = reader.readLine()) != null) {
        uptimeResponse += line + "%n";
      }

      return uptimeResponse;
    } catch (IOException e) {
      System.err.println("Exception caught in UptimeHandler");
      System.err.println(e.getMessage());
      return "An unexpected error has ocurred";
    }
  }
}
