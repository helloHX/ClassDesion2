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
		 
		isVisited = new boolean[maze.length][maze[0].length];// ��¼��Щ·���ܹ����ߵ�
		
		for (int i = 0; i < maze.length; i++) {
			System.arraycopy(maze[i], 0, isVisited[i], 0, maze[i].length);
		}
		
		 way = new boolean[maze.length][maze[0].length];// ����·��
		 
		int i = 0;//���
		int j = 0;
		
		if (fond(i, j)) {
			return way;
		} else {
			return null;
		}

	}
	
	public  boolean fond(int i, int j
			) {
		isVisited[i][j] = false;// ����ǰ��λ������Ϊ��������
		if (i == 7 && j == 7) {
			way[i][j] = true;
			return true;
		} 
		else {
			if ((j + 1 < 8) && isVisited[i][j + 1]) {// ������������ߣ������ҷ�û��Խ��
				if (fond(i, j + 1)) {// ���������·�ܹ��ߵ���β
					way[i][j] = true;
					return true;
			}
		}
			if ((i + 1 < 8) && isVisited[i + 1][j]) {// ������������ߣ������·�û��Խ��
				if (fond(i + 1, j)) {// ���������·�ܹ��ߵ���β
					way[i][j] = true;
					return true;
				} 
			}
			
			if ((j - 1 >= 0) && isVisited[i][j - 1]) {// ������������ߣ�������û��Խ��
						if (fond(i, j - 1)) {// ���������·�ܹ��ߵ���β
							way[i][j] = true;
							return true;
						}
					}
			if ((i - 1 >= 0) && isVisited[i - 1][j]) {// ������������ߣ������Ϸ�û��Խ��
						if (fond(i - 1, j)) {// ���������·�ܹ��ߵ���β
							way[i][j] = true;
							return true;
						}
				} 
		}
		return false;
	}
}
