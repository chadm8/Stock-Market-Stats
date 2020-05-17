package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * The GUI for the Stock Market Stats.
 * 
 * @author Chad Martin
 * @version 16 May 2020
 *
 */
public class StockMarketStatsWindow extends JFrame
{
  private static final long serialVersionUID = 1L;

  private static final String FONT_NAME = "TimesRoman";
  private static final int FONT_STYLE = Font.PLAIN;
  private static final Color DARK_GRAY = new Color(103, 103, 103);
  private static final Color LIGHT_GRAY = new Color(166, 166, 166);

  private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

  private JButton calculateButton;
  private JButton clearButton;

  private JLabel titleLabel;

  private JList<String> percentIncreaseList;
  private JList<String> percentDecreaseList;
  private JList<String> pointIncreaseList;
  private JList<String> pointDecreaseList;

  private JPanel upperPanel;
  private JPanel lowerPanel;

  private JPanel upperCentralPanel;
  private JPanel timeSpanPanel;

  private JRadioButton realDaysButton;
  private JRadioButton marketDaysButton;

  private JTextField textField;

  /**
   * The constructor.
   */
  public StockMarketStatsWindow()
  {
    initializeVariables();

    this.setLayout(new GridLayout(2, 0, 20, 20));
    this.setSize(screenSize.width - 20, screenSize.height - 20);
    this.setBackground(DARK_GRAY);
    this.getRootPane().setBorder(BorderFactory.createEmptyBorder(100, 30, 30, 30));

    add(upperPanel);
    add(lowerPanel);

    addToPanels();
    setAttributes();

    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }

  private void addToPanels()
  {
    upperPanel.add(titleLabel);

    upperPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
    upperPanel.add(textField);
    upperPanel.add(upperCentralPanel);

    upperCentralPanel.add(new JLabel());
    upperCentralPanel.add(new JLabel());
    upperCentralPanel.add(calculateButton);
    upperCentralPanel.add(clearButton);
    upperCentralPanel.add(new JLabel());
    upperCentralPanel.add(timeSpanPanel);
    
    timeSpanPanel.add(realDaysButton);
    timeSpanPanel.add(marketDaysButton);

    lowerPanel.add(percentIncreaseList);
    lowerPanel.add(percentDecreaseList);
    lowerPanel.add(pointIncreaseList);
    lowerPanel.add(pointDecreaseList);
  }

  private void initializeVariables()
  {
    titleLabel = new JLabel("Stock Market Stats", SwingConstants.CENTER);

    upperPanel = new JPanel(new GridLayout(4, 0, 50, 20));
    lowerPanel = new JPanel(new GridLayout(0, 4, 20, 20));

    upperCentralPanel = new JPanel(new GridLayout(0, 6, 50, 0));
    timeSpanPanel = new JPanel(new GridLayout(2, 0));

    calculateButton = new JButton("Calculate");
    clearButton = new JButton("Clear");
    realDaysButton = new JRadioButton("Real Days");
    marketDaysButton = new JRadioButton("Market Days");

    percentIncreaseList = new JList<>();
    percentDecreaseList = new JList<>();
    pointIncreaseList = new JList<>();
    pointDecreaseList = new JList<>();

    textField = new JTextField("Enter the Yahoo Finance historical data link address");
  }

  private void setAttributes()
  {
    setColors(this.getRootPane());
    titleLabel.setFont(new Font(FONT_NAME, FONT_STYLE, 60));
    textField.setFont(new Font(FONT_NAME, FONT_STYLE, 30));
    setTextFieldFocusListener();

    setBorderHelper(percentIncreaseList, "Percent Increase");
    setBorderHelper(percentDecreaseList, "Percent Decrease");
    setBorderHelper(pointIncreaseList, "Point Increase");
    setBorderHelper(pointDecreaseList, "Point Decrease");
  }

  private void setBorderHelper(JComponent component, String titleName)
  {
    component.setBorder(BorderFactory.createTitledBorder(null, titleName, 2, 2,
        new Font(FONT_NAME, FONT_STYLE, 20), Color.WHITE));
  }

  private void setColors(Container container)
  {
    Component[] comps = container.getComponents();
    for (Component comp : comps)
    {
      if (comp instanceof JPanel)
      {
        comp.setBackground(DARK_GRAY);
      }
      else
      {
        comp.setBackground(LIGHT_GRAY);
        comp.setForeground(Color.WHITE);
      }
      setColors((Container) comp);
    }
  }

  private void setTextFieldFocusListener()
  {
    textField.addFocusListener(new FocusListener()
    {
      @Override
      public void focusGained(FocusEvent e)
      {
        if (textField.getText().equals("Enter the Yahoo Finance historical data link address"))
        {
          textField.setText("");
          textField.setForeground(Color.WHITE);
        }
      }

      @Override
      public void focusLost(FocusEvent e)
      {
        if (textField.getText().isEmpty())
        {
          textField.setForeground(Color.GRAY);
          textField.setText("Enter the Yahoo Finance historical data link address");
        }
      }
    });
  }

}
