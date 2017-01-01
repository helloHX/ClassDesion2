package coinGame;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class  Cell extends JLabel {
	public Cell(String string,boolean change) {
		this.setBorder(new LineBorder(Color.black, 1)); // Cell border
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 20));
		if(change)
			setForeground(Color.red);
		  
		else
			setForeground(Color.black);
		
		  setText(string);
		
	}
}


