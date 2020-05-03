package calculating;

import java.util.Calendar;

public class CalendarCalculator
{
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
