package gui;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class UtilityBar extends JMenuBar
{
  private static final long serialVersionUID = 1L;

  private JMenu helpMenu;
  private JMenuItem helpMenuItem, aboutMenuItem;

  public UtilityBar()
  {
    helpMenu = new JMenu("Help");
    helpMenuItem = new JMenuItem("Help");
    aboutMenuItem = new JMenuItem("About");

    this.add(helpMenu);
    helpMenu.add(helpMenuItem);
    helpMenu.add(aboutMenuItem);

    addListeners();
  }

  private void aboutButtonHelper()
  {
    String message = "<html> Currently under work! </html>";
    JOptionPane.showMessageDialog(null, new JLabel(message, SwingConstants.CENTER), "About",
        JOptionPane.PLAIN_MESSAGE);
  }

  private void addListeners()
  {
    helpMenuItem.addActionListener(e -> {
      helpButtonHelper();
    });

    aboutMenuItem.addActionListener(e -> {
      aboutButtonHelper();
    });
  }

  private void helpButtonHelper()
  {
    String message = "<html> Step 1: Search and go to Yahoo Finance on the Internet <br/>"
        + "Step 2: Enter any ticker in the Yahoo Finance search bar (Examples: \"TSLA\", \"AMZN\", \"AAPL\") <br/>"
        + "Step 3: Click on the \"Historical Data\" button located near the center of the screen <br/>"
        + "Step 4: Click the \"Time Period\" drop down button and select any date (Preferably \"Max\") <br/>"
        + "Step 5: Click the \"Apply\" button <br/>"
        + "Step 6: Right click the \"Download\" button <br/>"
        + "Step 7: Click the \"Copy link address\" button <br/>"
        + "Step 8: Paste the link address in the Stock Market Volatility search bar <br/>"
        + "Step 9: Choose the day span, number of results, day type, and overlap dates option to your preference <br/>"
        + "Step 10: Click the \"Calculate\" button <br/> </html>";
    JOptionPane.showMessageDialog(null, new JLabel(message, SwingConstants.CENTER), "Help",
        JOptionPane.PLAIN_MESSAGE);
  }

}
