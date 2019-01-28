import java.util.Random;

/**
 * Class for map.
 * 
 * @author Curt Bridgers
 * @version 1/27/2019
 */
public class Map
{
	public static final char TILE_BOMB = '*';
	public static final char TILE_EMPTY = ' ';
	public static final char TILE_HIDDEN = '#';
	public static final char TILE_FLAGGED = '!';

	private final int height;
	private final int width;
	private final int bombCount;

	public static char[][] map;
	public static boolean[][] bomb;
	public static boolean[][] revealed;
	public static boolean[][] flagged;

	public static Cursor cursor;

	/**
	 * Constructs the map object.
	 *
	 * @param      height     The height
	 * @param      width      The width
	 * @param      bombCount  The bomb count
	 */
	public Map(int height, int width, int bombCount)
	{
		this.height = height;
		this.width = width;
		this.bombCount = bombCount;

		map = new char[height][width];
		bomb = new boolean[height][width];
		revealed = new boolean[height][width];
		flagged = new boolean[height][width];
		cursor = new Cursor(0, 0);

		generateMap();
	}

	/**
	 * Gets the width.
	 *
	 * @return     The width.
	 */
	public int getWidth()
	{
		return this.width;
	}

	/**
	 * Gets the height.
	 *
	 * @return     The height.
	 */
	public int getHeight()
	{
		return this.height;
	}

	/**
	 * Gets the bomb count.
	 *
	 * @return     The bomb count.
	 */
	public int getBombCount()
	{
		return this.bombCount;
	}

	/**
	 * Generates the map;
	 */
	private void generateMap()
	{
		Random rand = new Random();

		for (int i = 0; i < bombCount; i++)
		{
			int randX = rand.nextInt(width);
			int randY = rand.nextInt(height);

			bomb[randY][randX] = true;
		}

		initMap();

		for (int row = 0; row < height; row++)
		{
			for (int col = 0; col < width; col++)
			{
				if (!bomb[row][col])
				{
					if (getAdjacentBombCount(col, row) != 0)
					{
						map[row][col] = String.valueOf(getAdjacentBombCount(col, row)).charAt(0);
					}
				}
			}
		}
	}

	/**
	 * Initializes the map.
	 */
	private void initMap()
	{
		for (int row = 0; row < height; row++)
		{
			for (int col = 0; col < width; col++)
			{
				if (bomb[row][col])
				{
					map[row][col] = TILE_BOMB;
				}
				else
				{
					map[row][col] = TILE_EMPTY;
				}
			}
		}
	}

	/**
	 * Gets the adjacent bomb count.
	 *
	 * @param      x     X position to check.
	 * @param      y     Y position to check.
	 */
	private int getAdjacentBombCount(int x, int y)
	{
		int count = 0;

		//TOP LEFT
		if (x != 0 && y != 0 &&
			bomb[y - 1][x - 1])
		{
			count++;
		}

		//TOP
		if (y != 0 &&
			bomb[y - 1][x])
		{
			count++;
		}

		//TOP RIGHT
		if (x != width - 1 && y != 0 &&
			bomb[y - 1][x + 1])
		{
			count++;
		}

		//MIDDLE LEFT
		if (x != 0 &&
			bomb[y][x - 1])
		{
			count++;
		}

		//MIDDLE RIGHT
		if (x != width - 1 &&
			bomb[y][x + 1])
		{
			count++;
		}

		//BOTTOM LEFT
		if (x != 0 && y != height - 1 &&
			bomb[y + 1][x - 1])
		{
			count++;
		}

		//BOTTOM
		if (y != height - 1 &&
			bomb[y + 1][x])
		{
			count++;
		}

		//BOTTOM RIGHT
		if (x != width - 1 && y != height - 1 &&
			bomb[y + 1][x + 1])
		{
			count++;
		}

		return count;
	}

	/**
	 * Prints the map.
	 */
	public void print()
	{
		printVertBorder();
		for (int row = 0; row < height; row++)
		{
			System.out.print('|');
			for (int col = 0; col < width; col++)
			{
				if (row == cursor.getY() &&
					col == cursor.getX())
				{
					System.out.print(Cursor.TILE);
				}
				else if (flagged[row][col])
				{
					System.out.print(TILE_FLAGGED);
				}
				else if (revealed[row][col])
				{
					System.out.print(map[row][col]);
				}
				else
				{
					System.out.print(TILE_HIDDEN);
				}
			}
			System.out.println('|');
		}
		printVertBorder();
	}

	/**
	 * Helper method for printing the vertical border of the map.
	 */
	private void printVertBorder()
	{
		System.out.print('*');
		for (int i = 0; i < width; i++)
		{
			System.out.print('-');
		}
		System.out.println('*');
	}

	/**
	 * Reveals the map recursively untill certain conditions are met.
	 *
	 * @param      x     The starting x.
	 * @param      y     The starting y.
	 */
	public void reveal(int x, int y)
	{
		if (!bomb[y][x] && !revealed[y][x] && map[y][x] == TILE_EMPTY)
		{
			revealed[y][x] = true;

			if (x != 0 && y != 0) //TOP LEFT
				reveal(x - 1, y - 1);

			if (y != 0) //TOP
				reveal(x, y - 1);

			if (x != width - 1 && y != 0) //TOP RIGHT
				reveal(x + 1, y - 1);

			if (x != 0) //MIDDLE LEFT
				reveal(x - 1, y);

			if (x != width - 1) //MIDDLE RIGHT
				reveal(x + 1, y);

			if (x != 0 && y != height - 1) //BOTTOM LEFT
				reveal(x - 1, y + 1);

			if (y != height - 1) //BOTTOM
				reveal(x, y + 1);

			if (x != width - 1 && y != height - 1) //BOTTOM RIGHT
				reveal(x + 1, y + 1);
		}
		else
		{
			revealed[y][x] = true;
		}
	}

	/**
	 * Toggles if a tile is flagged
	 *
	 * @param      x     The x of the tile.
	 * @param      y     The y of the tile.
	 */
	public void toggleFlag(int x, int y)
	{
		if (flagged[y][x])
		{
			flagged[y][x] = false;
		}
		else
		{
			flagged[y][x] = true;
		}
	}
}