package io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
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

  /**
   * The constructor.
   * 
   * @param filePath
   *          The file path
   */
  public SpreadSheetReader(String fileInputPath)
  {
    map = new LinkedHashMap<>();
    this.fileInputPath = fileInputPath;
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
   * Reads the CSV file line by line and assigns the open and close values of each market day to the
   * map.
   */
  public void readRawDataAndAssignValues()
  {
    String line = "";
    String date = null;
    Double openVal = null;
    Double closeVal = null;

    try (
        BufferedInputStream inputStream = new BufferedInputStream(
            new URL(fileInputPath).openStream());

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)))
    {
      reader.readLine(); // gets rid of the spreadsheet header

      while ((line = reader.readLine()) != null)
      {
        String[] lineData = line.split(",");
        date = lineData[0];

        if (!lineData[1].equals("null") && !lineData[4].equals("null"))
        {
          System.out.println(lineData[1]);
          openVal = Double.parseDouble(lineData[1]);
          closeVal = Double.parseDouble(lineData[4]);

          Pair<Double, Double> values = new Pair<>(openVal, closeVal);

          map.put(date, values);
        }
      }

      mostRecentDate = date;

    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

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
