package maze;

import java.io.Serializable;

public class Position implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int x, y, attribute;              //0代表没走过可通过，1代表障碍，-1代表在该位置上走过（该位置不为障碍）
	String leaveDirection = "";     //从何方向离开此位置
	
	public Position()
	{
		
	}
	
	public Position(int x, int y, int attribute)
	{
		this.x = x;
		this.y = y;
		this.attribute = attribute;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getAttribute()
	{
		return attribute;
	}

	public void setAttribute(int attribute)
	{
		this.attribute = attribute;
	}
	
	public String getLeaveDirection()
	{
		return leaveDirection;
	}
	
	public void setLeaveDirection(String leaveDirection)
	{
		this.leaveDirection = leaveDirection;
	}
	
	public String toString()
	{
		return "(" + x + ", " + y + ", " + leaveDirection + ")";
	}
}

