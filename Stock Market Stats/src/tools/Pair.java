package tools;

/**
 * A class to help organizing a pair of values.
 * 
 * @author Chad Martin
 * @version 30 April 2020
 *
 * @param <O>
 *          The market open value
 * @param <C>
 *          The market close value
 */
public class Pair<O, C>
{
  private O open;
  private C close;

  /**
   * The constructor.
   * 
   * @param open
   *          The market open value
   * @param close
   *          The market close value
   */
  public Pair(O open, C close)
  {
    this.open = open;
    this.close = close;
  }

  /**
   * Get the market open value.
   * 
   * @return The open value
   */
  public O getOpen()
  {
    return open;
  }

  /**
   * Get the market close value.
   * 
   * @return The close value
   */
  public C getClose()
  {
    return close;
  }

}
