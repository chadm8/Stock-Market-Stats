package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class StockMarketStatsWindow extends JFrame
{
  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   */
  public StockMarketStatsWindow()
  {
    this.setLayout(new BorderLayout(40, 40));
    this.setSize(500, 500);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }

}
