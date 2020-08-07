package tools;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import calculating.CalendarCalculator;
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
  public MarketInfo(String fileInputPath)
  {
    percentDifferentialMap = new HashMap<>();
    pointDifferentialMap = new HashMap<>();
    sortedPercentList = new ArrayList<>();
    sortedPointList = new ArrayList<>();
    reader = new SpreadSheetReader(fileInputPath);
    mostRecentDate = Calendar.getInstance();

    reader.readRawDataAndAssignValues();
    CalendarCalculator.makeCalendarDate(getMostRecentStringDate());
  }

  /**
   * Calculates and sorts the values in a certain day span.
   * 
   * @param daySpan
   *          The day span
   */
  public void calculateDateDifferentialValues(int daySpan, boolean isCalendarDays)
  {
    ValueChangeCalculator.addDateDifferentialChanges(this, daySpan, isCalendarDays);
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
  public ArrayList<Map.Entry<String, Double>> getLargestPercentageIncreases(int amountOfValues,
      boolean allowDayOverlaps)
  {
    return getLargestIncreasesHelper(amountOfValues, sortedPercentList, allowDayOverlaps);
  }

  /**
   * Gets the largest percentage decreases in sorted form.
   * 
   * @param amountOfValues
   *          The number of date differential values wanted
   * @return The String form of the values in sorted form
   */
  public ArrayList<Map.Entry<String, Double>> getLargestPercentageDecreases(int amountOfValues,
      boolean allowDayOverlaps)
  {
    return getLargestDecreasesHelper(amountOfValues, sortedPercentList, allowDayOverlaps);
  }

  /**
   * Gets the largest point increases in sorted form.
   * 
   * @param amountOfValues
   *          The number of date differential values wanted
   * @return The String form of the values in sorted form
   */
  public ArrayList<Map.Entry<String, Double>> getLargestPointIncreases(int amountOfValues,
      boolean allowDayOverlaps)
  {
    return getLargestIncreasesHelper(amountOfValues, sortedPointList, allowDayOverlaps);
  }

  /**
   * Gets the largest point decreases in sorted form.
   * 
   * @param amountOfValues
   *          The number of date differential values wanted
   * @return The String form of the values in sorted form
   */
  public ArrayList<Map.Entry<String, Double>> getLargestPointDecreases(int amountOfValues,
      boolean allowDayOverlaps)
  {
    return getLargestDecreasesHelper(amountOfValues, sortedPointList, allowDayOverlaps);
  }

  public String getLargestPercentageIncreasesString(int amountOfValues, boolean allowDayOverlaps)
  {
    return getLargestValuesStringHelper(
        getLargestPercentageIncreases(amountOfValues, allowDayOverlaps));
  }

  public String getLargestPercentageDecreasesString(int amountOfValues, boolean allowDayOverlaps)
  {
    return getLargestValuesStringHelper(
        getLargestPercentageDecreases(amountOfValues, allowDayOverlaps));
  }

  public String getLargestPointIncreasesString(int amountOfValues, boolean allowDayOverlaps)
  {
    return getLargestValuesStringHelper(getLargestPointIncreases(amountOfValues, allowDayOverlaps));
  }

  public String getLargestPointDecreasesString(int amountOfValues, boolean allowDayOverlaps)
  {
    return getLargestValuesStringHelper(getLargestPointDecreases(amountOfValues, allowDayOverlaps));
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
  private ArrayList<Map.Entry<String, Double>> getLargestIncreasesHelper(int amountOfValues,
      ArrayList<Map.Entry<String, Double>> list, boolean canOverlapDates)
  {
    ArrayList<Map.Entry<String, Double>> tmp = new ArrayList<Map.Entry<String, Double>>();
    int lastIndex = list.size() - 1;
    int amOfVals = amountOfValues;

    if (!canOverlapDates)
    {
      for (int i = lastIndex; i > -1 && amOfVals != 0; i--)
      {
        boolean overlapped = CalendarCalculator.datesOverlapInList(tmp, list.get(i).getKey());

        if (!overlapped)
        {
          tmp.add(list.get(i));
          amOfVals--;
        }
      }
    }
    else
    {
      for (int i = lastIndex; i > -1 && amOfVals != 0; i--)
      {
        tmp.add(list.get(i));
        amOfVals--;
      }
    }

    return tmp;
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
  private ArrayList<Map.Entry<String, Double>> getLargestDecreasesHelper(int amountOfValues,
      ArrayList<Map.Entry<String, Double>> list, boolean canOverlapDates)
  {
    ArrayList<Map.Entry<String, Double>> tmp = new ArrayList<Map.Entry<String, Double>>();
    int amOfVals = amountOfValues;

    if (!canOverlapDates)
    {
      for (int i = 0; i < list.size() && amOfVals != 0; i++)
      {
        boolean overlapped = CalendarCalculator.datesOverlapInList(tmp, list.get(i).getKey());

        if (!overlapped)
        {
          tmp.add(list.get(i));
          amOfVals--;
        }
      }
    }
    else
    {
      for (int i = 0; i < list.size() && amOfVals != 0; i++)
      {
        tmp.add(list.get(i));
        amOfVals--;
      }
    }

    return tmp;
  }

  private String getLargestValuesStringHelper(ArrayList<Map.Entry<String, Double>> list)
  {
    Iterator<Map.Entry<String, Double>> it = list.iterator();
    String retVal = "";
    // int counter = 1;

    while (it.hasNext())
    {
      Map.Entry<String, Double> entry = it.next();
      String key = entry.getKey();
      Double val = entry.getValue();

      // retVal += String.format("%d. %s: %.2f\n", counter, key, val);
      retVal += String.format("%s: %.2f\n", key, val);

      // counter++;
    }

    return retVal;
  }

}
