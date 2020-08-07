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

  public static boolean datesOverlapInList(ArrayList<Map.Entry<String, Double>> list,
      String dateSet)
  {
    boolean isOverlapped = false;

    for (Entry<String, Double> current : list)
    {
      if (CalendarCalculator.datesOverlap(current.getKey(), dateSet))
      {
        isOverlapped = true;
      }
    }

    return isOverlapped;
  }

}
