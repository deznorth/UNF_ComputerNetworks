package handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunningProcessesHandler implements RequestHandler {
  public String resolve() {
    String response = "%n";

    try {
      Process process = Runtime.getRuntime().exec("ps -aux");
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String run;
      while((run = reader.readLine()) != null) {
				response += run + "%n";
			}
      return response;
    } catch (IOException e) {
      return "'ps' is not a recognized command.";
    }
  }
}
