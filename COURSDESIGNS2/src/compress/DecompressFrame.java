package compress;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class DecompressFrame extends JFrame{
	private FileChooser fileChooser;
	private String sourceP;
	private DeCompressCenter centerPanel = new DeCompressCenter();
	private JTextField sourcePath;
	private JTextField targetPath;
	private JLabel tipTabel;
	private JButton jbScan = new JButton("选择文件");
	private JButton decompress = new JButton("解压  >>");
	private JPanel titlePanel = new JPanel(new BorderLayout());
	private JPanel footPanel = new JPanel(new BorderLayout());
	private String tragetFile;
	private String decompressFileName;
	
	public DecompressFrame(){
		this.setLayout(new BorderLayout(0, 2));
		sourcePath = new JTextField(50);
		targetPath = new JTextField(50);
		tipTabel = new JLabel("压缩后文件地址:");
		sourcePath.setEditable(false);
		titlePanel.add(sourcePath, BorderLayout.WEST);
		titlePanel.add(jbScan, BorderLayout.EAST);
		
		footPanel.add(tipTabel, BorderLayout.WEST);
		footPanel.add(targetPath, BorderLayout.CENTER);
		footPanel.add(decompress, BorderLayout.EAST);
		
		ButtonListener monitor = new ButtonListener();
		jbScan.addActionListener(monitor);
		decompress.addActionListener(monitor);
		
		this.add(titlePanel, BorderLayout.NORTH);
		this.add(footPanel, BorderLayout.SOUTH);
		this.add(centerPanel, BorderLayout.CENTER);
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
			
			if (e.getSource() == decompress) {
				
				tragetFile = targetPath.getText();
				decompressFileName = sourcePath.getText();
				if ((sourceP != null && !sourceP.equals("")) && 
						(tragetFile != null && !tragetFile.equals("") )) {
					
					 Decompress decompress = new Decompress(tragetFile,decompressFileName);
					 JOptionPane.showMessageDialog(null, " 解压成功！！", 
								"消息", JOptionPane.INFORMATION_MESSAGE); 	
				}
				else{
					 JOptionPane.showMessageDialog(null, " 请输入保存地址", 
								"消息", JOptionPane.INFORMATION_MESSAGE); 
				}
			}

		}
	}
	
	
	public static void main(String[] args) {
		DecompressFrame frame = new DecompressFrame();
		frame.pack();
		frame.setTitle("compress");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
