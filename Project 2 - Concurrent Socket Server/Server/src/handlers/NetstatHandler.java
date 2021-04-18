package handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NetstatHandler implements RequestHandler {
  public String resolve() {
    String response = "%n";
    try {
      Process netstat = Runtime.getRuntime().exec("netstat -a");
      BufferedReader netreader = new BufferedReader(new InputStreamReader(netstat.getInputStream()));
      String line;
      while((line = netreader.readLine()) != null) {
				response += line + "%n";
			}
      return response;
    } catch (IOException e) {
      return "Netstat is not available.";
    }
  }
}
