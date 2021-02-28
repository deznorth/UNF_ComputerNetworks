package handlers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTimeHandler implements RequestHandler {
  public String resolve() {
    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
    Calendar calendar = Calendar.getInstance();
    return "The current server date and time is: " + dateFormat.format(calendar.getTime());
  }
}
