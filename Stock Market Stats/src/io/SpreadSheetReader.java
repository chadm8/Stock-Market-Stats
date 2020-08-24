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
  private Double startPrice;
  private Double endPrice;
  private Double startVolume;
  private Double endVolume;
  private String startDate;
  private String endDate;
  private String fileInputPath;
  private LinkedHashMap<String, Pair<Double, Double>> map;

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
    startDate = null;
    endDate = null;
    startVolume = null;
    endVolume = null;
    startPrice = null;
    endPrice = null;
  }

  /**
   * Gets the end (most recent) date in the spreadsheet.
   * 
   * @return The end date
   */
  public String getEndDateInSheet()
  {
    return endDate;
  }

  /**
   * Gets the end (most recent) price in the spreadsheet.
   * 
   * @return The end Price
   */
  public Double getEndPriceInSheet()
  {
    return endPrice;
  }

  /**
   * Gets the end (most recent) volume in the spreadsheet.
   * 
   * @return The end volume
   */
  public Double getEndVolumeInSheet()
  {
    return endVolume;
  }

  /**
   * Gets the start (oldest) date in the spreadsheet.
   * 
   * @return The start date
   */
  public String getStartDateInSheet()
  {
    return startDate;
  }

  /**
   * Gets the start (oldest) price in the spreadsheet.
   * 
   * @return The start price
   */
  public Double getStartPriceInSheet()
  {
    return startPrice;
  }

  /**
   * Gets the start (oldest) volume in the spreadsheet.
   * 
   * @return The start volume
   */
  public Double getStartVolumeInSheet()
  {
    return startVolume;
  }

  /**
   * Reads the CSV file line by line and assigns the open and close values of each market day to the
   * map.
   */
  public void readRawDataAndAssignValues()
  {
    String line = "";
    String date = null;
    String[] lineData = null;

    try (
        BufferedInputStream inputStream = new BufferedInputStream(
            new URL(fileInputPath).openStream());

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)))
    {
      reader.readLine(); // gets rid of the spreadsheet header
      line = reader.readLine(); // avoid loop to get the very first date in spreadsheet
      lineData = line.split(",");
      startDate = lineData[0];
      startPrice = handleParse(lineData[4]);
      startVolume = handleParse(lineData[6]);

      while ((line = reader.readLine()) != null)
      {
        lineData = line.split(",");
        date = lineData[0];

        assignValuesHelper(lineData);
      }

      endDate = date;
      endPrice = handleParse(lineData[4]);
      endVolume = handleParse(lineData[6]);;

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

  /**
   * A helper to assign the values of the sheet to the map of dates and values.
   * 
   * @param lineData
   *          The data of each line in the sheet
   */
  private void assignValuesHelper(String[] lineData)
  {
    if (!lineData[1].equals("null") && !lineData[4].equals("null"))
    {
      Double openVal = handleParse(lineData[1]);
      Double closeVal = handleParse(lineData[4]);

      Pair<Double, Double> values = new Pair<>(openVal, closeVal);

      // lineData[0] is the date
      map.put(lineData[0], values);
    }
  }
  
  private Double handleParse(String value)
  {
    Double retVal = null;
    
    try
    {
      retVal = Double.parseDouble(value);
    }
    catch (NumberFormatException nfe)
    {
      nfe.printStackTrace();
    }
    
    return retVal;
  }

}
