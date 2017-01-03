package gatherfame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class QueueGUI extends JFrame {
	private LinkedList<Integer> queue = new LinkedList<Integer>();
	private QueueView view = new QueueView();
	private JButton jbtInsert = new JButton("Insert (enqueue)");
	private JButton jbtDelete = new JButton("Delete (dequeue)");
	private JTextField jtfNumber = new JTextField(2);
	private JScrollPane srollPane;
	public QueueGUI() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Enter a value: "));
		panel.add(jtfNumber);
		panel.add(jbtInsert);
		panel.add(jbtDelete);
		
		srollPane = new JScrollPane(view);
		srollPane.setPreferredSize(new Dimension(600, 150));
		add(srollPane);
		
		add(panel, BorderLayout.SOUTH);
		jbtDelete.addActionListener(new ButtonListener());
		jbtInsert.addActionListener(new ButtonListener());
	}

	class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == jbtDelete) {
				queue.removeFirst();
				view.repaint();
			}

			if (e.getSource() == jbtInsert) {
				queue.addLast(Integer.parseInt(jtfNumber.getText()));
				view.repaint();
			}
			
		}

		

	}

	public class QueueView extends JPanel {
		private int startingX = 20;
		private int startingY = 20;
		private int boxWidth = 30;
		private int boxHeight = 20;
		public QueueView(){
			this.setPreferredSize(new Dimension(600, 150));
		}
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			if ((queue.size() + 1) * boxWidth > 600) {
				this.setPreferredSize(new Dimension(
						(queue.size() + 1) * boxWidth, 150));
			}
			
			if (queue.size() == 0) {
				g.drawString("queue is empty", startingX, startingY);
			} else {
				g.drawString("queue", startingX, startingY);
				
				int x = startingX + 10;
				int y = startingY + 10;
				drawArrowLine(startingX + 5, startingY, x, y, g);
				for (int i = 0; i < queue.size(); i++) {
					g.setColor(Color.BLACK);
					g.drawRect(x, y, boxWidth, boxHeight);
					g.drawString(queue.get(i) + "", x + 10, y + 15);
					x = x + boxWidth;
				}
				g.drawString("tail", x, startingY);
				drawArrowLine(x, startingY, x - boxWidth + 5, y, g);// 反向的箭头
			}
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
		QueueGUI frame = new QueueGUI();
		frame.setTitle("队列");
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
