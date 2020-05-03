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

public class MarketInfo
{
  private SpreadSheetReader reader;
  private HashMap<String, Double> percentDifferentialMap;
  private HashMap<String, Double> pointDifferentialMap;
  private ArrayList<Map.Entry<String, Double>> sortedPercentList;
  private ArrayList<Map.Entry<String, Double>> sortedPointList;
  private Calendar mostRecentDate;

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

  public void calculateDateDifferentialValues(int daySpan)
  {
    ValueChangeCalculator.addDateDifferentialChanges(this, daySpan);
    sortMap(percentDifferentialMap, sortedPercentList);
    sortMap(pointDifferentialMap, sortedPointList);
  }

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

  public String getLargestPercentageIncreases(int amountOfValues)
  {
    return "Largest Percent Increases\n\n"
        + getLargestIncreasesHelper(amountOfValues, sortedPercentList);
  }

  public String getLargestPercentageDecreases(int amountOfValues)
  {
    return "Largest Percent Decreases\n\n"
        + getLargestDecreasesHelper(amountOfValues, sortedPercentList);
  }

  public String getLargestPointIncreases(int amountOfValues)
  {
    return "Largest Point Increases\n\n"
        + getLargestIncreasesHelper(amountOfValues, sortedPointList);
  }

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

  private void sortMap(HashMap<String, Double> differentialMap,
      ArrayList<Map.Entry<String, Double>> sortedMap)
  {
    differentialMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
        .forEach(entry -> sortedMap.add(entry));
  }

  private String getLargestIncreasesHelper(int amountOfValues,
      ArrayList<Map.Entry<String, Double>> list)
  {
    String result = "";
    int lastIndex = list.size() - 1;
    int amOfVals = amountOfValues;
    int counter = 1;

    for (int i = lastIndex; i > -1 && amOfVals != 0; i--)
    {
      result += String.format(counter + ". " + list.get(i).getKey() + ": %.2f\n", list.get(i).getValue());
      amOfVals--;
      counter++;
    }

    return result;
  }

  private String getLargestDecreasesHelper(int amountOfValues,
      ArrayList<Map.Entry<String, Double>> list)
  {
    String result = "";
    int amOfVals = amountOfValues;
    int counter = 1;

    for (int i = 0; i < list.size() && amOfVals != 0; i++)
    {
      result += String.format(counter + ". " + list.get(i).getKey() + ": %1.2f\n", list.get(i).getValue());
      amOfVals--;
      counter++;
    }

    return result;
  }

}
