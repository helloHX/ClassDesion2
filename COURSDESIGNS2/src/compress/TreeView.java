package compress;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class TreeView<E> extends JPanel {
	private int radius = 20;
	private int vGap = 50;
	private HuffmanTree<E> tree;
    
	public TreeView(){
		this.setPreferredSize(new Dimension(2000, 1000));
	}
	
	public TreeView(HuffmanTree<E> tree){
		this.tree = tree;
		this.setPreferredSize(new Dimension(2000, 1000));
	}
	
	public void setTree(HuffmanTree<E> tree){
		this.tree = tree;
		this.setPreferredSize(new Dimension(2000, 1000));
	}
	
	protected void paintComponent(Graphics g){
		
		super.paintComponent(g);
		
		
		if(tree != null){
		if(tree.getHead() != null){
			displayTree(g,tree.getHead(),getWidth()/ 2,30,getWidth() / 4);
		}
		}
	}
	
	private void displayTree(Graphics g,HuffmanTree.Node<E> root,
			int x,int y,int hGap){
		g.setFont(new Font("Î¢ÈíÓ×Ô²", Font.PLAIN,20));
		g.setColor(new Color(
				 
				 221,160,221
				 ));
		g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
		
		if(root.data != null){
			
			g.setColor(new Color( 65,105,225));
			g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
			g.setColor(new Color(240,255,255));
		    g.drawString(root.data + "",x - 6, y + 4);
		}
		
		g.setColor(new Color(
				 
				 221,160,221
				 ));
		if(root.Lchild != null){
			connectLeftChild(g,x - hGap,y + vGap,x,y);
			displayTree(g,root.Lchild,x - hGap,y + vGap,hGap / 2);
		}
		
		if(root.Rchild != null){
			connnectRightChild(g,x + hGap,y + vGap,x,y);
			displayTree(g,root.Rchild,x + hGap,y + vGap,hGap / 2);
		}
	}
	
	private void connectLeftChild(Graphics g,
			int x1,int y1,int x2,int y2){
		double d = Math.sqrt(vGap * vGap + (x2 - x1) * (x2 - x1));
		int x11 = (int)(x1 + radius * (x2 - x1) / d);
		int y11 = (int)(y1 - radius * vGap / d);
		int x21 = (int)(x2 - radius * (x2 - x1) / d);
		int y21 = (int)(y2 + radius * vGap / d);
		g.drawString("0",(x11 + x21)/2, (y11 + y21) / 2 + 2);
		g.drawLine(x11,y11,x21,y21);
	}
	
	private void connnectRightChild(Graphics g,
			int x1,int y1,int x2,int y2){
		double d = Math.sqrt(vGap * vGap + (x2 - x1) * (x2 - x1));
		int x11 = (int)(x1 - radius * (x1 - x2) / d);
		int y11 = (int)(y1 - radius * vGap / d);
		int x21 = (int)(x2 + radius * (x1 - x2) / d);
		int y21 = (int)(y2 + radius * vGap / d);
		g.drawString("1",(x11 + x21)/2, (y11 + y21) / 2 + 2);
		g.drawLine(x11,y11,x21,y21);
	}

}
