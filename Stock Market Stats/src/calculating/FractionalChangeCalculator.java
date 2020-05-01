package calculating;

import java.util.HashMap;
import java.util.LinkedHashMap;

import io.SpreadSheetReader;
import tools.Pair;

// MAKE THIS INTO A STATIC CLASS
public class FractionalChangeCalculator
{
  private int amountOfDaysToCompare;
  private String currentDate;

  public FractionalChangeCalculator(String filePath)
  {
    this(filePath, 0, 3, 15, 2020);
  }

  public FractionalChangeCalculator(String filePath, int amountOfDaysToCompare, int currentMonth, int currentDay, int currentYear)
  {
    currentDate = String.format("%s/%s/%s", currentMonth, currentDay, currentYear);
    this.amountOfDaysToCompare = amountOfDaysToCompare;
  }

  public void addPercentChanges(LinkedHashMap<String, Double> valuesMap, HashMap<String, Double> resultMap)
  {
    for (String key : SpreadSheetReader.getCloseMap().keySet())
    {
      String date1 = key;
      String date2 = findAvailableDay(key);
      Double val = valuesMap.get(date1);
      Double val2 = valuesMap.get(date2);
    }
  }
  
  public void addOneDayPercentChanges(LinkedHashMap<String, Pair<Double, Double>> valuesMap, HashMap<String, Double> resultMap)
  {
    
  }
  
  private String findAvailableDay(String dayToCompare)
  {
    String[] curDate = currentDate.split("/");
    String[] date = dayToCompare.split("/");
    int month = Integer.parseInt(date[0]);
    int day = Integer.parseInt(date[1]);
    int year = Integer.parseInt(date[2]);
    
    if (month + (amountOfDaysToCompare / 30) > 12)
    {
      year += 1;
    }
    
    year += amountOfDaysToCompare / 365;
    month += (amountOfDaysToCompare / 30) % 12;
    day += amountOfDaysToCompare % 30;
    
    return "" + month + "/" + day + "/" + year;
  }

}
