package calculating;

import java.util.Calendar;

public class NextDayCalculator
{
  public static Calendar makeCalendarDate(String firstDate)
  {
    Calendar cal = Calendar.getInstance();
    String[] values = firstDate.split("-");
    int year = Integer.parseInt(values[0]);
    int month = Integer.parseInt(values[1]);
    int day = Integer.parseInt(values[2]);

    cal.set(year, month, day);
    
    return cal;
  }
}
