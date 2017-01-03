package tree;

import java.awt.*;

import javax.swing.*;

import tree.BinaryTree.TreeNode;

import java.awt.event.*;

public class TreeControl extends JPanel {
	private BinaryTree<Integer> tree;
	private JTextField jtfKey = new JTextField(5);
	private TreeView view = new TreeView();
	private JButton jbtInset = new JButton("Insert");
	private JButton jbtDelete = new JButton("Delete");
	private JButton jbtSearch = new JButton("Search");

	public TreeControl(BinaryTree<Integer> tree) {
		this.tree = tree;
		setUI();
	}

	private void setUI() {
		this.setLayout(new BorderLayout());
		add(view, BorderLayout.CENTER);
		JPanel panel = new JPanel();
		panel.add(new JLabel("Enter a key"));
		panel.add(jtfKey);
		panel.add(jbtInset);
		panel.add(jbtDelete);
		panel.add(jbtSearch);

		add(panel, BorderLayout.SOUTH);

		jbtInset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int key = Integer.parseInt(jtfKey.getText());
				if (tree.search(key)) {
					JOptionPane.showMessageDialog(null, key
							+ " is already in the tree");
				} else {
					tree.insert(key);
					view.repaint();
				}
			}

		});

		jbtSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int key = Integer.parseInt(jtfKey.getText());
				if (!tree.search(key)) {
					JOptionPane.showMessageDialog(null, key
							+ " is not in the tree");
				} else {
					view.setSearchNode(key);
					view.repaint();
				}
			}
		});

		jbtDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int key = Integer.parseInt(jtfKey.getText());
				if (!tree.search(key)) {
					JOptionPane.showMessageDialog(null, key
							+ "si not in the tree");
				} else {
					tree.delete(key);
					view.repaint();
				}
			}
		});
	}

	class TreeView extends JPanel {
		private int radius = 20;
		private int vGap = 50;
		private  BinaryTree.TreeNode<Integer> SearchNode;
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;  //g是Graphics对象
			g2.setStroke(new BasicStroke(2.0f));//改变画线的粗细
			g.setFont(new Font("微软幼圆", Font.PLAIN,20));
			if (tree.getRoot() != null) {
				displayTree(g2, tree.getRoot(), getWidth() / 2, 30,
						getWidth() / 4);
			}
		}

		public void setSearchNode(int i) {
			this.SearchNode = new TreeNode<Integer>(i);
		}

		private void displayTree(Graphics g, BinaryTree.TreeNode<Integer> root, int x,
				int y, int hGap) {
			g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
			g.setColor(Color.black);
		   if(SearchNode != null && SearchNode.element.equals(root.element)){
				g.setColor(Color.red);
				this.SearchNode = null; //颜色复位
			}

			
			g.drawString(root.element + "", x - 6, y + 4);
			
			
			if (root.left != null) {
				connectLeftChild(g, x - hGap, y + vGap, x, y);
				displayTree(g, root.left, x - hGap, y + vGap, hGap / 2);
			}

			if (root.right != null) {
				connnectRightChild(g, x + hGap, y + vGap, x, y);
				displayTree(g, root.right, x + hGap, y + vGap, hGap / 2);
			}
		}

		private void connectLeftChild(Graphics g, int x1, int y1, int x2, int y2) {
			double d = Math.sqrt(vGap * vGap + (x2 - x1) * (x2 - x1));
			int x11 = (int) (x1 + radius * (x2 - x1) / d);
			int y11 = (int) (y1 - radius * vGap / d);
			int x21 = (int) (x2 - radius * (x2 - x1) / d);
			int y21 = (int) (y2 + radius * vGap / d);
			g.drawLine(x11, y11, x21, y21);
		}

		private void connnectRightChild(Graphics g, int x1, int y1, int x2,
				int y2) {
			double d = Math.sqrt(vGap * vGap + (x2 - x1) * (x2 - x1));
			int x11 = (int) (x1 - radius * (x1 - x2) / d);
			int y11 = (int) (y1 - radius * vGap / d);
			int x21 = (int) (x2 + radius * (x1 - x2) / d);
			int y21 = (int) (y2 + radius * vGap / d);
			g.drawLine(x11, y11, x21, y21);

		}
	}
}
