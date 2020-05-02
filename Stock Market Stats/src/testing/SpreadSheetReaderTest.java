package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.SpreadSheetReader;
import tools.MarketInfo;

class SpreadSheetReaderTest
{

  MarketInfo mi = new MarketInfo(
      "https://query1.finance.yahoo.com/v7/finance/download/%5EGSPC?period1=-1325635200&period2=1588377600&interval=1d&events=history",
      "data.txt");

  @Test
  void test()
  {
    assertEquals(23192, mi.getMap().size());
  }

}
