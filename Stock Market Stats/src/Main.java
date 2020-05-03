import java.io.IOException;
import java.util.Calendar;

import tools.MarketInfo;

public class Main
{
  public static void main(String[] args) throws IOException
  {
    MarketInfo info = new MarketInfo(
        "https://query1.finance.yahoo.com/v7/finance/download/USO?period1=1144627200&period2=1588464000&interval=1d&events=history",
        "data.txt");
    info.calculateDateDifferentialValues(1);
    System.out.println(info.getLargestPercentageDecreases(10));
    System.out.println(info.getLargestPercentageIncreases(10));
    System.out.println(info.getLargestPointDecreases(10));
    System.out.println(info.getLargestPointIncreases(10));
  }

}
