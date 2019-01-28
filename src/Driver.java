

/**
 * Class for driver.
 * 
 * @author Curt Bridgers
 * @version 1/27/2019
 */
public class Driver
{
	/**
	 * The main method.
	 *
	 * @param      args  	Usage: java Driver (width) (height) (bombCount)
	 */
	public static void main(String[] args)
	{
		int width = Integer.valueOf(args[0]);
		int height = Integer.valueOf(args[1]);
		int bombCount = Integer.valueOf(args[2]);
		Game g = new Game(width, height, bombCount);
		g.playGame();
	}
}