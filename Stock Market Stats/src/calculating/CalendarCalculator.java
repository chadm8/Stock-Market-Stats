package calculating;

import java.util.Calendar;

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
    int month = Integer.parseInt(values[1]);
    int day = Integer.parseInt(values[2]);

    cal.set(year, month, day);

    return cal;
  }
}
