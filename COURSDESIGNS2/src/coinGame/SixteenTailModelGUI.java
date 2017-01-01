package coinGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

public class SixteenTailModelGUI extends JFrame {
	private JButton jbtDiagSolve = new JButton("SolveDiag");
	private JButton jbtNBHSolve = new JButton("SolveNBH");
	private JButton jbtStartOver = new JButton("Start Over");
	// ������ʼ��
	private InitialNodePanel initialNodePanel = new InitialNodePanel();
	private JPanel displayPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,10, 10));
	private SixteenTailModel Diagmodel = new SixteenTailModel(true);
	private SixteenTailModel NBHmodel = new SixteenTailModel(false);
	//���ÿ��
	public static void main(String[] args){
		SixteenTailModelGUI frame = new SixteenTailModelGUI();
		frame.setSize(600,400);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("16 Tail Problem");
		frame.setVisible(true);
	}

	/** ��ʼ�� GUI */
	public SixteenTailModelGUI() {
		// ��һ���ط�displayPanel��������
		displayPanel.add(initialNodePanel);
		add(new JScrollPane(displayPanel), BorderLayout.CENTER);
		// buttonpanelӵ��������ť
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(jbtDiagSolve);
		buttonPanel.add(jbtNBHSolve);
		buttonPanel.add(jbtStartOver);
		add(buttonPanel, BorderLayout.SOUTH);

		// DigSolve��ť�ļ�����
		jbtDiagSolve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPanel.removeAll();

				java.util.List<Integer> path = Diagmodel
						.getShortestPath(SixteenTailModel
								.getIndex(initialNodePanel.getMatrix()));
				displayPanel.add(new NodePanel(SixteenTailModel
						.getNode(path.get(0).intValue())));
				for (int i = 1; i < path.size(); i++) {
					displayPanel.add(new NodePanel(SixteenTailModel
							.getNode(path.get(i - 1).intValue()),SixteenTailModel
							.getNode(path.get(i).intValue())));
				}
				displayPanel.revalidate();
			}
		});
		
		// NBHSolve��ť�ļ�����
				jbtNBHSolve.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						displayPanel.removeAll();

						java.util.List<Integer> path = NBHmodel
								.getShortestPath(SixteenTailModel
										.getIndex(initialNodePanel.getMatrix()));
						displayPanel.add(new NodePanel(SixteenTailModel
								.getNode(path.get(0).intValue())));
						for (int i = 1; i < path.size(); i++) {
							displayPanel.add(new NodePanel(SixteenTailModel
									.getNode(path.get(i - 1).intValue()),SixteenTailModel
									.getNode(path.get(i).intValue())));
						}
						displayPanel.revalidate();
					}
				});
		
		// Start Over��ť�ļ�����
		jbtStartOver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayPanel.removeAll();
				displayPanel.add(initialNodePanel); // ��ʾ��ʼ�ڵ�
				displayPanel.repaint();
			}
		});
	}
}
