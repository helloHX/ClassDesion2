package point24;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Point24Center extends JPanel{
	
	public int[] carNumber = new int[4];

    
	public Point24Center(){
		
		full();
	}
	
	public void flash(){//移出旧牌刷新牌
		this.removeAll();
		full();
		this.repaint();
		this.doLayout();
		
	}
	
	public void full(){//将随机生成不重复的四张牌
		this.setPreferredSize(new Dimension(300, 150));
		this.setLayout(new GridLayout(1,3,5,5));
		for (int i = 0; i < 4; i++) {
			String p = "";
			int noCar ;
			do{
				noCar =  (int)(Math.random()*52) + 1;
			}while(!this.isUnique(noCar));
			carNumber[i] = noCar;
			 p ="image/card/" + noCar + ".png";
			this.add(addCard(p));
		}
	}
	
	public JPanel addCard(String p){//将卡片加到界面上
		
		ImageIcon icon = new ImageIcon(p);
		Image img = icon.getImage();
		ImagePanle jpl = new ImagePanle(img);
		jpl.setSize(40, 40);
		return jpl;
	}
	
	public boolean isUnique(int noCar){//判断与前几张牌内容是否相同
		boolean isUnique = true;
		for(int i = 0;i < carNumber.length;i++){
			if(noCar == carNumber[i])
				return false;
		}
		return isUnique;
	}
	
	public int[] getCarNumber(){//获取当前内容的牌
		return this.carNumber;
	}
	

}
