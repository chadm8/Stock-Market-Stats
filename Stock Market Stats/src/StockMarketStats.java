import java.io.IOException;
import java.util.Calendar;

import gui.StockMarketStatsWindow;
import tools.MarketInfo;

/**
 * The main class to get certain statistics from the market.
 * 
 * @author Chad Martin
 * @version 11 May 2020
 *
 */
public class StockMarketStats
{
  public static void main(String[] args) throws IOException
  {
    MarketInfo info = new MarketInfo(
        "https://query1.finance.yahoo.com/v7/finance/download/TSLA?period1=1277769600&period2=1589155200&interval=1d&events=history",
        "data.txt");
    info.calculateDateDifferentialValues(1);
    System.out.println(info.getLargestPercentageDecreases(10));
    System.out.println(info.getLargestPercentageIncreases(10));
    System.out.println(info.getLargestPointDecreases(10));
    System.out.println(info.getLargestPointIncreases(10));
    
    new StockMarketStatsWindow();
  }

}
