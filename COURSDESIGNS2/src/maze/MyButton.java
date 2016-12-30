package maze;

import javax.swing.JButton;

public class MyButton extends JButton{
	
	private int row;
	private int col;
	private boolean isChoiced;//是否被选中
	
	public boolean isChoiced() {
		return isChoiced;
	}

	public void setChoiced(boolean isChoiced) {
		this.isChoiced = isChoiced;
	}

	public MyButton(int row,int col){
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	
	
}
