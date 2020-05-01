package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import tools.Pair;

/**
 * A class to read certain columns from each row of the spreadsheet given.
 * 
 * @author Chad Martin
 * @version 27 February 2020
 *
 */
public class SpreadSheetReader
{
  private static LinkedHashMap<String, Double> closeMap;
  private static LinkedHashMap<String, Pair<Double, Double>> oneDayMap;
  private static HashMap<String, Double> dateDifferentialMap;

  /**
   * The constructor.
   * 
   * @param filePath
   *          The file path
   */
  public SpreadSheetReader(String filePath)
  {
    closeMap = new LinkedHashMap<>();
    readSheet(filePath);
  }

  /**
   * Reads the date and the closing price of the stock, ETF, or other medium.
   * 
   * @param filePath
   *          The file path
   */
  public void readSheet(String filePath)
  {
    String line = "";
    String date = null;
    Double val = Double.valueOf(0.0);

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath)))
    {
      line = reader.readLine();

      while ((line = reader.readLine()) != null)
      {
        String[] lineData = line.split(",");
        date = lineData[0];
        val = Double.parseDouble(lineData[4]);

        closeMap.put(date, val);
      }

    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Writes the specific values in the HashMap to a file with the name provided.
   * 
   * @param fileName
   *          The file name
   * @param map
   *          The map of dates and values
   */
  public void writeRawDataToFile(String fileName, HashMap<String, Double> map)
  {
    try
    {
      BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

      writer.write(map.size() + "\n\n");

      for (String key : map.keySet())
      {
        String date = key;
        Double val = map.get(date);

        writer.write(String.format(date + "  %.2f\n", val));
        writer.flush();
      }
      writer.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * Gets the map of dates and close values.
   * 
   * @return The map
   */
  public static LinkedHashMap<String, Double> getCloseMap()
  {
    return closeMap;
  }
  
}
