package coinGame;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class InitialNodePanel extends JPanel {
	// 每一个cell都代表一个硬币，它可以被翻转
	ClickableCell[][] clickableCells = new ClickableCell[4][4];

	public InitialNodePanel() {
		this.setLayout(new GridLayout(4, 4));
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				add(clickableCells[i][j] = new ClickableCell("H"));
			}
		}
	}

	/** 4乘4矩阵图形用户界面 */
	public char[] getMatrix() {
		char[] initialNode = new char[16];
		int k = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (clickableCells[i][j].getText().equals("H")) {
					initialNode[k] = 'H';
					k++;
				} else {
					initialNode[k] = 'T';
					k++;
				}
			}
		}
		return initialNode;
	}
}