package compress;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class CompressFrame extends JFrame {
	private FileChooser fileChooser;

	private String sourceP;

	private TreeView<Character> treeView = new TreeView<Character>();
	private JTextField sourcePath;
	private JTextField targetPath;
	private JLabel tipTabel;
	private JButton jbScan = new JButton("选择文件");
	private JButton compress = new JButton("压缩  >>");
	private JPanel titlePanel = new JPanel(new BorderLayout());
	private JPanel footPanel = new JPanel(new BorderLayout());

	public CompressFrame() {

		this.setLayout(new BorderLayout(0, 2));
		sourcePath = new JTextField(50);
		targetPath = new JTextField(50);

		tipTabel = new JLabel("压缩后文件地址:");

		sourcePath.setEditable(false);
		targetPath.setEditable(false);

		titlePanel.add(sourcePath, BorderLayout.WEST);
		titlePanel.add(jbScan, BorderLayout.EAST);

		footPanel.add(tipTabel, BorderLayout.WEST);
		footPanel.add(targetPath, BorderLayout.CENTER);
		footPanel.add(compress, BorderLayout.EAST);
		ButtonListener monitor = new ButtonListener();
		JScrollPane srollPane = new JScrollPane(treeView);
		srollPane.setPreferredSize(new Dimension(500, 300));
		jbScan.addActionListener(monitor);
		compress.addActionListener(monitor);

		this.add(titlePanel, BorderLayout.NORTH);
		this.add(footPanel, BorderLayout.SOUTH);
		this.add(srollPane, BorderLayout.CENTER);

	}

	class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == jbScan) {
				
				fileChooser = new FileChooser();
				sourceP = fileChooser.getPath();
				sourcePath.setText(sourceP);
				
			} 
			if (e.getSource() == compress) {
			
				if (sourceP != null && !sourceP.equals("")) {
					
					 Compress compress = new Compress(sourceP);
					 HuffmanTree<Character> tree = compress.getHuffmanTree();
					 JOptionPane.showMessageDialog(null, "压缩完成！！", 
								"消息", JOptionPane.INFORMATION_MESSAGE); 	
					 targetPath.setText(compress.getTragetFile());
					 treeView.setTree(tree);
					 treeView.repaint();
				}
			}

		}
	}

	public static void main(String[] args) {
		CompressFrame frame = new CompressFrame();
		frame.pack();
		frame.setTitle("compress");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
