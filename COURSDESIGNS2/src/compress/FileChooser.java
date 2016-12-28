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
			jdir.setDialogTitle("��ѡ��·��");
			if (JFileChooser.APPROVE_OPTION == jdir.showOpenDialog(null)) {// �û������ȷ��
				path = jdir.getSelectedFile().getAbsolutePath();// ȡ��·��ѡ��
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
