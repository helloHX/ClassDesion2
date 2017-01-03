package tree;
import javax.swing.*;

public class DisplayBinaryTree extends JFrame{
	public DisplayBinaryTree(){
	 add(new TreeControl(new BinaryTree<Integer>()));
	}
	
	public static void main(String[] args) {
		DisplayBinaryTree frame = new DisplayBinaryTree();
		frame.setSize(500, 500);
		frame.setTitle("compress");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
