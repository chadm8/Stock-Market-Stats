package tools;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import calculating.ValueChangeCalculator;
import io.SpreadSheetReader;

/**
 * A class to hold the information from the data read from yahoo finance.
 * 
 * @author Chad Martin
 * @version 11 May 2020
 *
 */
public class MarketInfo
{
  private SpreadSheetReader reader;
  private HashMap<String, Double> percentDifferentialMap;
  private HashMap<String, Double> pointDifferentialMap;
  private ArrayList<Map.Entry<String, Double>> sortedPercentList;
  private ArrayList<Map.Entry<String, Double>> sortedPointList;
  private Calendar mostRecentDate;

  /**
   * The constructor.
   * 
   * @param fileInputPath
   *          The file input path
   * @param fileOutputPath
   *          The file output path
   */
  public MarketInfo(String fileInputPath, String fileOutputPath)
  {
    percentDifferentialMap = new HashMap<>();
    pointDifferentialMap = new HashMap<>();
    sortedPercentList = new ArrayList<>();
    sortedPointList = new ArrayList<>();
    reader = new SpreadSheetReader(fileInputPath, fileOutputPath);
    mostRecentDate = Calendar.getInstance();

    reader.writeRawDataToFile();
    reader.readOutputSheet();
    setMostRecentDate(getMostRecentStringDate());
  }

  /**
   * Calculates and sorts the values in a certain day span.
   * 
   * @param daySpan
   *          The day span
   */
  public void calculateDateDifferentialValues(int daySpan)
  {
    ValueChangeCalculator.addDateDifferentialChanges(this, daySpan);
    sortMap(percentDifferentialMap, sortedPercentList);
    sortMap(pointDifferentialMap, sortedPointList);
  }

  /**
   * Clears the maps.
   */
  public void clearDifferentialMaps()
  {
    percentDifferentialMap.clear();
    pointDifferentialMap.clear();
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
   * Gets the largest percentage increases in sorted form.
   * 
   * @param amountOfValues
   *          The number of date differential values wanted
   * @return The String form of the values in sorted form
   */
  public String getLargestPercentageIncreases(int amountOfValues)
  {
    return "Largest Percent Increases\n\n"
        + getLargestIncreasesHelper(amountOfValues, sortedPercentList);
  }

  /**
   * Gets the largest percentage decreases in sorted form.
   * 
   * @param amountOfValues
   *          The number of date differential values wanted
   * @return The String form of the values in sorted form
   */
  public String getLargestPercentageDecreases(int amountOfValues)
  {
    return "Largest Percent Decreases\n\n"
        + getLargestDecreasesHelper(amountOfValues, sortedPercentList);
  }

  /**
   * Gets the largest point increases in sorted form.
   * 
   * @param amountOfValues
   *          The number of date differential values wanted
   * @return The String form of the values in sorted form
   */
  public String getLargestPointIncreases(int amountOfValues)
  {
    return "Largest Point Increases\n\n"
        + getLargestIncreasesHelper(amountOfValues, sortedPointList);
  }

  /**
   * Gets the largest point decreases in sorted form.
   * 
   * @param amountOfValues
   *          The number of date differential values wanted
   * @return The String form of the values in sorted form
   */
  public String getLargestPointDecreases(int amountOfValues)
  {
    return "Largest Point Decreases\n\n"
        + getLargestDecreasesHelper(amountOfValues, sortedPointList);
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

  /**
   * Gets the most recent date.
   * 
   * @return The most recent date
   */
  public String getMostRecentStringDate()
  {
    return reader.getMostRecentDate();
  }

  /**
   * Gets the most recent Calendar date.
   * 
   * @return The most recent Calendar date
   */
  public Calendar getMostRecentCalendarDate()
  {
    return mostRecentDate;
  }

  /**
   * A helper method to set the most recent Calendar date.
   * 
   * @param firstDate
   *          The most recent date in String form
   */
  private void setMostRecentDate(String firstDate)
  {
    String[] values = firstDate.split("-");
    int year = Integer.parseInt(values[0]);
    int month = Integer.parseInt(values[1]);
    int day = Integer.parseInt(values[2]);

    mostRecentDate.set(year, month, day);
  }

  /**
   * Sorts a given HashMap into an ArrayList of entries.
   * 
   * @param differentialMap
   *          The date differential value map
   * @param sortedList
   *          The ArrayList to store the sorted values
   */
  private void sortMap(HashMap<String, Double> differentialMap,
      ArrayList<Map.Entry<String, Double>> sortedList)
  {
    differentialMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
        .forEach(entry -> sortedList.add(entry));
  }

  /**
   * A helper method to get a given amount of the largest increases in values.
   * 
   * @param amountOfValues
   *          The amount of values wanted
   * @param list
   *          The sorted ArrayList
   * @return A String of the largest increases
   */
  private String getLargestIncreasesHelper(int amountOfValues,
      ArrayList<Map.Entry<String, Double>> list)
  {
    String result = "";
    int lastIndex = list.size() - 1;
    int amOfVals = amountOfValues;
    int counter = 1;

    for (int i = lastIndex; i > -1 && amOfVals != 0; i--)
    {
      result += String.format(counter + ". " + list.get(i).getKey() + ": %.2f\n",
          list.get(i).getValue());
      amOfVals--;
      counter++;
    }

    return result;
  }

  /**
   * A helper method to get a given amount of the largest decreases in values.
   * 
   * @param amountOfValues
   *          The amount of values wanted
   * @param list
   *          The sorted ArrayList
   * @return A String of the largest decreases
   */
  private String getLargestDecreasesHelper(int amountOfValues,
      ArrayList<Map.Entry<String, Double>> list)
  {
    String result = "";
    int amOfVals = amountOfValues;
    int counter = 1;

    for (int i = 0; i < list.size() && amOfVals != 0; i++)
    {
      result += String.format(counter + ". " + list.get(i).getKey() + ": %1.2f\n",
          list.get(i).getValue());
      amOfVals--;
      counter++;
    }

    return result;
  }

}
