package maze;

public class Maze {
	
	private boolean[][] maze;
	private boolean[][] isVisited;
	private boolean[][] way;
	
	public Maze(){
		
	}
	
	public Maze(boolean[][] maze){
		this.maze = maze;
	}
	
	public void setMaze(boolean[][] maze){
		this.maze = maze;
	}
	
	public boolean[][] getIsVisited() {
		return isVisited;
	}

	public void setIsVisited(boolean[][] isVisited) {
		this.isVisited = isVisited;
	}

	public boolean[][] getWay() {
		return way;
	}

	public void setWay(boolean[][] way) {
		this.way = way;
	}

	public boolean[][] getMaze() {
		return maze;
	}

	public  boolean[][] readlyFondway() {
		 
		isVisited = new boolean[maze.length][maze[0].length];// 记录那些路是能够再走的
		
		for (int i = 0; i < maze.length; i++) {
			System.arraycopy(maze[i], 0, isVisited[i], 0, maze[i].length);
		}
		
		 way = new boolean[maze.length][maze[0].length];// 行走路线
		 
		int i = 0;//起点
		int j = 0;
		
		if (fond(i, j)) {
			return way;
		} else {
			return null;
		}

	}
	
	public  boolean fond(int i, int j
			) {
		isVisited[i][j] = false;// 将当前的位置设置为不可再走
		if (i == 7 && j == 7) {
			way[i][j] = true;
			return true;
		} 
		else {
			if ((j + 1 < 8) && isVisited[i][j + 1]) {// 如果可以往右走，而且右方没有越界
				if (fond(i, j + 1)) {// 如果往这条路能够走到结尾
					way[i][j] = true;
					return true;
			}
		}
			if ((i + 1 < 8) && isVisited[i + 1][j]) {// 如果可以往下走，而且下方没有越界
				if (fond(i + 1, j)) {// 如果往这条路能够走到结尾
					way[i][j] = true;
					return true;
				} 
			}
			
			if ((j - 1 >= 0) && isVisited[i][j - 1]) {// 如果可以往左走，而且左方没有越界
						if (fond(i, j - 1)) {// 如果往这条路能够走到结尾
							way[i][j] = true;
							return true;
						}
					}
			if ((i - 1 >= 0) && isVisited[i - 1][j]) {// 如果可以往上走，而且上方没有越界
						if (fond(i - 1, j)) {// 如果往这条路能够走到结尾
							way[i][j] = true;
							return true;
						}
				} 
		}
		return false;
	}
}
