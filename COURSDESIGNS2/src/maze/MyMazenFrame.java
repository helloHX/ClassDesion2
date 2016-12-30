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
	private JButton random = new JButton("随机生成");
	private JButton save = new JButton("保存数据");
	private JButton get = new JButton("取出数据");
	private JButton shrink = new JButton("收拢>>");
	private JButton delete = new JButton("删除");
	
	private JPanel operation = new JPanel(new GridLayout(8, 1, 0, 15));
	private MazeMap mazemap = new MazeMap();
	private DataPanel dataPanel = new DataPanel(mazemap);
	private JScrollPane srollPane   = new JScrollPane(dataPanel);;
	private LinkedList<MazeNode> mazeList; // 从文件中加载出来的list

	public MyMazenFrame() {
		loadMaze();
		this.setLayout(new BorderLayout());

		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (mazemap.isVrify()) {
					JOptionPane.showMessageDialog(null, "请刷新迷宫", "警告",
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
				mazemap.fresh();//清理内容
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
				dataPanel.showDataPanel(mazeList);//热更新datapanel
				outPutToFile();//保存
				JOptionPane.showMessageDialog(null, "删除成功", "消息",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveOneMaze();//保存
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
				if (mazeList != null) {//如果没有则不在想dataPanel中添加内容
					dataPanel.showDataPanel(mazeList);
				}
				
				srollPane.setPreferredSize(new Dimension(150,300));
				add(srollPane, BorderLayout.CENTER);
				fresh();//更新Frame的布局和内容
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
			if (mazeList == null) {//如果没有相应文件则创建一个新的文件
				mazeList = new LinkedList<>();
				
				mazeList.add(new MazeNode(mazemap.getWay(), mazemap
						.getIsVisited(), mazemap.getMaze()));

			} else {
				
				mazeList.add(new MazeNode(mazemap.getWay(), mazemap
						.getIsVisited(), mazemap.getMaze()));
			}
			dataPanel.showDataPanel(mazeList);//热更新datapanel
			outPutToFile();//保存到文件中
			JOptionPane.showMessageDialog(null, "保存成功", "消息",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "地图没有执行", "消息",
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
			System.out.println("保存失败");
		}
	}

	public void loadMaze() {
		try {
			ObjectInputStream loadMazes = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream("Mazes.dat")));
			mazeList = (LinkedList<MazeNode>) loadMazes.readObject();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "无存储记录", "消息",
					JOptionPane.INFORMATION_MESSAGE);
		}

		catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "文件内容出错", "警告",
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
		frame.setTitle("建立迷宫");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
