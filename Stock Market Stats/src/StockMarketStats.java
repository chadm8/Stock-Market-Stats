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
    //MarketInfo info = new MarketInfo(
        //"https://query1.finance.yahoo.com/v7/finance/download/%5EGSPC?period1=-1325635200&period2=1590105600&interval=1d&events=history");
//    info.calculateDateDifferentialValues(1);
//    System.out.println(info.getLargestPercentageDecreasesString(10));
//    System.out.println(info.getLargestPercentageIncreasesString(10));
//    System.out.println(info.getLargestPointDecreasesString(10));
//    System.out.println(info.getLargestPointIncreasesString(10));
    
    new StockMarketStatsWindow();
  }

}
