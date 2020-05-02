package io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
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
  private LinkedHashMap<String, Pair<Double, Double>> map;
  private String mostRecentDate;
  private String fileInputPath;
  private String fileOutputPath;
  private String rawDataPath;

  /**
   * The constructor.
   * 
   * @param filePath
   *          The file path
   */
  public SpreadSheetReader(String fileInputPath, String fileOutputPath)
  {
    map = new LinkedHashMap<>();
    this.fileInputPath = fileInputPath;
    this.fileOutputPath = fileOutputPath;
    rawDataPath = fileOutputPath;
    mostRecentDate = null;
  }

  /**
   * Gets the first date in the spreadsheet.
   * 
   * @return The first date
   */
  public String getMostRecentDate()
  {
    return mostRecentDate;
  }

  /**
   * Reads the date and the closing price of the stock, ETF, index, or other medium.
   * 
   * @param filePath
   *          The file path
   */
  public void readOutputSheet()
  {
    String line = "";
    String date = null;
    Double openVal = null;
    Double closeVal = null;

    try (BufferedReader reader = new BufferedReader(new FileReader(rawDataPath)))
    {
      reader.readLine(); // gets rid of the spreadsheet header
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

      mostRecentDate = date;

    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  public void writeRawDataToFile()
  {
    try (
        BufferedInputStream inputStream = new BufferedInputStream(
            new URL(fileInputPath).openStream());
        FileOutputStream fileOS = new FileOutputStream(fileOutputPath))
    {
      byte data[] = new byte[1024];
      int byteContent;
      while ((byteContent = inputStream.read(data, 0, 1024)) != -1)
      {
        fileOS.write(data, 0, byteContent);
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  // /**
  // * Writes the specific values in the HashMap to a file with the name provided.
  // *
  // * @param fileName
  // * The file name
  // * @param map
  // * The map of dates and values
  // */
  // public void writeRawDataToFile(String fileName)
  // {
  // try
  // {
  // BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
  //
  // writer.write(map.size() + "\n\n");
  //
  // for (String key : map.keySet())
  // {
  // String date = key;
  // Pair<Double, Double> pair = map.get(date);
  // Double openVal = pair.getOpen();
  // Double closeVal = pair.getClose();
  //
  // writer.write(String.format(date + " Open: %.2f Close: %.2f\n", openVal, closeVal));
  // writer.flush();
  // }
  // writer.close();
  // }
  // catch (IOException e)
  // {
  // e.printStackTrace();
  // }
  // }

  /**
   * Gets the map of dates and the open and close values.
   * 
   * @return The map
   */
  public LinkedHashMap<String, Pair<Double, Double>> getMap()
  {
    return map;
  }

}
