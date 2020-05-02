import java.io.IOException;

import io.SpreadSheetReader;

public class Main
{
  public static void main(String[] args) throws IOException
  {
    SpreadSheetReader ssr = new SpreadSheetReader("C:\\Users\\chado\\Downloads\\s&pHistorical.csv");
    ssr.writeRawDataToFile("data.txt");
    System.out.println(findAvailableDay("12/3/1999"));
  }
  
  private static String findAvailableDay(String dayToCompare)
  {
    int amountOfDaysToCompare = 30;
    //String[] curDate = currentDate.split("/");
    String[] date = dayToCompare.split("/");
    int month = Integer.parseInt(date[0]);
    int day = Integer.parseInt(date[1]);
    int year = Integer.parseInt(date[2]);
    
    if (month + (amountOfDaysToCompare / 30) > 12)
    {
      year += 1;
    }
    
    year += amountOfDaysToCompare / 365;
    month = (month + (amountOfDaysToCompare / 30)) % 12;
    day += amountOfDaysToCompare % 30;
    
    return "" + month + "/" + day + "/" + year;
  }
}
