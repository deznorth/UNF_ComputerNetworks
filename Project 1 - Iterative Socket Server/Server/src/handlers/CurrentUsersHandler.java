package handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CurrentUsersHandler implements RequestHandler {
  public String resolve() {
    String response = "%n";

    try {
      Process process = Runtime.getRuntime().exec("who");
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String users;
      while((users = reader.readLine()) != null) {
				response += users + "%n";
			}
      return response;
    } catch (IOException e) {
      return "'Who' is not a recognized command.";
    }
  }
}
