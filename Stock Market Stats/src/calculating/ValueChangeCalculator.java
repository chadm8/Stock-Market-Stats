package calculating;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import tools.MarketInfo;
import tools.Pair;

/**
 * A class to compute changes in the market value over a given set of time.
 * 
 * @author Chad Martin
 * @version 2 May 2020
 *
 */
public class ValueChangeCalculator
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
  public static void addDateDifferentialChanges(MarketInfo marketInfo, int daySpan)
  {
    boolean isDone = false;
    Iterator<String> it = marketInfo.getMap().keySet().iterator();

    while (it.hasNext() && !isDone)
    {
      String key = it.next();

      if (canFindNextDate(marketInfo, key, daySpan))
      {
        String date1 = key;
        String date2;
        Double first, second;

        if (daySpan == 0)
        {
          date2 = date1;
          first = marketInfo.getMap().get(date1).getOpen();
        }
        else
        {
          if (canFindNextDate(marketInfo, key, daySpan))
            date2 = findNextAvailableDay(marketInfo, key, marketInfo.getMap(), daySpan);
          else
            date2 = null;

          if (date2 == null)
          {
            System.err.print("Could not find the next date");
            return;
          }

          first = marketInfo.getMap().get(date1).getClose();
        }

        second = marketInfo.getMap().get(date2).getClose();

        Double pointChange = second - first;
        datesHelper(marketInfo.getPointDifferentialMap(), pointChange, date1, date2, daySpan);

        if (pointChange > 0)
        {
          Double percInc = calculateIncreasePercentage(first, second);
          datesHelper(marketInfo.getPercentDifferentialMap(), percInc, date1, date2, daySpan);
        }
        else if (pointChange < 0)
        {
          Double percDec = -calculateDecreasePercentage(first, second);
          datesHelper(marketInfo.getPercentDifferentialMap(), percDec, date1, date2, daySpan);
        }
      }
      else
      {
        isDone = true;
      }
    }
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
  private static String findNextAvailableDay(MarketInfo marketInfo, String dayToCompare,
      LinkedHashMap<String, Pair<Double, Double>> valuesMap, int daySpan)
  {
    String retVal = null;

    Calendar cal = CalendarCalculator.makeCalendarDate(dayToCompare);
    cal.add(Calendar.DAY_OF_MONTH, daySpan);

    // date values after day span is taken into account
    int year2 = cal.get(Calendar.YEAR);
    int month2 = cal.get(Calendar.MONTH);
    int day2 = cal.get(Calendar.DAY_OF_MONTH);

    // ensures a consistent format with the spreadsheet
    // if the date is 11-1-3, it changes to 11-01-03
    String sMonth2 = month2 < 10 ? "0" + month2 : "" + month2;
    String sDay2 = day2 < 10 ? "0" + day2 : "" + day2;

    retVal = String.format("%d-%s-%s", year2, sMonth2, sDay2);

    if (valuesMap.containsKey(retVal))
      return retVal;
    else
      // if the map does not contain the date, one day gets added until a date is valid
      return findNextAvailableDay(marketInfo, retVal, valuesMap, 1);
  }

  private static Double calculateIncreasePercentage(Double first, Double second)
  {
    return ((second - first) / first) * 100;
  }

  private static Double calculateDecreasePercentage(Double first, Double second)
  {
    return ((first - second) / first) * 100;
  }

  /**
   * Checks to ensure that the next date comes before the most recent date of the stock market.
   * 
   * @param marketInfo
   *          The MarketInfo object
   * @param date
   *          The date
   * @param daySpan
   *          The day span
   * @return True if the date comes before the most recent stock market date
   */
  private static boolean canFindNextDate(MarketInfo marketInfo, String date, int daySpan)
  {
    Calendar cal = CalendarCalculator.makeCalendarDate(date);
    cal.add(Calendar.DAY_OF_MONTH, daySpan);

    return cal.before(marketInfo.getMostRecentCalendarDate());
  }

  private static void datesHelper(HashMap<String, Double> map, Double value, String date1,
      String date2, int daySpan)
  {
    if (daySpan > 1)
    {
      map.put(date1 + " to " + date2, value);
    }
    else
    {
      map.put(date2, value);
    }
  }

}
