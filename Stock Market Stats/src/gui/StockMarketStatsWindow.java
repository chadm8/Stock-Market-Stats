package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import tools.GUIConstants;
import tools.MarketInfo;

/**
 * The GUI for the Stock Market Stats.
 * 
 * @author Chad Martin
 * @version 16 May 2020
 *
 */
public class StockMarketStatsWindow extends JFrame implements ActionListener, FocusListener
{
  private static final long serialVersionUID = 1L;

  private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

  private JButton calculateButton;
  private JButton clearButton;

  private JLabel titleLabel;

  private JPanel upperPanel;
  private JPanel lowerPanel;

  private JPanel upperCentralPanel;
  private JPanel lowerCentralPanel;
  private JPanel timeSpanPanel;
  private JPanel overlapDatesPanel;

  private JRadioButton calendarDaysButton;
  private JRadioButton marketDaysButton;
  private JRadioButton overlapDatesButton;

  private JSpinner daySpanSpinner;
  private JSpinner numberOfResultsSpinner;

  private JTextField textField;

  private StockList percentIncreasePane;
  private StockList pointIncreasePane;
  private StockList percentDecreasePane;
  private StockList pointDecreasePane;
  private StockList additionalInfoPane;

  private UtilityBar utilityBar;

  /**
   * The constructor.
   */
  public StockMarketStatsWindow()
  {
    initializeVariables();

    this.setLayout(new GridLayout(2, 0, 20, 20));
    this.setSize(screenSize.width / 2, screenSize.height / 2);
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    this.setBackground(GUIConstants.DARK_GRAY);
    this.getRootPane().setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

    addToPanels();
    setAttributes();
    addListeners(this.getRootPane());
    this.setJMenuBar(utilityBar);

    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
    this.setVisible(true);

    titleLabel.requestFocus();
  }

  @Override
  public void focusGained(FocusEvent e)
  {
    if (e.getComponent().equals(textField))
    {
      if (textField.getText()
          .equals("Enter the Yahoo Finance historical data link address of a ticker here"))
      {
        textField.setText("");
        textField.setForeground(Color.WHITE);
      }
    }
  }

  @Override
  public void focusLost(FocusEvent e)
  {
    if (e.getComponent().equals(textField))
    {
      if (textField.getText().isEmpty())
      {
        textField.setForeground(Color.WHITE);
        textField.setText("Enter the Yahoo Finance historical data link address of a ticker here");
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    String command = e.getActionCommand();

    if (command.equals("Calculate"))
    {
      calculateButtonHelper();
    }
    else if (command.equals("Clear"))
    {
      textField.setText("Enter the Yahoo Finance historical data link address of a ticker here");
      daySpanSpinner.setValue(1);
      numberOfResultsSpinner.setValue(5);
      clearLists();
    }
    else if (command.equals("Calendar Days"))
    {
      // ensures that one button is always selected
      if (!calendarDaysButton.isSelected() && !marketDaysButton.isSelected())
      {
        calendarDaysButton.setSelected(true);
      }
      else
      {
        marketDaysButton.setSelected(false);
      }
    }
    else if (command.equals("Market Days"))
    {
      // ensures that one button is always selected
      if (!marketDaysButton.isSelected() && !calendarDaysButton.isSelected())
      {
        marketDaysButton.setSelected(true);
      }
      else
      {
        calendarDaysButton.setSelected(false);
      }
    }
  }

  /**
   ******************************************************************************
   ******************************************************************************
   ***************************** PRIVATE METHODS ********************************
   ******************************************************************************
   ******************************************************************************
   */

  private void addListeners(Container container)
  {

    Component[] comps = container.getComponents();
    for (Component comp : comps)
    {
      if (comp instanceof AbstractButton)
      {
        ((AbstractButton) comp).addActionListener(this);
      }
      else
      {
        comp.addFocusListener(this);
      }
      addListeners((Container) comp);
    }
  }

  private void addToPanels()
  {
    add(upperPanel);
    add(lowerPanel);

    upperPanel.add(titleLabel);
    upperPanel.add(textField);
    upperPanel.add(upperCentralPanel);
    upperPanel.add(lowerCentralPanel);

    upperCentralPanel.add(new JLabel());
    upperCentralPanel.add(new JLabel());
    upperCentralPanel.add(calculateButton);
    upperCentralPanel.add(clearButton);
    upperCentralPanel.add(new JLabel());
    upperCentralPanel.add(new JLabel());
    upperCentralPanel.add(new JLabel());

    lowerCentralPanel.add(new JLabel());
    lowerCentralPanel.add(new JLabel());
    lowerCentralPanel.add(new JLabel("Day Span:", SwingConstants.RIGHT));
    lowerCentralPanel.add(daySpanSpinner);
    lowerCentralPanel.add(new JLabel());
    lowerCentralPanel.add(timeSpanPanel);

    lowerCentralPanel.add(new JLabel());
    lowerCentralPanel.add(new JLabel());
    lowerCentralPanel.add(new JLabel("Number Of Results:", SwingConstants.RIGHT));
    lowerCentralPanel.add(numberOfResultsSpinner);
    lowerCentralPanel.add(new JLabel());
    lowerCentralPanel.add(overlapDatesPanel);

    timeSpanPanel.add(calendarDaysButton);
    timeSpanPanel.add(marketDaysButton);

    overlapDatesPanel.add(overlapDatesButton);

    lowerPanel.add(percentIncreasePane);
    lowerPanel.add(pointIncreasePane);
    lowerPanel.add(percentDecreasePane);
    lowerPanel.add(pointDecreasePane);
    lowerPanel.add(additionalInfoPane);

  }

  private void calculateButtonHelper()
  {
    try
    {
      new URL(textField.getText());
    }
    catch (MalformedURLException e)
    {
      JOptionPane.showMessageDialog(new JFrame(),
          "Invalid or empty link address, click help for information", "Invalid URL",
          JOptionPane.ERROR_MESSAGE);

      return;
    }

    int daySpan = (int) daySpanSpinner.getValue();
    int amountOfValues = (int) numberOfResultsSpinner.getValue();
    boolean isCalendarDays = calendarDaysButton.isSelected();
    boolean isOverlappingDates = overlapDatesButton.isSelected();
    MarketInfo marketInfo = new MarketInfo(textField.getText());

    marketInfo.calculateDateDifferentialValues(daySpan, isCalendarDays);

    percentIncreasePane
        .addToVolitilityList(marketInfo.getLargestPercentageIncreases(amountOfValues, isOverlappingDates));
    pointIncreasePane
        .addToVolitilityList(marketInfo.getLargestPointIncreases(amountOfValues, isOverlappingDates));
    percentDecreasePane
        .addToVolitilityList(marketInfo.getLargestPercentageDecreases(amountOfValues, isOverlappingDates));
    pointDecreasePane
        .addToVolitilityList(marketInfo.getLargestPointDecreases(amountOfValues, isOverlappingDates));
    additionalInfoPane.addToAdditionalInfoList(marketInfo);
  }

  private void clearLists()
  {
    percentIncreasePane.clearList();
    pointIncreasePane.clearList();
    percentDecreasePane.clearList();
    pointDecreasePane.clearList();
    additionalInfoPane.clearList();
  }

  private void initializeVariables()
  {
    titleLabel = new JLabel("Stock Market Volatility", SwingConstants.CENTER);
    textField = new JTextField(
        "Enter the Yahoo Finance historical data link address of a ticker here");

    upperPanel = new JPanel(new GridLayout(4, 0, 10, 25));
    lowerPanel = new JPanel(new GridLayout(0, 5, 10, 0));

    upperCentralPanel = new JPanel(new GridLayout(0, 6, 40, 0));
    lowerCentralPanel = new JPanel(new GridLayout(2, 6, 40, 3));
    timeSpanPanel = new JPanel(new GridLayout(2, 0));
    overlapDatesPanel = new JPanel(new GridLayout(1, 0));

    calculateButton = new JButton("Calculate");
    clearButton = new JButton("Clear");
    calendarDaysButton = new JRadioButton("Calendar Days");
    marketDaysButton = new JRadioButton("Market Days");
    overlapDatesButton = new JRadioButton("Overlap Dates");

    daySpanSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 5000, 1));
    numberOfResultsSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 30, 1));

    percentIncreasePane = new StockList("Largest Percent Increase");
    pointIncreasePane = new StockList("Largest Point Increase");
    percentDecreasePane = new StockList("Largest Percent Decrease");
    pointDecreasePane = new StockList("Largest Point Decrease");
    additionalInfoPane = new StockList("Additional Information");

    utilityBar = new UtilityBar();
  }

  private void setAttributes()
  {
    setAttributesHelper(this.getRootPane());
    textField.setHorizontalAlignment(JTextField.CENTER);

    titleLabel.setFont(new Font(GUIConstants.FONT_NAME, GUIConstants.FONT_STYLE, 60));
    textField.setFont(new Font(GUIConstants.FONT_NAME, GUIConstants.FONT_STYLE, 30));
    ((JSpinner.NumberEditor) daySpanSpinner.getEditor()).getTextField()
        .setFont(new Font(GUIConstants.FONT_NAME, GUIConstants.FONT_STYLE, 20));
    ((JSpinner.NumberEditor) numberOfResultsSpinner.getEditor()).getTextField()
        .setFont(new Font(GUIConstants.FONT_NAME, GUIConstants.FONT_STYLE, 20));

    setJSpinnerButtonColors(daySpanSpinner);
    setJSpinnerButtonColors(numberOfResultsSpinner);

    daySpanSpinner.getComponent(0).setBackground(Color.WHITE);

    calendarDaysButton.setSelected(true);

    lowerPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 30, 30));
    upperPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
    overlapDatesPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    timeSpanPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    calculateButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
    clearButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
  }

  private void setAttributesHelper(Container container)
  {
    Component[] comps = container.getComponents();
    for (Component comp : comps)
    {
      if (comp instanceof JPanel)
      {
        comp.setBackground(GUIConstants.DARK_GRAY);
      }
      else if (comp instanceof JRadioButton || comp instanceof JButton
          || comp instanceof JTextField)
      {
        comp.setBackground(GUIConstants.LIGHT_GRAY);
      }
      else if (comp instanceof JTextPane)
      {
        ((JTextPane) comp).setEditable(false);
        comp.setBackground(GUIConstants.MEDIUM_GRAY);
      }
      else
      {
        // comp.setBackground(GUIConstants.MEDIUM_GRAY);
      }

      comp.setForeground(Color.WHITE);
      comp.setFont(new Font(GUIConstants.FONT_NAME, GUIConstants.FONT_STYLE, 18));
      setAttributesHelper((Container) comp);
    }
  }

  private void setJSpinnerButtonColors(JSpinner spinner)
  {
    for (int i = 0; i < 2; i++)
    {
      spinner.getComponent(i).setBackground(Color.WHITE);
    }
  }

}
