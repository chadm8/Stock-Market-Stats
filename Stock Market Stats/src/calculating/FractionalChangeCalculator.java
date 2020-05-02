package calculating;

import java.util.HashMap;
import java.util.LinkedHashMap;

import io.SpreadSheetReader;
import tools.Pair;

/**
 * A class to compute changes in the market value over a given set of time.
 * 
 * @author Chad Martin
 * @version 2 May 2020
 *
 */
public class FractionalChangeCalculator
{

  /**
   * Computes the percentage change over the time span given throughout the historical data.
   * 
   * @param valuesMap
   *          The map with open and close dates
   * @param resultMap
   *          The map to insert the percentage change
   * @param timeSpan
   *          The amount of time to compare
   */
  public static void addPercentChanges(LinkedHashMap<String, Pair<Double, Double>> valuesMap,
      HashMap<String, Double> resultMap, int timeSpan)
  {
    for (String key : SpreadSheetReader.getMap().keySet())
    {
      String date1 = key;
      String date2 = findAvailableDay(timeSpan, key);
      Double val = valuesMap.get(date1).getOpen();
      Double val2 = valuesMap.get(date2).getClose();
    }
  }

  /**
   * Computes the point change over the time span given throughout the historical data.
   * 
   * @param valuesMap
   *          The map with open and close dates
   * @param resultMap
   *          The map to insert the percentage change
   * @param timeSpan
   *          The amount of time to compare
   */
  public static void addPointChanges(LinkedHashMap<String, Pair<Double, Double>> valuesMap,
      HashMap<String, Double> resultMap, int timeSpan)
  {

  }

  /**
   * A helper method to find the next available day the market is open.
   * 
   * @param timeSpan
   *          The amount of days after the day to compare
   * @param dayToCompare
   *          The first day
   * @return The next available day from the day to compare
   */
  private static String findAvailableDay(int daySpan, String dayToCompare)
  {
    if (daySpan == 1)
      return dayToCompare;

    String[] date = dayToCompare.split("/");
    int month = Integer.parseInt(date[0]);
    int day = Integer.parseInt(date[1]);
    int year = Integer.parseInt(date[2]);

    if (month + (daySpan / 30) > 12)
    {
      year += 1;
    }

    year += daySpan / 365;
    month += (daySpan / 30) % 12;
    day += daySpan % 30;

    return "" + month + "/" + day + "/" + year;
  }

}
