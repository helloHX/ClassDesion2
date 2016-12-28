package compress;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class DeCompressCenter extends JPanel{
	
	private ImageIcon ImageIcon = new ImageIcon("image/compress.png");
    private Image image = ImageIcon.getImage();
	
    public DeCompressCenter(){
    	
    	this.setPreferredSize(new Dimension(500, 300));
    }
    
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		if(image != null){
			g.drawImage(image, 220, 40, 256, 256, this);
		}
	}
}
