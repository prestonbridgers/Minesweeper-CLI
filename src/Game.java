import java.util.Scanner;

/**
 * Class for game.
 * 
 * @author Curt Bridgers
 * @version 1/27/2019
 */
public class Game
{
	public static Map map;

	/**
	 * Constructs the object.
	 */
	public Game(int width, int height, int bombCount)
	{
		map = new Map(width, height, bombCount);
	}

	public void playGame()
	{
		map.print();

		Scanner scan = new Scanner(System.in);
		char move;

		for (;;)
		{
			move = scan.nextLine().toLowerCase().charAt(0);

			switch (move)
			{
				case 'w':
					map.cursor.moveUp();
					break;
				case 's':
					map.cursor.moveDown();
					break;
				case 'a':
					map.cursor.moveLeft();
					break;
				case 'd':
					map.cursor.moveRight();
					break;
				case 'x':
					if (!map.flagged[map.cursor.getY()][map.cursor.getX()])
						map.cursor.excavate();
					break;
				case 'f':
					map.toggleFlag(map.cursor.getX(), map.cursor.getY());
					break;
				case 'q':
					System.exit(0);
					break;
			}

			if (won())
			{
				System.out.println("You flagged all the bombs");
				System.exit(0);
			}
			map.print();
		}
	}

	/**
	 * Checks if a win condition is met and ends the game if so.
	 */
	public boolean won()
	{
		int flaggedBombs = 0;
		for (int row = 0; row < map.getHeight(); row++)
		{
			for (int col = 0; col < map.getWidth(); col++)
			{
				if (map.bomb[row][col] && map.flagged[row][col])
				{
					flaggedBombs++;
				}
			}
		}

		if (flaggedBombs == map.getBombCount())
		{
			return true;
		}
		
		return false;
	}
}