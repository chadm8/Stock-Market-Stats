import java.io.IOException;
import java.util.Calendar;

import tools.MarketInfo;

public class Main
{
  public static void main(String[] args) throws IOException
  {
    MarketInfo info = new MarketInfo(
        "https://query1.finance.yahoo.com/v7/finance/download/%5EDJI?period1=475804800&period2=1588377600&interval=1d&events=history",
        "data.txt");
    System.out.println(findAvailableDay(3, "1999-01-01"));
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
  private static String findAvailableDay(int daySpan, String dayToCompare)
  {
    if (daySpan == 1)
      return dayToCompare;

    String retVal = null;
    String[] date = null;

    if (dayToCompare.contains("-"))
    {
      date = dayToCompare.split("-");
    }
    else
    {
      date = dayToCompare.split("/");
    }

    int year = Integer.parseInt(date[0]);
    int month = Integer.parseInt(date[1]);
    int day = Integer.parseInt(date[2]);

    Calendar cal = Calendar.getInstance();
    cal.set(Calendar.DAY_OF_MONTH, day);
    cal.set(Calendar.MONTH, month);
    cal.set(Calendar.YEAR, year);
    cal.add(Calendar.DAY_OF_MONTH, daySpan);

    int year2 = cal.get(Calendar.YEAR);
    int month2 = cal.get(Calendar.MONTH);
    int day2 = cal.get(Calendar.DAY_OF_MONTH);

    String sMonth2 = month2 < 10 ? "0" + month2 : "" + month2;
    String sDay2 = day2 < 10 ? "0" + day2 : "" + day2;

    retVal = String.format("%d-%s-%s", year2, sMonth2, sDay2);

    return retVal;
  }
}
