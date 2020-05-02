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
  private static LinkedHashMap<String, Pair<Double, Double>> map;
  private static HashMap<String, Pair<Double, Double>> dateDifferentialMap;

  /**
   * The constructor.
   * 
   * @param filePath
   *          The file path
   */
  public SpreadSheetReader(String filePath)
  {
    map = new LinkedHashMap<>();
    dateDifferentialMap = new HashMap<>();
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
    Double openVal = null;
    Double closeVal = null;

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath)))
    {
      line = reader.readLine();

      while ((line = reader.readLine()) != null)
      {
        String[] lineData = line.split(",");
        date = lineData[0];

        openVal = Double.parseDouble(lineData[1]);
        closeVal = Double.parseDouble(lineData[4]);

        Pair<Double, Double> values = new Pair<>(openVal, closeVal);

        map.put(date, values);
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
  public void writeRawDataToFile(String fileName)
  {
    try
    {
      BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

      writer.write(map.size() + "\n\n");

      for (String key : map.keySet())
      {
        String date = key;
        Pair<Double, Double> pair = map.get(date);
        Double openVal = pair.getOpen();
        Double closeVal = pair.getClose();

        writer.write(String.format(date + "  Open: %.2f  Close: %.2f\n", openVal, closeVal));
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
   * Gets the map of the dates and the percent and point changes.
   * 
   * @return The map
   */
  public static HashMap<String, Pair<Double, Double>> getDifferentialMap()
  {
    return dateDifferentialMap;
  }

  /**
   * Gets the map of dates and the open and close values.
   * 
   * @return The map
   */
  public static LinkedHashMap<String, Pair<Double, Double>> getMap()
  {
    return map;
  }

}
