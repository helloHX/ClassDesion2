package maze;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

public class MyMazenFrame extends JFrame {

	private JButton ok = new JButton("ok");
	private JButton cancel = new JButton("cancel");
	private JButton random = new JButton("�������");
	private JButton save = new JButton("��������");
	private JButton get = new JButton("ȡ������");
	private JButton shrink = new JButton("��£>>");
	private JButton delete = new JButton("ɾ��");
	
	private JPanel operation = new JPanel(new GridLayout(8, 1, 0, 15));
	private MazeMap mazemap = new MazeMap();
	private DataPanel dataPanel = new DataPanel(mazemap);
	private JScrollPane srollPane   = new JScrollPane(dataPanel);;
	private LinkedList<MazeNode> mazeList; // ���ļ��м��س�����list

	public MyMazenFrame() {
		loadMaze();
		this.setLayout(new BorderLayout());

		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (mazemap.isVrify()) {
					JOptionPane.showMessageDialog(null, "��ˢ���Թ�", "����",
							JOptionPane.ERROR_MESSAGE);
				} else {
					mazemap.Vrify();
				}
			}
		});

		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mazemap.setVriify(false);
				mazemap.fresh();//��������
			}
		});
		
		random.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mazemap.random();
			}
		});
		
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mazeList.remove(dataPanel.getChoiced());
				System.out.println(mazeList.size());
				dataPanel.showDataPanel(mazeList);//�ȸ���datapanel
				outPutToFile();//����
				JOptionPane.showMessageDialog(null, "ɾ���ɹ�", "��Ϣ",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveOneMaze();//����
			}

		});
		
		shrink.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			   remove(srollPane);
			   fresh();
			}
		});
		
		get.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadMaze();
				if (mazeList != null) {//���û��������dataPanel���������
					dataPanel.showDataPanel(mazeList);
				}
				
				srollPane.setPreferredSize(new Dimension(150,300));
				add(srollPane, BorderLayout.CENTER);
				fresh();//����Frame�Ĳ��ֺ�����
			}
		});

		operation.add(ok);
		operation.add(cancel);
		operation.add(random);
		operation.add(save);
		operation.add(get);
		operation.add(shrink);
		operation.add(delete);

		operation.setBorder(new LineBorder(new Color(240, 230, 140), 1));

		add(mazemap, BorderLayout.WEST);
		add(operation, BorderLayout.EAST);
	}

	
	public void saveOneMaze() {
		if (mazemap.isVrify()) {
			if (mazeList == null) {//���û����Ӧ�ļ��򴴽�һ���µ��ļ�
				mazeList = new LinkedList<>();
				
				mazeList.add(new MazeNode(mazemap.getWay(), mazemap
						.getIsVisited(), mazemap.getMaze()));

			} else {
				
				mazeList.add(new MazeNode(mazemap.getWay(), mazemap
						.getIsVisited(), mazemap.getMaze()));
			}
			dataPanel.showDataPanel(mazeList);//�ȸ���datapanel
			outPutToFile();//���浽�ļ���
			JOptionPane.showMessageDialog(null, "����ɹ�", "��Ϣ",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "��ͼû��ִ��", "��Ϣ",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void outPutToFile() {
		try {
			try (ObjectOutputStream saveMazes = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream("Mazes.dat")));) {
				saveMazes.writeObject(mazeList);
				
			}
		} catch (IOException e) {
			System.out.println("����ʧ��");
		}
	}

	public void loadMaze() {
		try {
			ObjectInputStream loadMazes = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream("Mazes.dat")));
			mazeList = (LinkedList<MazeNode>) loadMazes.readObject();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "�޴洢��¼", "��Ϣ",
					JOptionPane.INFORMATION_MESSAGE);
		}

		catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "�ļ����ݳ���", "����",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void fresh() {
		this.doLayout();
		pack();
	}

	public static void main(String[] args) {
		MyMazenFrame frame = new MyMazenFrame();
		frame.pack();
		frame.setTitle("�����Թ�");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
