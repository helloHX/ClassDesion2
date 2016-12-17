package spoint24;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import point24.Point24Center;
import point24.Verify;

public class ShowFrame  extends JFrame {
	private Point24Center centerPanle = new Point24Center();
	private JPanel flashPanel = new JPanel(new BorderLayout(5,10));
	private JPanel inputPanel = new JPanel(new BorderLayout(5,10));
	private JLabel pointLable = new JLabel("Enter an expression :");
	private JButton verifyButton = new JButton("verify");
	private JButton flashButton = new JButton("Flash");
	private JButton findASButton = new JButton("Find a Solution");
	private JTextField inputJtf = new  JTextField(20);
	private JTextField resultJtf = new  JTextField(18);
	private FindASolution findASolution = new FindASolution();
	
	public ShowFrame(){
		
		this.setLayout(new BorderLayout(10,5));
		Font font = new Font("微软雅黑",Font.PLAIN,15);
		inputJtf.setFont(font);
		resultJtf.setFont(font);
		inputPanel.add(pointLable,BorderLayout.WEST);
		inputPanel.add(inputJtf,BorderLayout.CENTER);
		inputPanel.add(verifyButton,BorderLayout.EAST);
	    flashButton.addActionListener(new ActionListener() {
			
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					centerPanle.flash();
				}
			});
	       verifyButton.addActionListener(new  ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String expression = inputJtf.getText();
				Verify verify = new Verify();
				if(verify.Comform(centerPanle.getCarNumber(),expression)){
				switch (verify.Calculter(expression)) {
				case 0:
					JOptionPane.showMessageDialog(null, "Correct",
							"消息", JOptionPane.INFORMATION_MESSAGE); 
					break;

				default:
					JOptionPane.showMessageDialog(null, "Incorrect result", 
							"消息", JOptionPane.INFORMATION_MESSAGE); 	
					break;
				}
					
				}else{
					JOptionPane.showMessageDialog(null, "The number in the expression"
							+ " don't match the numbers in the set", 
							"消息", JOptionPane.INFORMATION_MESSAGE); 	
				}
			}
	    	   
	       });
        
	       findASButton.addActionListener(new  ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		resultJtf.setText(findASolution.createExpression(
        				centerPanle.getCarNumber()));
        	}
        });
        
        resultJtf.setEditable(false);
        flashPanel.add(flashButton,BorderLayout.EAST);
        flashPanel.add(resultJtf,BorderLayout.CENTER);
        flashPanel.add(findASButton,BorderLayout.WEST);
        
		this.add(flashPanel,BorderLayout.NORTH);
		this.add(centerPanle,BorderLayout.CENTER);
		this.add(inputPanel,BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) {
		ShowFrame frame = new ShowFrame();
		frame.pack();
		frame.setTitle("Point24");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
