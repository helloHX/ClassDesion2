package spoint24;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputPoint24 extends JFrame{
	private JButton findASButton = new JButton("Find a Solution");
	private JTextField resultJtf = new  JTextField(18);
	private JLabel label = new JLabel("input four numbers between 1 to 13");
	private FindASolution findASolution = new FindASolution();
	private InputPointCenter inputPanel = new InputPointCenter();

	
	public InputPoint24(){
		JPanel tipPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,5,0));
		tipPanel.add(label);
		
		 findASButton.addActionListener(new  ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        		inputPanel.fresh();
	        		resultJtf.setText(findASolution.createExpression(
	        				inputPanel.getCarNumber()));
	        	}
	        });
		 
		 JPanel resultPanel = new JPanel(new BorderLayout(5,5));
		 
		 resultJtf.setEditable(false);
		 
		 resultPanel.add(resultJtf,BorderLayout.WEST);
		 resultPanel.add(findASButton,BorderLayout.EAST);
		
		 this.setLayout(new BorderLayout(5,5));
		 this.add(tipPanel,BorderLayout.NORTH);
		 this.add(inputPanel,BorderLayout.CENTER);
		 this.add(resultPanel,BorderLayout.SOUTH);
		
	}
	
	public static void main(String[] args) {
		InputPoint24 frame = new InputPoint24();
		frame.pack();
		frame.setTitle("Point24");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
