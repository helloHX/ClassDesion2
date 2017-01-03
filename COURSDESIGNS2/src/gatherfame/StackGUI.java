package gatherfame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.*;

public class StackGUI extends JFrame {
	private Stack<Integer> stack = new Stack<Integer>();
	private StackView view = new StackView();
	private JButton jbtInsert = new JButton("Insert (push)");
	private JButton jbtDelete = new JButton("Delete (pop)");
	private JTextField jtfNumber = new JTextField(2);
	private JScrollPane srollPane;
	public StackGUI() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Enter a value: "));
		panel.add(jtfNumber);
		panel.add(jbtInsert);
		panel.add(jbtDelete);
		srollPane = new JScrollPane(view);
		srollPane.setPreferredSize(new Dimension(300, 400));
		add(srollPane);
		add(panel, BorderLayout.SOUTH);
		jbtDelete.addActionListener(new ButtonListener());
		jbtInsert.addActionListener(new ButtonListener());
	}
	
	class ButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==jbtDelete){
				stack.pop(); 
		        view.repaint();
			}
			
			if(e.getSource()==jbtInsert){
				stack.push(Integer.parseInt(jtfNumber.getText()));
		        view.repaint();
			}
			
		}
	}

	public class StackView extends JPanel {
		private int startingX = 20;
		private int startingY = 20;
		private int boxWidth = 30;
		private int boxHeight = 20;

		public StackView(){
			this.setPreferredSize(new Dimension(300, 400));
		}
		
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			if ((stack.size() + 1) * boxHeight > 400) {
				this.setPreferredSize(new Dimension(
						300,(stack.size() + 1) * boxHeight));
			}
			
			if (stack.size() == 0) {
				g.drawString("stack is empty", startingX, startingY);
			} else {
				int x = startingX + 30;
				int y = startingY + 10;
				
				ArrayList<Integer> list = new ArrayList<Integer>(stack);

				for (int i = list.size() - 1; i >= 0; i--) {
					g.setColor(Color.BLACK);
					g.drawRect(x, y, boxWidth, boxHeight);
					g.drawString(list.get(i) + "", x + 10, y + 15);
					y = y + boxHeight;
				}
				
				g.drawString("top", x, startingY);
				drawArrowLine(startingX, startingY, x, startingY + 10, g);// 反向的箭头
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
		StackGUI frame = new StackGUI();
		frame.setTitle("栈");
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
