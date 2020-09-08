package calculating;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A class for making calculations for Calendar dates.
 * 
 * @author Chad Martin
 * @version 11 May 2020
 *
 */
public class CalendarCalculator
{
  /**
   * Makes a Calendar dates from a String date.
   * 
   * @param date
   *          The date
   * @return The Calendar date
   */
  public static Calendar makeCalendarDate(String date)
  {
    Calendar cal = Calendar.getInstance();
    String[] values = date.split("-");
    int year = Integer.parseInt(values[0]);
    int month = Integer.parseInt(values[1]) - 1; // minus 1 because of Calendar zero-based api
    int day = Integer.parseInt(values[2]);

    cal.set(year, month, day);

    return cal;
  }

  /**
   * Checks to see if the dates given overlap each other. For example, 2016-12-24 to 2016-12-28
   * overlaps 2016-12-23 to 2016-12-27.
   * 
   * @param firstDateSetString
   *          The first set of dates
   * @param secondDateSetString
   *          The second set of dates
   * @return True if the dates overlap
   */
  public static boolean datesOverlap(String firstDateSetString, String secondDateSetString)
  {

    boolean isOverlapped = false;

    if (firstDateSetString.contains(" to "))
    {
      String[] firstDateSet = firstDateSetString.split(" to ");
      String[] secondDateSet = secondDateSetString.split(" to ");

      Calendar firstDateFirstSet = makeCalendarDate(firstDateSet[0]);
      Calendar secondDateFirstSet = makeCalendarDate(firstDateSet[1]);
      Calendar firstDateSecondSet = makeCalendarDate(secondDateSet[0]);
      Calendar secondDateSecondSet = makeCalendarDate(secondDateSet[1]);

      if (firstDateSecondSet.after(firstDateFirstSet)
          && firstDateSecondSet.compareTo(secondDateFirstSet) <= 0)
      {
        isOverlapped = true;
      }
      else if (secondDateSecondSet.after(firstDateFirstSet)
          && secondDateSecondSet.compareTo(secondDateFirstSet) <= 0)
      {
        isOverlapped = true;
      }
    }

    return isOverlapped;
  }

  /**
   * Checks the entire list of dates, and compares each one to the dateSet to see if it overlaps
   * with anything.
   * 
   * @param list The list of dates
   * @param date The date
   * @return
   */
  public static boolean datesOverlapInList(ArrayList<Map.Entry<String, Double>> list,
      String date)
  {
    boolean isOverlapped = false;

    for (Entry<String, Double> current : list)
    {
      if (CalendarCalculator.datesOverlap(current.getKey(), date))
      {
        isOverlapped = true;
      }
    }

    return isOverlapped;
  }

}
