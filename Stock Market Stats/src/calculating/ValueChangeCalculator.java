package calculating;

import java.util.ArrayList;
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
  public static void addDateDifferentialChanges(MarketInfo marketInfo, int daySpan,
      boolean isCalendarDays)
  {
    if (isCalendarDays)
    {
      calculateCalendarDayDifferentialValues(marketInfo, daySpan);
    }
    else
    {
      calculateMarketDayDifferentialValues(marketInfo, daySpan);
    }

  }

  private static void calculateCalendarDayDifferentialValues(MarketInfo marketInfo, int daySpan)
  {
    boolean isDone = false;
    Iterator<String> it = marketInfo.getMap().keySet().iterator();

    while (it.hasNext() && !isDone)
    {
      String key = it.next();

      if (canFindNextDate(marketInfo, key, daySpan))
      {
        Double firstValue, secondValue;
        String firstDate, secondDate;
        firstDate = key;

        secondDate = daySpan == 0 ? firstDate
            : findNextAvailableDay(marketInfo, key, marketInfo.getMap(), daySpan);
        firstValue = daySpan == 0 ? marketInfo.getMap().get(firstDate).getOpen()
            : marketInfo.getMap().get(firstDate).getClose();

        secondValue = marketInfo.getMap().get(secondDate).getClose();

        Double pointChange = secondValue - firstValue;
        datesHelper(marketInfo.getPointDifferentialMap(), pointChange, firstDate, secondDate,
            daySpan);

        if (pointChange != 0)
        {
          Double percentage = calculatePercentage(pointChange, firstValue, secondValue);
          datesHelper(marketInfo.getPercentDifferentialMap(), percentage, firstDate, secondDate,
              daySpan);
        }
      }
      else
      {
        isDone = true;
      }
    }
  }

  private static void calculateMarketDayDifferentialValues(MarketInfo marketInfo, int daySpan)
  {
    boolean isDone = false;

    ArrayList<Pair<Double, Double>> values = new ArrayList<>(marketInfo.getMap().values());
    ArrayList<String> keys = new ArrayList<>(marketInfo.getMap().keySet());

    for (int i = 0; i < values.size() && !isDone; i++)
    {
      if (i + daySpan < values.size())
      {
        Double firstValue, secondValue;
        String firstDate, secondDate;

        firstValue = daySpan == 0 ? values.get(i).getOpen() : values.get(i).getClose();
        secondValue = values.get(i + daySpan).getClose();
        firstDate = keys.get(i);
        secondDate = keys.get(i + daySpan);

        Double pointChange = secondValue - firstValue;
        datesHelper(marketInfo.getPointDifferentialMap(), pointChange, firstDate, secondDate,
            daySpan);

        if (pointChange != 0)
        {
          Double percentage = calculatePercentage(pointChange, firstValue, secondValue);
          datesHelper(marketInfo.getPercentDifferentialMap(), percentage, firstDate, secondDate,
              daySpan);
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

  private static Double calculatePercentage(Double pointChange, Double first, Double second)
  {
    Double retVal = null;

    if (pointChange > 0)
    {
      retVal = ((second - first) / first) * 100;
    }
    else if (pointChange < 0)
    {
      retVal = -((first - second) / first) * 100;
    }

    return retVal;
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
