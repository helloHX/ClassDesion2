package maze;

import java.io.Serializable;

public class MazeNode implements Serializable{
	private boolean[][] way;//通路
	private boolean[][] isVisited;//访问过的点
	private boolean[][] maze;//迷宫的初始样式
	public MazeNode(boolean[][] way,boolean[][] isVisited,boolean[][] maze){
		this.way = way;
		this.isVisited = isVisited;
		this.maze = maze;
	}
	
	public boolean[][] getWay() {
		return way;
	}
	public void setWay(boolean[][] way) {
		this.way = way;
	}
	public boolean[][] getIsVisited() {
		return isVisited;
	}
	public void setIsVisited(boolean[][] isVisited) {
		this.isVisited = isVisited;
	}
	public boolean[][] getMaze() {
		return maze;
	}
	public void setMaze(boolean[][] maze) {
		this.maze = maze;
	}
}
