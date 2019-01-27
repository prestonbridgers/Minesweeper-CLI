

/**
 * Class for cursor.
 * 
 * @author Curt Bridgers
 * @version 1/27/2019
 */
public class Cursor
{
	public static final char TILE = 'X';

	private int xPos;
	private int yPos;

	/**
	 * Constructs the Cursor object.
	 *
	 * @param      xPos  The x position
	 * @param      yPos  The y position
	 */
	public Cursor(int xPos, int yPos)
	{
		this.xPos = xPos;
		this.yPos = yPos;
	}

	/**
	 * Gets the x.
	 *
	 * @return     The x.
	 */
	public int getX()
	{
		return this.xPos;
	}

	/**
	 * Gets the y.
	 *
	 * @return     The y.
	 */
	public int getY()
	{
		return this.yPos;
	}

	/**
	 * Moves the cursor right, if able.
	 */
	public void moveUp()
	{
		if (yPos != 0)
		{
			this.yPos--;
		}
	}

	/**
	 * Moves the cursor right, if able.
	 */
	public void moveDown()
	{
		if (yPos != Game.map.getHeight() - 1)
		{
			this.yPos++;
		}
	}

	/**
	 * Moves the cursor right, if able.
	 */
	public void moveLeft()
	{
		if (xPos != 0)
		{
			this.xPos--;
		}
	}

	/**
	 * Moves the cursor right, if able.
	 */
	public void moveRight()
	{
		if (xPos != Game.map.getWidth() - 1)
		{
			this.xPos++;
		}
	}


	/**
	 * Excavates the map from the cursor location outward.
	 */
	public void excavate()
	{
		char[][] map = Game.map.map;
		boolean[][] bomb = Game.map.bomb;
		boolean[][] revealed = Game.map.revealed;

		if (bomb[yPos][xPos])
		{
			System.out.println("You died");
			System.exit(0);
		}
		else if (!revealed[yPos][xPos] && !bomb[yPos][xPos])
		{
			Game.map.reveal(xPos, yPos);
		}
	}
}