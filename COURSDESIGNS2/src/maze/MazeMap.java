package maze;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MazeMap extends JPanel {
	private boolean isVrify;
	private Maze maze;
	private MyButton point[][] = new MyButton[8][8];
	private boolean[][] mazenMap;

	public MazeMap() {//�����Թ���ͼ��ʼ����ͼ��Ϣ

		mazenMap = new boolean[8][8];
		this.setLayout(new GridLayout(8, 8, 0, 0));
		this.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
		for (int i = 0; i < point.length; i++) {
			for (int j = 0; j < point[i].length; j++) {
				mazenMap[i][j] = true;
				point[i][j] = new MyButton(i, j);
				point[i][j].setBackground(new Color(127, 255, 170));
				point[i][j].addActionListener(new ButtonListener());
				this.add(point[i][j]);
			}
		}
		fresh();

	}

	public void fresh() {//��ʼ����ͼ����ʽ
		mazenMap = new boolean[8][8];
		for (int i = 0; i < point.length; i++) {
			for (int j = 0; j < point[i].length; j++) {
				mazenMap[i][j] = true;
				point[i][j].setIcon(null);
			}
		}
		point[0][0].setIcon(new ImageIcon("card/man.png"));
		point[7][7].setIcon(new ImageIcon("card/exit.png"));
	}

	public boolean Vrify() {//��֤�û�����ĵ�ͼ�е�Ч��
		maze = new Maze(this.mazenMap);
		maze.readlyFondway();//
		this.fillWay(getWay(), getIsVisited(),getMaze());
		this.isVrify = true;//����ͼ���Ƿ���֤��־�޸�
		return true;
	}

	public boolean[][] getIsVisited() {
		return maze.getIsVisited();
	}

	public boolean[][] getWay() {
		return maze.getWay();
	}

	public boolean[][] getMaze() {
		return maze.getMaze();
	}

	public boolean isVrify() {
		return isVrify;
	}

	public void setVriify(boolean isVrify) {
		this.isVrify = isVrify;
	}
	
	public void random(){//���������ͼ�е��ϰ�
		for (int i = 0; i < 15; i++) {
			int row = (int)(Math.random() * 7);
			int col = (int)(Math.random() * 7);
			if((row == 0 && col == 0)||(row == 7 && col == 7))//�����������յ��������
				continue;
			mazenMap[row][col] = false;
			point[row][col].setIcon(new ImageIcon(
					"card/barrier.png"));
		}
	}

	public void fillWay(boolean[][] way, boolean[][] isVisited, boolean[][] maze) {
		if (way == null) {//���ݵ�ͼ�����ƺ�ԭ�ʹ�����ͼUI
			JOptionPane.showMessageDialog(null, "��ͨ·", "��Ϣ",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		for (int i = 0; i < way.length; i++) {
			for (int j = 0; j < way.length; j++) {
				if (isVisited[i][j] == false && maze[i][j] == true)
					point[i][j].setIcon(new ImageIcon("card/blocking.png"));
				if (way[i][j] == true)
					point[i][j].setIcon(new ImageIcon("card/man.png"));
				if( maze[i][j] == false){
					point[i][j].setIcon(new ImageIcon("card/barrier.png"));
				}
			}
		}
		point[7][7].setIcon(new ImageIcon("card/exit.png"));
	}

	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == point[0][0]) {
				JOptionPane.showMessageDialog(null, "�������������ϰ�", "����",
						JOptionPane.ERROR_MESSAGE);
				return;
			} else if (e.getSource() == point[7][7]) {
				JOptionPane.showMessageDialog(null, "�������յ�����ϰ�", "����",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			for (int i = 0; i < point.length; i++) {
				for (int j = 0; j < point[i].length; j++) {
					if (point[i][j] == e.getSource()) {
						if (!point[i][j].isChoiced()) {
							point[i][j].setChoiced(true);
							mazenMap[i][j] = false;
							point[i][j].setIcon(new ImageIcon(
									"card/barrier.png"));
						} else {
							point[i][j].setChoiced(false);
							mazenMap[i][j] = true;
							point[i][j].setIcon(null);
						}
					}
				}
			}
		}

	}
}
