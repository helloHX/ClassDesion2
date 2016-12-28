package maze;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

public class MazeUI extends JFrame
{
	private static final long serialVersionUID = 1L;

	private boolean findAccess = false; // 是否找到迷宫通路
	private boolean haveFind; // 是否在该迷宫中寻找过通路

	Position labyrinth[][] = new Position[8][8];

	JPanel map = new JPanel(new GridLayout(8, 8, 0, 0));
	static JButton pane[][] = new JButton[8][8];

	JPanel operation = new JPanel(new GridLayout(8, 1, 0, 15));
	JButton ok = new JButton("ok");
	JButton cancel = new JButton("cancel");
	JButton random = new JButton("随机生成");
	JButton save = new JButton("保存数据");
	JButton get = new JButton("取出数据");

	public MazeUI()
	{
		setLayout(new BorderLayout());

		for (int i = 0; i < pane.length; i++)
		{
			for (int j = 0; j < pane[i].length; j++)
			{
				labyrinth[i][j] = new Position(i, j, 0);
				pane[i][j] = new JButton();
				pane[i][j].setBackground(new Color(0, 224, 202));
				pane[i][j].addMouseListener(new MouseListener(i, j));
				map.add(pane[i][j]);
			}
		}
		pane[0][0].addMouseListener(null);
		pane[0][0].addActionListener(new ButtonListener());
		pane[pane.length - 1][pane[pane.length - 1].length - 1]
				.addMouseListener(null);
		pane[pane.length - 1][pane[pane.length - 1].length - 1]
				.addActionListener(new ButtonListener());

		operation.add(ok);
		ok.setBackground(Color.gray);
		operation.add(cancel);
		cancel.setBackground(Color.gray);
		operation.add(random);
		random.setBackground(Color.gray);
		operation.add(save);
		save.setBackground(Color.gray);
		operation.add(get);
		get.setBackground(Color.gray);

		map.setBorder(BorderFactory.createLineBorder(Color.RED));
		add(map);
		add(operation, BorderLayout.EAST);

		ok.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (findAccess || haveFind)
				{
					JOptionPane.showMessageDialog(null, "请刷新迷宫", "警告",
							JOptionPane.ERROR_MESSAGE);
				} else
				{
					findAccess(labyrinth, 0, 0);
					haveFind = true;
				}
			}
		});

		cancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				findAccess = false;
				haveFind = false;
				for (int i = 0; i < pane.length; i++)
				{
					for (int j = 0; j < pane[i].length; j++)
					{
						labyrinth[i][j] = new Position(i, j, 0);
						pane[i][j].setIcon(null);
					}
				}
			}
		});

		random.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{

				findAccess = false;
				haveFind = false;
				for (int i = 0; i < pane.length; i++)
				{
					for (int j = 0; j < pane[i].length; j++)
					{
						labyrinth[i][j] = new Position(i, j, 0);
						pane[i][j].setIcon(null);
					}
				}

				int barriers = 1 + (int) (Math.random() * 25);
				int c, r;
				for (int i = 0; i < barriers; i++)
				{
					c = (int) (Math.random() * 8);
					r = (int) (Math.random() * 8);
					if ((c == r & c == 0) || (c == r & c == 7))
					{
						barriers++;
						continue;
					}
					labyrinth[c][r].setAttribute(1);
					pane[c][r].setIcon(new ImageIcon("card/barrier.png"));
				}
			}
		});

		save.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (findAccess)
				{
					saveData(labyrinth);
				} else
				{
					JOptionPane.showMessageDialog(null, "请先录入迷宫并找到通路", "警告",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		get.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				final DataFrame frame;
				try
				{
					frame = new DataFrame();
					frame.setSize(230, 400);
					frame.setTitle("Maze Data");
					frame.setLocationRelativeTo(null);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (FileNotFoundException e1)
				{
					JOptionPane.showMessageDialog(null, "文件丢失，或没有存入迷宫数据", "警告",
							JOptionPane.ERROR_MESSAGE);
				} catch (IOException e1)
				{
					JOptionPane.showMessageDialog(null, "系统出错", "警告",
							JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (ClassNotFoundException e2)
				{
					JOptionPane.showMessageDialog(null, "系统出错", "警告",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	public static void printMaze(Position[][] maze)
	{
		for (int i = 0; i < pane.length; i++)
		{
			for (int j = 0; j < pane[i].length; j++)
			{
				if (!(maze[i][j].getLeaveDirection().equals("")))
				{
					pane[i][j].setIcon(new ImageIcon("card/"
							+ maze[i][j].getLeaveDirection() + ".png"));
				} else if (i == pane.length - 1 && j == pane[i].length - 1)
				{
					pane[i][i].setIcon(new ImageIcon("card/floower.png"));
				} else if (maze[i][j].getAttribute() == 0)
				{
					pane[i][j].setIcon(null);
				} else if (maze[i][j].getAttribute() == -1)
				{
					pane[i][j].setIcon(new ImageIcon("card/jam-up.png"));
				} else
				{
					pane[i][j].setIcon(new ImageIcon("card/barrier.png"));
				}
			}
		}
	}

	public boolean saveData(Position[][] maze)
	{
		File file = new File("access.dat");
		ObjectOutputStream output = null;
		try
		{
			FileOutputStream fos = new FileOutputStream(file, true);
			if (file.length() < 1)
			{
				output = new ObjectOutputStream(fos);
			} else
			{
				output = new MyObjectOutputStream(fos);
			}
			output.writeObject(maze);
			output.reset();
			output.close();
			output.flush();
			JOptionPane.showMessageDialog(null, "数据写入成功", "通知",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		} catch (Exception e1)
		{
			JOptionPane.showMessageDialog(null, "数据写入出错", "警告",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}

	public void findAccess(Position[][] labyrinth, int x, int y)
	{
		labyrinth[x][y].setAttribute(-1);
		if (x == labyrinth.length - 1
				&& y == labyrinth[labyrinth.length - 1].length - 1)
		{
			pane[x][y].setIcon(new ImageIcon("card/floower.png"));
			findAccess = true;
			return;
		}

		if (!findAccess && y + 1 < labyrinth[x].length && x < labyrinth.length
				&& labyrinth[x][y + 1].getAttribute() == 0)
		{
			labyrinth[x][y].setLeaveDirection("right");
			pane[x][y].setIcon(new ImageIcon("card/right.png"));
			findAccess(labyrinth, x, y + 1);
		}
		if (!findAccess && x + 1 < labyrinth.length
				&& y < labyrinth[x + 1].length
				&& labyrinth[x + 1][y].getAttribute() == 0)
		{
			labyrinth[x][y].setLeaveDirection("down");
			pane[x][y].setIcon(new ImageIcon("card/down.png"));
			findAccess(labyrinth, x + 1, y);
		}
		if (!findAccess && y - 1 >= 0 && y - 1 < labyrinth[x].length
				&& x < labyrinth.length
				&& labyrinth[x][y - 1].getAttribute() == 0)
		{
			labyrinth[x][y].setLeaveDirection("left");
			pane[x][y].setIcon(new ImageIcon("card/left.png"));
			findAccess(labyrinth, x, y - 1);
		}
		if (!findAccess && x - 1 < labyrinth.length && x - 1 >= 0
				&& y < labyrinth[x - 1].length
				&& labyrinth[x - 1][y].getAttribute() == 0)
		{
			labyrinth[x][y].setLeaveDirection("up");
			pane[x][y].setIcon(new ImageIcon("card/up.png"));
			findAccess(labyrinth, x - 1, y);
		}

		if (!findAccess)
		{
			labyrinth[x][y].setLeaveDirection("");
			pane[x][y].setIcon(new ImageIcon("card/jam-up.png"));
		}

		if (x == 0 && y == 0 && !findAccess)
		{
			JOptionPane.showMessageDialog(null, "此迷宫没有通路", "通知",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public static void main(String[] args)
	{
		MazeUI frame = new MazeUI();
		frame.setSize(500, 400);
		frame.setTitle("Maze");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	class ButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == pane[0][0])
			{
				JOptionPane.showMessageDialog(null, "不能在起点添加障碍", "警告",
						JOptionPane.ERROR_MESSAGE);
			} else if (e.getSource() == pane[pane.length - 1][pane[pane.length - 1].length - 1])
			{
				JOptionPane.showMessageDialog(null, "不能在终点添加障碍", "警告",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class MouseListener extends MouseAdapter
	{
		private int i;
		private int j;

		public MouseListener(int i, int j)
		{
			this.i = i;
			this.j = j;
		}

		public void mouseClicked(MouseEvent evt)
		{
			if ((evt.getModifiers() & InputEvent.BUTTON1_MASK) != 0)
			{
				pane[i][j].setIcon(new ImageIcon("card/barrier.png"));
				labyrinth[i][j].setAttribute(1);
			} else if ((evt.getModifiers() & InputEvent.BUTTON3_MASK) != 0)
			{
				pane[i][j].setIcon(null);
				labyrinth[i][j] = new Position(i, j, 0);
			}
		}
	}
}

class MyObjectOutputStream extends ObjectOutputStream // 防止数据崩溃
{
	public MyObjectOutputStream() throws IOException
	{
		super();
	}

	public MyObjectOutputStream(OutputStream out) throws IOException
	{
		super(out);
	}

	@Override
	protected void writeStreamHeader() throws IOException
	{
		return;
	}
}

class DataFrame extends JFrame
{
	private static final long serialVersionUID = 1L;

	static ArrayList<Position[][]> maze = new ArrayList<Position[][]>();
	private JPanel panel = new JPanel(null);
	static int index = -1;

	public DataFrame() throws ClassNotFoundException, FileNotFoundException, IOException
	{
		DataFrame.maze.clear();
		getData();
		panel.setPreferredSize(new Dimension(0, maze.size() * 60));
		
		if (maze.isEmpty())
		{
			JButton exit = new JButton("退出");
			JLabel message = new JLabel("没有任何迷宫存入");
			message.setBounds(0, 5, 200, 45);
			exit.setBounds(0, 50, 200, 45);
			exit.setBackground(new Color(146, 150, 149));
			panel.add(message);
			panel.add(exit);
			
			exit.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					dispose();
				}
			});
		} else
		{
			for (int i = 0; i < maze.size(); i++)
			{
				JLabel label = new JLabel("   maze" + (i + 1));
				label.setFont(new Font("SansSerif", Font.ITALIC, 36));
				label.setIcon(new ImageIcon("card/maze.png"));
				label.setOpaque(true);
				label.setBounds(0, i * 50 + 5, 210, 45);
				label.addMouseListener(new LabelMouseListener(label, i, this,
						panel));
				panel.add(label);
			}
		}
		add(new JScrollPane(panel));
	}

	public void getData() throws ClassNotFoundException, IOException,
			FileNotFoundException
	{
		File file = new File("access.dat");

		try (ObjectInputStream input = new ObjectInputStream(
				new FileInputStream(file));)
		{
			while (true)
			{
				maze.add((Position[][]) (input.readObject()));
			}
		} catch (EOFException ex)
		{
			return;
		}
	}
}

class LabelMouseListener extends MouseAdapter
{
	final JPopupMenu menu = new JPopupMenu();
	JButton delete = new JButton("删除");
	JButton clear = new JButton("清空");
	JButton exit = new JButton("退出");

	private JLabel label;
	private int index;
	JFrame frame;
	JPanel panel;

	public LabelMouseListener(JLabel label, int index1, JFrame frame1,
			JPanel panel1)
	{
		this.label = label;
		this.index = index1;
		this.frame = frame1;
		this.panel = panel1;

		clear.setBackground(new Color(146, 150, 149));
		delete.setBackground(new Color(146, 150, 149));
		exit.setBackground(new Color(146, 150, 149));
		menu.add(delete);
		menu.add(clear);
		menu.add(exit);
		menu.setBackground(new Color(146, 150, 149));

		delete.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				File file = new File("access.dat");

				try
				{
					FileWriter fw = new FileWriter(file);
					fw.write("");
					FileOutputStream fos = new FileOutputStream(file, true);
					
					ObjectOutputStream output = new ObjectOutputStream(fos);
					output.writeObject(DataFrame.maze.get(0));
					output.reset();
					output.flush();
					
					DataFrame.maze.remove(index);

					MyObjectOutputStream output1 = new MyObjectOutputStream(fos);
					for (int i = 1; i < DataFrame.maze.size(); i++)
					{
						output1.writeObject(DataFrame.maze.get(i));
						output1.reset();
						output1.flush();
					}

					fw.close();
					output.close();
					output1.close();

				} catch (Exception e1)
				{
					JOptionPane.showMessageDialog(null, "数据写入出错", "警告",
							JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}

				panel.removeAll();
				if(DataFrame.maze.size() == 0)
				{
					JButton exit = new JButton("退出");
					JLabel message = new JLabel("没有任何迷宫存入");
					message.setBounds(0, 5, 200, 45);
					exit.setBounds(0, 50, 200, 45);
					exit.setBackground(new Color(146, 150, 149));
					panel.add(message);
					panel.add(exit);
					
					exit.addActionListener(new ActionListener()
					{
						@Override
						public void actionPerformed(ActionEvent e)
						{
							frame.dispose();
						}
					});
				}else
				{
					for (int i = 0; i < DataFrame.maze.size(); i++)
					{
						JLabel label = new JLabel("   maze" + (i + 1));
						label.setFont(new Font("SansSerif", Font.ITALIC, 36));
						label.setIcon(new ImageIcon("card/maze.png"));
						label.setOpaque(true);
						label.setBounds(0, i * 50 + 5, 200, 45);
						label.addMouseListener(new LabelMouseListener(label, i,
								frame, panel));
						panel.add(label);
					}
				}
				panel.validate();
				panel.repaint();
				menu.setVisible(false);
				JOptionPane.showMessageDialog(null, "删除成功", "通知",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		clear.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				DataFrame.maze.clear();

				File file = new File("access.dat");
				FileWriter fw;
				try
				{
					fw = new FileWriter(file);
					fw.write("");
					fw.close();
				} catch (IOException e1)
				{
					JOptionPane.showMessageDialog(null, "系统出错", "通知",
							JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}

				panel.removeAll();
				JLabel message = new JLabel("没有任何迷宫存入");
				JButton exit = new JButton("退出");
				
				message.setBounds(0, 5, 200, 45);
				exit.setBounds(0, 50, 230, 45);
				exit.setBackground(new Color(146, 150, 149));
				panel.add(message);
				panel.add(exit);
				
				exit.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						frame.dispose();
					}
				});
				panel.validate();
				panel.repaint();
				menu.setVisible(false);
			}

		});

		exit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				frame.dispose();
			}
		});
	}

	@Override
	public void mouseEntered(MouseEvent evt)
	{
		label.setBackground(new Color(229, 243, 251));
		label.setBorder(BorderFactory.createLineBorder(
				new Color(112, 192, 231), 1));
		label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent evt)
	{
		label.setBackground(new Color(238, 238, 238));
		label.setBorder(null);
	}

	@Override
	public void mouseClicked(MouseEvent evt)
	{
		if ((evt.getModifiers() & InputEvent.BUTTON1_MASK) != 0
				&& evt.getClickCount() == 2)
		{
			MazeUI.printMaze(DataFrame.maze.get(index));
			DataFrame.maze.clear();
			frame.dispose();
		}

		if ((evt.getModifiers() & InputEvent.BUTTON3_MASK) != 0)
		{
			menu.show(label, evt.getX(), evt.getY());
		}
	}
}