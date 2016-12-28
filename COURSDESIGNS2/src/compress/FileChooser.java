package compress;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.UIManager;

public class FileChooser {
	private String path = null;

	public FileChooser() {
		
	}
	
	public void ScanFile(){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			JFileChooser jdir = new JFileChooser(new File(System.getProperty("user.dir")));
			jdir.setDialogTitle("请选择路径");
			if (JFileChooser.APPROVE_OPTION == jdir.showOpenDialog(null)) {// 用户点击了确定
				path = jdir.getSelectedFile().getAbsolutePath();// 取得路径选择
			}
			this.path = path;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getPath(){
		ScanFile();
		return this.path;
	}
}
