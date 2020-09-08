package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import tools.GUIConstants;
import tools.MarketInfo;

/**
 * A class for aiding the setup of the lists for the differential values and additional information.
 * 
 * @author Chad Martin
 * @version 8/14/2020
 *
 */
public class StockList extends JPanel
{
  private static final long serialVersionUID = 1L;

  private DefaultListModel<String> listModel;
  private JList<String> list;
  private String titleName;

  /**
   * Creates a list for the name of the title.
   * 
   * @param titleName
   *          The name of the title
   */
  public StockList(String titleName)
  {
    listModel = new DefaultListModel<>();
    list = new JList<>(listModel);
    this.titleName = titleName;

    DefaultListCellRenderer renderer = (DefaultListCellRenderer) list.getCellRenderer();
    renderer.setHorizontalAlignment(SwingConstants.CENTER);

    list.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    list.setBackground(GUIConstants.DIM_GRAY);
    list.setFixedCellHeight(30);

    this.setLayout(new BorderLayout());
    this.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(GUIConstants.DARK_GRAY), titleName, 2, 2,
        new Font(GUIConstants.FONT_NAME, GUIConstants.FONT_STYLE, 20), Color.WHITE));
    this.add(new JScrollPane(list));
  }

  /**
   * Clears the list then adds the values from the ArrayList to the list.
   * 
   * @param valuesList
   *          The ArrayList of values
   */
  public void addToVolitilityList(ArrayList<Map.Entry<String, Double>> valuesList)
  {
    listModel.clear();

    for (int i = 0; i < valuesList.size(); i++)
    {
      Entry<String, Double> entry = valuesList.get(i);
      String date = reformatDate(entry.getKey());
      listModel.addElement(String.format("%s: %.2f\n", date, entry.getValue()));
    }

    // checks for negative value to determine color of foreground
    if (titleName.contains("Decrease"))
    {
      list.setForeground(GUIConstants.RED);
    }
    else if (titleName.contains("Increase"))
    {
      list.setForeground(GUIConstants.GREEN);
    }
  }

  /**
   * Adds information from MarketInfo to the list.
   * 
   * @param marketInfo
   *          The MarketInfo
   */
  public void addToAdditionalInfoList(MarketInfo marketInfo)
  {
    listModel.clear();
    list.setForeground(Color.WHITE);

    listModel.addElement(String.format("Date Start/End: %s | %s",
        reformatDate(marketInfo.getStartDate()), reformatDate(marketInfo.getEndDate())));
    if (marketInfo.getStartPrice() != null && marketInfo.getEndPrice() != null)
      listModel.addElement(String.format("Price Start/End: %.2f | %.2f", marketInfo.getStartPrice(),
          marketInfo.getEndPrice()));
    if (marketInfo.getStartVolume() != null && marketInfo.getEndVolume() != null)
      listModel.addElement(String.format("Volume Start/End: %.0f | %.0f",
          marketInfo.getStartVolume(), marketInfo.getEndVolume()));

  }

  /**
   * Clears the list model.
   */
  public void clearList()
  {
    listModel.clear();
  }

  /**
   * Reformats a date(s) from yyyy-mm-dd to mm/dd/yyyy format.
   * 
   * @param date The date(s)
   * @return The reformatted date(s)
   */
  private String reformatDate(String date)
  {
    String reformattedDate;

    if (date.contains(" to "))
    {
      String[] dates = date.split(" to ");
      String[] firstDateElements = dates[0].split("-");
      String[] secondDateElements = dates[1].split("-");

      // reformat from yyyy-mm-dd to mm/dd/yyyy
      reformattedDate = String.format("%s/%s/%s to %s/%s/%s", firstDateElements[1],
          firstDateElements[2], firstDateElements[0], secondDateElements[1], secondDateElements[2],
          secondDateElements[0]);
    }
    else
    {
      String[] dateElements = date.split("-");
      // reformat from yyyy-mm-dd to mm/dd/yyyy
      reformattedDate = String.format("%s/%s/%s", dateElements[1], dateElements[2],
          dateElements[0]);
    }

    return reformattedDate;
  }

}
