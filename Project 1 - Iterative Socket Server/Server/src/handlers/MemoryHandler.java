package handlers;

public class MemoryHandler implements RequestHandler {
  double conversionRate = 1000000.0;
  public String resolve() {
    String response = "";
    Runtime memory = Runtime.getRuntime();
    response += String.format("%nTotal memory: \t%s MB", memory.maxMemory() * conversionRate);
    response += String.format("%nFree memory: \t%s MB", memory.freeMemory() * conversionRate);
    response += String.format("%nMemory in use: \t%s MB", (memory.maxMemory() - memory.freeMemory()) * conversionRate);
    return response;
  }
}
