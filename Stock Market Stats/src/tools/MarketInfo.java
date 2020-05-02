package tools;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;

import io.SpreadSheetReader;

public class MarketInfo
{
  private SpreadSheetReader reader;
  private HashMap<String, Double> percentDifferentialMap;
  private HashMap<String, Double> pointDifferentialMap;
  private Calendar mostRecentDate;

  public MarketInfo(String fileInputPath, String fileOutputPath)
  {
    percentDifferentialMap = new HashMap<String, Double>();
    pointDifferentialMap = new HashMap<String, Double>();
    mostRecentDate = Calendar.getInstance();
    reader = new SpreadSheetReader(fileInputPath, fileOutputPath);
    reader.writeRawDataToFile();
    reader.readOutputSheet();
    setMostRecentDate(getMostRecentStringDate());
  }

  /**
   * Gets the map of the dates and the percent changes.
   * 
   * @return The map
   */
  public HashMap<String, Double> getPercentDifferentialMap()
  {
    return percentDifferentialMap;
  }

  /**
   * Gets the map of the dates and the point changes.
   * 
   * @return The map
   */
  public HashMap<String, Double> getPointDifferentialMap()
  {
    return pointDifferentialMap;
  }

  /**
   * Gets the map of dates and the open and close values.
   * 
   * @return The map
   */
  public LinkedHashMap<String, Pair<Double, Double>> getMap()
  {
    return reader.getMap();
  }

  public String getMostRecentStringDate()
  {
    return reader.getMostRecentDate();
  }

  public Calendar getMostRecentCalendarDate()
  {
    return mostRecentDate;
  }

  private void setMostRecentDate(String firstDate)
  {
    String[] values = firstDate.split("-");
    int year = Integer.parseInt(values[0]);
    int month = Integer.parseInt(values[1]);
    int day = Integer.parseInt(values[2]);

    mostRecentDate.set(year, month, day);
  }

}
