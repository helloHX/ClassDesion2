package spoint24;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputPointCenter extends JPanel{
	private int[] carNumber = new int[4];
	
	private JTextField tp1 = new JTextField(2);
	private JTextField tp2 = new JTextField(2);
	private JTextField tp3 = new JTextField(2);
	private JTextField tp4 = new JTextField(2);
	
	public InputPointCenter(){
		this.setLayout(new GridLayout(1, 4, 5, 10));
		Font font = new Font("Œ¢»Ì—≈∫⁄",Font.PLAIN,40);
		
		tp1.setPreferredSize(new Dimension(100,110));
		tp2.setPreferredSize(new Dimension(100,110));
		tp3.setPreferredSize(new Dimension(100,110));
		tp4.setPreferredSize(new Dimension(100,110));
		
		tp1.setFont(font);//…Ë÷√◊÷ÃÂ
		tp2.setFont(font);
		tp3.setFont(font);
		tp4.setFont(font);
		
		tp1.setHorizontalAlignment(JTextField.CENTER);//æ”÷–
		tp2.setHorizontalAlignment(JTextField.CENTER);
		tp3.setHorizontalAlignment(JTextField.CENTER);
		tp4.setHorizontalAlignment(JTextField.CENTER);
		
		this.add(tp1);
		this.add(tp2);
		this.add(tp3);
		this.add(tp4);
	}
	
	public void fresh(){
		carNumber[0] = Integer.parseInt(tp1.getText());
		carNumber[1] = Integer.parseInt(tp2.getText());
		carNumber[2] = Integer.parseInt(tp3.getText());
		carNumber[3] = Integer.parseInt(tp4.getText());
	}
	
	public int[] getCarNumber(){
		return this.carNumber;
	}
}
