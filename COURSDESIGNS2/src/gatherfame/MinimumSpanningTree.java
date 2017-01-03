package gatherfame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import tool.Graph;
import tool.WeightedEdge;
import tool.WeightedGraph;



public class MinimumSpanningTree extends JFrame {
	private JTextField jtfVertexName = new JTextField(5);
	private JTextField jtfX = new JTextField(5);
	private JTextField jtfY = new JTextField(5);
	private JButton jbtAddVertex = new JButton("Add Vertex");

	private JTextField jtfu = new JTextField(5);
	private JTextField jtfv = new JTextField(5);
	private JTextField jtfWeight = new JTextField(5);
	private JButton jbtAddEdge = new JButton("Add Edge");

	private JButton jbtFindMiniTree = new JButton("显示最小生成树");

	private JButton jbtStartOver = new JButton("重新开始，清空面板");

	private WeightedGraph<City> graph = new WeightedGraph<City>();
	private GraphView view = new GraphView(graph);

	public MinimumSpanningTree() {
		JPanel panel1 = new JPanel(new GridLayout(4, 2));
		panel1.add(new JLabel("Vertex name: "));
		panel1.add(jtfVertexName);
		panel1.add(new JLabel("x-coordinate: "));
		panel1.add(jtfX);
		panel1.add(new JLabel("y-coordinate: "));
		panel1.add(jtfY);
		panel1.add(new JLabel());
		panel1.add(jbtAddVertex);
		panel1.setBorder(new TitledBorder("Add a new vertex"));

		JPanel panel2 = new JPanel(new GridLayout(4, 2));
		panel2.add(new JLabel("Vertex u (index): "));
		panel2.add(jtfu);
		panel2.add(new JLabel("Vertex v (index): "));
		panel2.add(jtfv);
		panel2.add(new JLabel("Weight (int): "));
		panel2.add(jtfWeight);
		panel2.add(new JLabel(""));
		panel2.add(jbtAddEdge);
		panel2.setBorder(new TitledBorder("Add a new edge"));

		JPanel panel3 = new JPanel(new GridLayout(3, 2,10,30));
		panel3.add(jbtFindMiniTree);
		panel3.add(jbtStartOver);
		panel3.setBorder(new TitledBorder("Find mini tree"));

		JPanel panel4 = new JPanel(new GridLayout(1, 3));
		panel4.add(panel1);
		panel4.add(panel2);
		panel4.add(panel3);

		add(new JScrollPane(view));
		add(panel4, BorderLayout.SOUTH);
		jbtAddEdge.addActionListener(new ButtonListener());
		jbtAddVertex.addActionListener(new ButtonListener());
		jbtFindMiniTree.addActionListener(new ButtonListener());
		jbtStartOver.addActionListener(new ButtonListener());
	}
	
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == jbtAddVertex){
				try {
					String name = jtfVertexName.getText();
					if (graph.getSize() != Integer.parseInt(name.trim()))//判断输入的下标是不是输入错误了
						JOptionPane.showMessageDialog(null,
								"The next vertex to be added must be index "
										+ graph.getSize());
					else {//否者将当前入入添加为一个边
						int x = Integer.parseInt(jtfX.getText().trim());
						int y = Integer.parseInt(jtfY.getText().trim());

						graph.addVertex(new City(name, x, y));
						view.repaint();
					}
				} catch (Exception ex) {//输入如果不是整型
					JOptionPane.showMessageDialog(null,
							"The input must be an integer index");
				}
			}
			
			if(e.getSource()==jbtAddEdge){
				try {
					int u = Integer.parseInt(jtfu.getText().trim());
					int v = Integer.parseInt(jtfv.getText().trim());
					int weight = Integer.parseInt(jtfWeight.getText().trim());
					//判断输入
					if (u < 0 || u >= graph.getSize())
						JOptionPane.showMessageDialog(null, "Vertex " + u
								+ " is not in the graph");
					else if (v < 0 || v >= graph.getSize())
						JOptionPane.showMessageDialog(null, "Vertex " + v
								+ " is not in the graph");
					else if (u == v)
						JOptionPane.showMessageDialog(null,
								"Two vertices cannot be the same");
					else {
						graph.addEdge(u, v, weight);
						view.repaint();
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null,
							"The input must be an integer index");
				}
			}
			if(e.getSource()==jbtFindMiniTree){
				WeightedGraph<City>.MST tree = graph.getMinimumSpanningTree();//通过当前的图获取最小生成树
				view.displayTree(tree);
				view.repaint();
			}
			
			if(e.getSource() == jbtStartOver){
				graph.clear();
				view.displayTree(null);
			}
			
		}
		
	}

	class GraphView extends javax.swing.JPanel {
		Graph<? extends Displayable> graph;
		WeightedGraph<City>.MST tree;

		public GraphView(Graph<? extends Displayable> graph,
				WeightedGraph<City>.MST tree) {
			this.graph = graph;
			this.tree = tree;
		}

		public GraphView(Graph<? extends Displayable> graph) {
			this.graph = graph;
		}

		public void displayTree(WeightedGraph<City>.MST tree) {
			this.tree = tree;
			repaint();
		}

		protected void paintComponent(java.awt.Graphics g) {
			super.paintComponent(g);

			List<? extends Displayable> vertices = graph.getVertices();

			for (int i = 0; i < graph.getSize(); i++) {//画点
				int x = vertices.get(i).getX();
				int y = vertices.get(i).getY();
				String name = vertices.get(i).getName();

				g.fillOval(x - 8, y - 8, 16, 16);
				g.drawString(name, x - 12, y - 12);
			}

			for (int i = 0; i < graph.getSize(); i++) {//画边
				List<Integer> neighbors = graph.getNeighbors(i);
				for (int j = 0; j < neighbors.size(); j++) {
					int v = neighbors.get(j);
					int x1 = graph.getVertex(i).getX();
					int y1 = graph.getVertex(i).getY();
					int x2 = graph.getVertex(v).getX();
					int y2 = graph.getVertex(v).getY();

					g.drawLine(x1, y1, x2, y2);
				}
			}

			List<PriorityQueue<WeightedEdge>> queues = ((WeightedGraph<? extends Displayable>) graph)
					.getWeightedEdges();

			for (int i = 0; i < graph.getSize(); i++) {//画权重
				ArrayList<WeightedEdge> list = new ArrayList<WeightedEdge>(
						queues.get(i));

				for (WeightedEdge edge : list) {
					int u = edge.u;
					int v = edge.v;
					int weight = edge.weight;
					int x1 = graph.getVertex(u).getX();
					int y1 = graph.getVertex(u).getY();
					int x2 = graph.getVertex(v).getX();
					int y2 = graph.getVertex(v).getY();
					g.drawString(weight + "", (x1 + x2) / 2, (y1 + y2) / 2 - 6);
				}
			}

			// 画申城树
			if (tree == null)
				return;
			g.setColor(Color.RED);
			for (int i = 0; i < graph.getSize(); i++) {
				if (tree.getParent(i) != -1) {
					int v = tree.getParent(i);
					int x1 = graph.getVertex(i).getX();
					int y1 = graph.getVertex(i).getY();
					int x2 = graph.getVertex(v).getX();
					int y2 = graph.getVertex(v).getY();

					g.drawLine(x1, y1, x2, y2);
				}
			}
		}

		public Dimension getPreferredSize() {
			return new Dimension(1200, 800);
		}
	}

	public static void main(String[] args) {
		MinimumSpanningTree frame = new MinimumSpanningTree();
		frame.setTitle("ShortestPath");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(750, 500);
		frame.setVisible(true);
	}
}
