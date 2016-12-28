package maze;

import java.util.ArrayList;

public class Rrcursion
{
	static ArrayList<ArrayList<String>> route = new ArrayList<ArrayList<String>>();
	static ArrayList<String> temp = new ArrayList<String>();
	static boolean findWay;
	public static void findPath(boolean labyrinth[][], int x, int y)
	{
		if (x == labyrinth.length - 1 && y == labyrinth[x].length - 1)
		{
			findWay = true;
			System.out.println("找到出口");
		}
		if(findWay)
		{
			return;
		}
		if (x + 1 < labyrinth.length && y < labyrinth[x + 1].length && !labyrinth[x + 1][y])
		{
			labyrinth[x + 1][y] = true;
				findPath(labyrinth, x + 1, y);
		}
		if (y + 1 < labyrinth[x].length && x < labyrinth.length  && !labyrinth[x][y + 1])
		{
			labyrinth[x][y + 1] = true;
			findPath(labyrinth, x, y + 1);
		}
		if (x - 1 < labyrinth.length && x - 1 > 0 && y < labyrinth[x - 1].length && !labyrinth[x - 1][y])
		{
			labyrinth[x - 1][y] = true;
			findPath(labyrinth, x - 1, y);
		}
		if (y - 1 > 0 && y - 1 < labyrinth[x].length && x < labyrinth.length && !labyrinth[x][y - 1])
		{
			labyrinth[x][y - 1] = true;
			findPath(labyrinth, x, y - 1);
		}
		if (x == 0 && y == 0 && !findWay)
		{
			System.out.println("此迷宫没有出口");
			return;
		}
	}
	
	public static void main(String[] args)
	{
		boolean a[][] = new boolean[9][8];
		a[0][2] = true;
		a[0][6] = true;
		a[1][2] = true;
		a[1][6] = true;
		a[2][4] = true;
		a[2][5] = true;
		a[2][7] = true;
		a[3][1] = true;
		a[3][2] = true;
		a[3][3] = true;
		a[3][6] = true;
		a[4][3] = true;
		a[5][1] = true;
		a[5][5] = true;
		a[5][7] = true;
		a[6][1] = true;
		a[6][2] = true;
		a[6][3] = true;
		a[6][4] = true;
		a[6][7] = true;
		a[7][0] = true;
		a[7][1] = true;
		a[7][5] = true;
		a[7][7] = true;
		a[8][0] = true;
		a[8][1] = true;
		findPath(a, 0, 0);
	}
	
	@SuppressWarnings("unchecked")
	public static void findAllWays(Position[][] labyrinth, int x, int y)
	{
		labyrinth[x][y].setAttribute(-1);
		if(x == labyrinth.length - 1 && y == labyrinth[labyrinth.length - 1].length - 1)
		{
			temp.add(labyrinth[x][y].toString());
			ArrayList<String> temp1 = (ArrayList<String>)temp.clone();
			route.add(temp1);
			temp.remove(temp.size() - 1);
			labyrinth[x][y].setAttribute(0);
			return;
		}
		
		if(y + 1 < labyrinth[x].length && x < labyrinth.length
				&& labyrinth[x][y + 1].getAttribute() == 0)
		{
			labyrinth[x][y].setLeaveDirection("right");
			temp.add(labyrinth[x][y].toString());
			findAllWays(labyrinth, x, y + 1);
		}
		if(x + 1 < labyrinth.length && y < labyrinth[x + 1].length
				&& labyrinth[x + 1][y].getAttribute() == 0)
		{
			labyrinth[x][y].setLeaveDirection("down");
			temp.add(labyrinth[x][y].toString());
			findAllWays(labyrinth, x + 1, y);
		}
		if(y - 1 > 0 && y - 1 < labyrinth[x].length
				&& x < labyrinth.length
				&& labyrinth[x][y - 1].getAttribute() == 0)
		{
			labyrinth[x][y].setLeaveDirection("left");
			temp.add(labyrinth[x][y].toString());
			findAllWays(labyrinth, x, y - 1);
		}
		if(x - 1 < labyrinth.length && x - 1 > 0
				&& y < labyrinth[x - 1].length
				&& labyrinth[x - 1][y].getAttribute() == 0)
		{
			labyrinth[x][y].setLeaveDirection("up");
			temp.add(labyrinth[x][y].toString());
			findAllWays(labyrinth, x - 1, y);
		}
		
		labyrinth[x][y].setAttribute(0);
	}
}
