package coinGame;

import java.awt.GridLayout;

import javax.swing.JPanel;

public class NodePanel extends JPanel {
	
	public NodePanel(char[] ch){
		this.setLayout(new GridLayout(4, 4));
		for (int i = 0; i < 16; i++) {
			add(new Cell(ch[i] + "",false));
		}
	}
	
	public NodePanel(char[] ch1,char[] ch) {
		this.setLayout(new GridLayout(4, 4));
		for (int i = 0; i < 16; i++) {
			if(ch[i] != ch1[i])
			    add(new Cell(ch[i] + "",true));
			else
				add(new Cell(ch[i] + "",false));
		}
	}
}