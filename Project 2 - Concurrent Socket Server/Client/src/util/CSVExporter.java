package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CSVExporter {

  public void export(ArrayList<String> data, String fileName) {
    try (
      PrintWriter writer = new PrintWriter(new File(fileName + ".csv"))
    ) {
      StringBuilder sb = new StringBuilder();

      for (int row = 0; row < data.size(); row++) {
          String escapedValue = String.format("\"%s\"%n", data.get(row));
          sb.append(escapedValue);
      }

      writer.write(sb.toString());
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }
  }
}