package gatherfame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;

public class LinkedListGUI extends JFrame {
	private LinkedList<Integer> list = new LinkedList<Integer>();
	private LinkedListView view = new LinkedListView();
	private JButton jbtSearch = new JButton("Search");
	private JButton jbtInsert = new JButton("Insert");
	private JButton jbtDelete = new JButton("Delete");
	private JTextField jtfNumber = new JTextField(5);
	private JTextField jtfIndex = new JTextField(5);
	private JScrollPane srollPane;

	public LinkedListGUI() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Enter a value"));
		panel.add(jtfNumber);
		panel.add(new JLabel("Enter an index"));
		panel.add(jtfIndex);
		panel.add(jbtSearch);
		panel.add(jbtInsert);
		panel.add(jbtDelete);

		srollPane = new JScrollPane(view);
		srollPane.setPreferredSize(new Dimension(600, 150));
		add(srollPane);
		add(panel, BorderLayout.SOUTH);
		jbtDelete.addActionListener(new ButtonListener());
		jbtInsert.addActionListener(new ButtonListener());
		jbtSearch.addActionListener(new ButtonListener());
	}

	class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == jbtSearch) {
				if (!list.contains(Integer.parseInt(jtfNumber.getText()))) {
					JOptionPane.showMessageDialog(null,
							"key is not in the list");
				} else {
					if (jtfNumber.getText() != null
							&& !jtfNumber.getText().equals(""))
						view.search(Integer.parseInt(jtfNumber.getText()));// 选中
				}
				 fresh();
			}

			if (e.getSource() == jbtInsert) {
				if (jtfIndex.getText().trim().length() > 0)
					list.add(Integer.parseInt(jtfIndex.getText()),
							Integer.parseInt(jtfNumber.getText()));
				else
					list.add(Integer.parseInt(jtfNumber.getText()));
				 fresh();
				 view.search(Integer.MAX_VALUE);//复位寻找元素
			}

			if (e.getSource() == jbtDelete) {

				if (!list.contains(Integer.parseInt(jtfNumber.getText()))) {
					JOptionPane.showMessageDialog(null,
							"key is not in the list");
				} else {
					list.remove(new Integer(Integer.parseInt(jtfNumber
							.getText())));
					 fresh();
				}
				view.search(Integer.MAX_VALUE);//复位寻找元素
			}
		}

	}

	public void fresh() {
		view.repaint();
		srollPane.repaint();
		this.doLayout();
	}

	class LinkedListView extends JPanel {
		private int startingX = 20;
		private int startingY = 20;
		private int boxWidth = 50;
		private int boxHeight = 20;
		private int arrowLineLength = 30;
		private int hGap = 80;
		private int searchNode = Integer.MAX_VALUE;

		public LinkedListView() {
			this.setPreferredSize(new Dimension(600, 150));
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			if ((list.size() + 2) * (boxWidth + arrowLineLength)> 600) {
				this.setPreferredSize(new Dimension(
						(list.size() + 2) * (boxWidth + arrowLineLength), 150));
			}

			if (list.size() == 0) {
				g.drawString("head: null", startingX, startingY);
				g.drawString("tail: null", startingX, startingY + 15);
			} else {
				g.drawString("head", startingX, startingY);

				int x = startingX + 30;// 矩形的开始位置
				int y = startingY + 20;
				drawArrowLine(startingX + 5, startingY, x, y, g);
				g.setColor(Color.BLACK);

				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).equals(searchNode))
						g.setColor(Color.red);
					g.drawRect(x, y, boxWidth, boxHeight);
					g.drawLine(x + arrowLineLength, y, x + arrowLineLength, y
							+ boxHeight);// 画出中间的竖线
					g.setColor(Color.RED);

					if (i < list.size() - 1)// 在倒数的二个以前的矩形的尾巴上都加一个箭头
						drawArrowLine(x + 40, y + boxHeight / 2, x + hGap, y
								+ boxHeight / 2, g);// 水平的箭头
					g.setColor(Color.BLACK);
					g.drawString(list.get(i) + "", x + 10, y + 15);
					x = x + hGap;
				}
				
				g.drawString("tail", x, startingY);
				drawArrowLine(x, startingY, x - hGap, y, g);// 反向的箭头
			}
		}

		public void search(int index) {
			this.searchNode = index;

		}
	}

	public static void drawArrowLine(int x1, int y1, int x2, int y2, Graphics g) {
		g.setColor(Color.red);
		g.drawLine(x1, y1, x2, y2);

		// find slope of this line
		double slope = ((((double) y1) - (double) y2))
				/ (((double) x1) - (((double) x2)));

		double arctan = Math.atan(slope);// arctan(slope)为后面做准备

		double set45 = 1.57 / 2;// 45度角的弧度主要针对头部箭头

		if (x1 < x2) {// 主要反向箭头
			// add 90 degrees to arrow lines
			set45 = -1.57 * 1.5;
		}

		int arrlen = 10;// 箭头变得长度

		// 三角箭头
		g.drawLine(x2, y2, (int) ((x2 + (Math.cos(arctan + set45) * arrlen))),
				(int) (((y2)) + (Math.sin(arctan + set45) * arrlen)));

		g.drawLine(x2, y2, (int) ((x2 + (Math.cos(arctan - set45) * arrlen))),
				(int) (((y2)) + (Math.sin(arctan - set45) * arrlen)));
	}

	public static void main(String[] args) {
		LinkedListGUI frame = new LinkedListGUI();
		frame.setTitle("链表");
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
