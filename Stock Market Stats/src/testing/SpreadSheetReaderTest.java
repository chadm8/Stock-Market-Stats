package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import io.SpreadSheetReader;

class SpreadSheetReaderTest
{
  
  SpreadSheetReader ssr = new SpreadSheetReader("C:\\Users\\chado\\Downloads\\s&pHistorical.csv");

  @Test
  void test()
  {
    assertEquals(23192, SpreadSheetReader.getMap().size());
  }

}
