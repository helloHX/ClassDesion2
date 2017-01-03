package gatherfame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
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
import javax.swing.JTextField;

import tool.Graph;
import tool.WeightedEdge;
import tool.WeightedGraph;

public class ShortestPath extends JFrame {
	private JTextField jtfStartCity = new JTextField(10);
	private JTextField jtfEndCity = new JTextField(10);
	private JButton jbtDisplay = new JButton("Display Shortest Path");
	private JButton jbtDFSDisplay = new JButton("Display DFS Tree");
	private JButton jbtBFSDisplay = new JButton("Display BFS Tree");

	private City[] vertices = { new City("Seattle", 75, 50),
			new City("San Francisco", 50, 210),
			new City("Los Angeles", 75, 275), new City("Denver", 275, 175),
			new City("Kansas City", 400, 245), new City("Chicago", 450, 100),
			new City("Boston", 700, 80), new City("New York", 675, 120),
			new City("Atlanta", 575, 295), new City("Miami", 600, 400),
			new City("Dallas", 408, 325), new City("Houston", 450, 360) };

	int[][] edges = { { 0, 1, 807 }, { 0, 3, 1331 }, { 0, 5, 2097 },
			{ 1, 0, 807 }, { 1, 2, 381 }, { 1, 3, 1267 }, { 2, 1, 381 },
			{ 2, 3, 1015 }, { 2, 4, 1663 }, { 2, 10, 1435 }, { 3, 0, 1331 },
			{ 3, 1, 1267 }, { 3, 2, 1015 }, { 3, 4, 599 }, { 3, 5, 1003 },
			{ 4, 2, 1663 }, { 4, 3, 599 }, { 4, 5, 533 }, { 4, 7, 1260 },
			{ 4, 8, 864 }, { 4, 10, 496 }, { 5, 0, 2097 }, { 5, 3, 1003 },
			{ 5, 4, 533 }, { 5, 6, 983 }, { 5, 7, 787 }, { 6, 5, 983 },
			{ 6, 7, 214 }, { 7, 4, 1260 }, { 7, 5, 787 }, { 7, 6, 214 },
			{ 7, 8, 888 }, { 8, 4, 864 }, { 8, 7, 888 }, { 8, 9, 661 },
			{ 8, 10, 781 }, { 8, 11, 810 }, { 9, 8, 661 }, { 9, 11, 1187 },
			{ 10, 2, 1435 }, { 10, 4, 496 }, { 10, 8, 781 }, { 10, 11, 239 },
			{ 11, 8, 810 }, { 11, 9, 1187 }, { 11, 10, 239 } };

	private Graph<City> graph = new WeightedGraph<City>(edges, vertices);

	private GraphView view = new GraphView(graph);

	public ShortestPath() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Starting city"));
		panel.add(jtfStartCity);
		panel.add(new JLabel("Ending city"));
		panel.add(jtfEndCity);
		panel.add(jbtDisplay);

		add(view);
		add(panel, BorderLayout.SOUTH);
		jbtDisplay.addActionListener(new ButtonListener());
	}
	
	//根据用户的输入，将获取到的内容转换为图中的点的下标，调用图中的获取最短路径的方法，
	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == jbtDisplay) {
				String startCity = jtfStartCity.getText();
				String endCity = jtfEndCity.getText();
				int startIndex = graph.getIndex(new City(startCity, 0, 0));
				int endIndex = graph.getIndex(new City(endCity, 0, 0));
				if (startIndex < 0)
					JOptionPane.showMessageDialog(null, startCity
							+ " is not in the map");

				if (endIndex < 0)
					JOptionPane.showMessageDialog(null, endCity
							+ " is not in the map");
				else {
					List<City> path = ((WeightedGraph<City>) graph)
							.getShortestPath(startIndex).getPath(endIndex);
					view.disPlayPath(path);
				}
			}
		}
	}

	class GraphView extends JPanel {//和以往的方法一样
		Graph<? extends Displayable> graph;
		List<? extends Displayable> path;

		public GraphView(Graph<? extends Displayable> graph,
				List<? extends Displayable> path) {//通过图和路径展示寻找的结果
			this.graph = graph;
			this.path = path;
		}

		public GraphView(Graph<? extends Displayable> graph) {//通过图展示图
			this.graph = graph;
		}

		public void disPlayPath(List<? extends Displayable> path) {
			this.path = path;
			repaint();
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			// 画点
			List<? extends Displayable> vertices = graph.getVertices();

			for (int i = 0; i < vertices.size(); i++) {
				int x = vertices.get(i).getX();
				int y = vertices.get(i).getY();
				String name = vertices.get(i).getName();

				g.fillOval(x - 8, y - 8, 16, 16);
				g.drawString(name, x - 12, y - 12);
			}

			// 画边
			for (int i = 0; i < vertices.size(); i++) {
				List<Integer> edges = graph.getNeighbors(i);
				for (int j = 0; j < edges.size(); j++) {//获取到所有邻居以画边
					int v = edges.get(j);
					int x1 = graph.getVertex(i).getX();
					int y1 = graph.getVertex(i).getY();
					int x2 = graph.getVertex(v).getX();
					int y2 = graph.getVertex(v).getY();

					g.drawLine(x1, y1, x2, y2);
				}
			}

			// 将权重添加在图上
			List<PriorityQueue<WeightedEdge>> queues = ((WeightedGraph<? extends Displayable>) graph)
					.getWeightedEdges();
			for (int i = 0; i < vertices.size(); i++) {
				ArrayList<WeightedEdge> list = new ArrayList<WeightedEdge>(
						queues.get(i));

				for (WeightedEdge edge : list) {
					int u = edge.u;
					int v = edge.v;
					double weight = edge.weight;
					int x1 = graph.getVertex(u).getX();
					int y1 = graph.getVertex(u).getY();
					int x2 = graph.getVertex(v).getX();
					int y2 = graph.getVertex(v).getY();
					g.drawString(weight + "", (x1 + x2) / 2, (y1 + y2) / 2 - 6);
				}
			}

			// displayPath
			if (path == null)
				return;
			g.setColor(Color.red);//用红色的线条覆盖最短路径
			for (int i = 1; i < path.size(); i++) {
				int x1 = path.get(i).getX();
				int y1 = path.get(i).getY();
				int x2 = path.get(i - 1).getX();
				int y2 = path.get(i - 1).getY();
				g.drawLine(x1, y1, x2, y2);
			}
		}
	}

	public static void main(String[] args) {
		ShortestPath frame = new ShortestPath();
		frame.setTitle("ShortestPath");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(750, 500);
		frame.setVisible(true);
	}
}
