package maze;

public class Onrecurrent
{
	public static Position[][] creatLabyrinth(int[][] blueprint)
	{
		Position[][] labyrinth = new Position[blueprint.length][];
		for (int i = 0; i < blueprint.length; i++)
		{
			for (int j = 0; j < blueprint[i].length; j++)
			{
				if (blueprint[i][j] == 0)
				{
					labyrinth[i][j] = new Position(i, j, 0);
				} else
				{
					labyrinth[i][j] = new Position(i, j, 1);
				}
			}
		}
		return labyrinth;
	}

	
}
