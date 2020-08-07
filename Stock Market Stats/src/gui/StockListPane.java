package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import tools.GUIConstants;

public class StockListPane extends JPanel
{
  private static final long serialVersionUID = 1L;

  private DefaultListModel<String> listModel;
  private JList<String> valuesList;
  private String titleName;

  public StockListPane(String titleName)
  {
    listModel = new DefaultListModel<>();
    valuesList = new JList<>(listModel);
    this.titleName = titleName;

    this.setLayout(new BorderLayout());
    this.setBorder(BorderFactory.createTitledBorder(null, titleName, 2, 2,
        new Font(GUIConstants.FONT_NAME, GUIConstants.FONT_STYLE, 20), Color.WHITE));
    this.add(new JScrollPane(valuesList));
  }

  public void addToList(ArrayList<Map.Entry<String, Double>> list)
  {
    listModel.clear();

    for (int i = 0; i < list.size(); i++)
    {
      Entry<String, Double> entry = list.get(i);
      String date = reformatDate(entry.getKey());
      listModel.addElement(String.format("%s: %.2f\n", date, entry.getValue()));
    }

    // checks for negative value to determine color of foreground
    if (titleName.contains("Decrease"))
    {
      valuesList.setForeground(GUIConstants.RED);
    }
    else if (titleName.contains("Increase"))
    {
      valuesList.setForeground(GUIConstants.GREEN);
    }
  }

  private String reformatDate(String date)
  {
    String reformattedDate;

    if (date.contains(" to "))
    {
      String[] dates = date.split(" to ");
      String[] firstDateElements = dates[0].split("-");
      String[] secondDateElements = dates[1].split("-");

      // reformat to mm/dd/yyyy to mm/dd/yyyy
      reformattedDate = String.format("%s/%s/%s to %s/%s/%s", firstDateElements[1],
          firstDateElements[2], firstDateElements[0], secondDateElements[1], secondDateElements[2],
          secondDateElements[0]);
    }
    else
    {
      String[] dateElements = date.split("-");
      // reformat to mm/dd/yyyy
      reformattedDate = String.format("%s/%s/%s", dateElements[1], dateElements[2],
          dateElements[0]);
    }

    return reformattedDate;
  }

}
