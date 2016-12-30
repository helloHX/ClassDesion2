package maze;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class DataPanel extends JPanel {
	private LinkedList<MazeNode> mazeList; // ���ļ��м��س�����list
	private JLabel[] label;
	private MazeNode currentMazeNode;
	private MazeMap mazemap;
	private int choiced;
	public DataPanel(MazeMap mazemap) {
		this.setLayout(null);
		this.mazemap = mazemap;
		this.setPreferredSize(new Dimension(150, 600));
	}

	public int getChoiced() {
		return choiced;
	}

	public void setChoiced(int choiced) {
		this.choiced = choiced;
	}

	public void showDataPanel(LinkedList<MazeNode> mazeList) {//����list���������չʾ������ĵ�ͼ
		this.setPreferredSize(new Dimension(150, mazeList.size() * 60));
		this.removeAll();//�Ƴ�����״̬
	    label = new JLabel[mazeList.size()];
		this.mazeList = mazeList;
		LabelListener monitor = new LabelListener();
		for (int i = 0; i < mazeList.size(); i++)
		{
			label[i] = new JLabel("ͼ" + (i + 1));
			label[i].setFont(new Font("SansSerif", Font.ITALIC, 16));
			label[i].setIcon(new ImageIcon("card/maze.png"));
			label[i].setBounds(0, i * 50 + 5, 150, 45);//�趨����չʾ���ݵ�λ��
			label[i].addMouseListener(monitor);
			this.add(label[i]);
		}
		
	}
	
	class LabelListener extends MouseAdapter{
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			for (int i = 0; i < mazeList.size(); i++) {
				if(e.getSource() == label[i]){
					mazemap.fresh();
					currentMazeNode = mazeList.get(i);
					
					mazemap.fillWay(
							currentMazeNode.getWay(), 
							currentMazeNode.getIsVisited(),
							currentMazeNode.getMaze());
					
					choiced = i;//����ѡ�еı�ǩ
				}
			}
		}
		
		public void mouseEntered(MouseEvent e) {//��������Ƴ���Ч��
			for (int i = 0; i < mazeList.size(); i++) {
				if(e.getSource() == label[i]){
					label[i].setBackground(new Color(229, 243, 251));
					label[i].setBorder(BorderFactory.createLineBorder(
							new Color(112, 192, 231), 1));
					label[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			}
		}
		
		public void mouseExited(MouseEvent e) {
			for (int i = 0; i < mazeList.size(); i++) {
				if(e.getSource() == label[i]){
					label[i].setBackground(new Color(238, 238, 238));
					label[i].setBorder(null);
				}
			}
		}
	}
}
