package gatherfame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import tool.MyArrayList;

public class ArrayListUI extends JFrame {
  private MyArrayList<Integer> list = new MyArrayList<Integer>();
  private ListView view = new ListView();
  private JButton jbtSearch = new JButton("Search");
  private JButton jbtInsert = new JButton("Insert");
  private JButton jbtDelete = new JButton("Delete");
  private JButton jbtTrimToSize = new JButton("TrimToSize");
  private JTextField jtfNumber = new JTextField(2);
  private JTextField jtfIndex = new JTextField(2);
  private JScrollPane srollPane;
  public ArrayListUI() {    
    JPanel panel = new JPanel();
    panel.add(new JLabel("Enter a value: "));
    panel.add(jtfNumber);
    panel.add(new JLabel("Enter an index: "));
    panel.add(jtfIndex);
    panel.add(jbtSearch);
    panel.add(jbtInsert);
    panel.add(jbtDelete);
    panel.add(jbtTrimToSize);
    
     srollPane = new JScrollPane(view);
    srollPane.setPreferredSize(new Dimension(600, 150));
    
    add(srollPane);  
    add(panel, BorderLayout.SOUTH);
    
    jbtSearch.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (!list.contains(Integer.parseInt(jtfNumber.getText()))) {
          JOptionPane.showMessageDialog(null, "key is not in the list");
        }
        else{      
        	if(jtfNumber.getText() != null && !jtfNumber.getText().equals(""))
        		view.search(Integer.parseInt(jtfNumber.getText()));//选中
        }
        fresh();
      }
    });

    jbtInsert.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (jtfIndex.getText().trim().length() > 0)//判断是否有输入位置
          list.add(Integer.parseInt(jtfIndex.getText()), Integer.parseInt(jtfNumber.getText()));
        else
          list.add(Integer.parseInt(jtfNumber.getText()));
        fresh();
        view.search(Integer.MAX_VALUE);//复位寻找元素
      }
    });
    
    jbtDelete.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (!list.contains(Integer.parseInt(jtfNumber.getText()))) {//先判断在数组中是否存在这个元素
          JOptionPane.showMessageDialog(null, "key is not in the list");
        }       
        else {
          list.remove(new Integer(Integer.parseInt(jtfNumber.getText())));
        }
        fresh();
        view.search(Integer.MAX_VALUE);//复位寻找元素
      }
    });
    
    jbtTrimToSize.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        list.trimToSize();
        fresh();
        view.search(Integer.MAX_VALUE);//复位寻找元素
      }
    });
  }
  
  public void fresh(){
	  view.repaint();
	  srollPane.repaint();
	  this.doLayout();
  }
  
  public class ListView extends JPanel {
    private int startingX = 20;
    private int startingY = 20;
    private int boxWidth = 30;
    private int boxHeight = 20;
    private int searchNode = Integer.MAX_VALUE;
    
    public ListView(){
    	this.setPreferredSize(new Dimension(600, 150));
    }
    
    public void search(int index){
    	this.searchNode = index;
    	
    }
    
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      
      int x = startingX + 10;
      int y = startingY + 10;
      
      if((list.size()+ 4) * boxWidth > 600){
      this.setPreferredSize(new Dimension((list.size()+ 6) * boxWidth , 150));
      }
      
      g.drawString("size = " + list.size() 
          + " and capacity = " + list.getCapacity(), 
          startingX + 80, startingY);
      if (list.size() == 0) {//如果为空
        g.drawString("list is empty.", startingX, startingY);
      }
      else {//如果数组中不为空
        g.drawString("array list", startingX, startingY);        
        
        for (int i = 0; i < list.size(); i++) {
        	
         if(list.get(i).equals(searchNode))
        		g.setColor(Color.red); 
          g.drawRect(x, y, boxWidth, boxHeight);
          g.drawString(list.get(i) + "", x + 10, y + 15);
          x = x + boxWidth;
          g.setColor(Color.black);
        }
      }
      
      for (int i = list.size(); i < list.getCapacity(); i++) {//将空的数组添加在末尾位置
        g.drawRect(x, y, boxWidth, boxHeight);
//        g.drawLine(x, y, x + boxWidth, y + boxHeight);
        g.drawLine(x + boxWidth, y, x, y + boxHeight);
        x = x + boxWidth;
      }
    }
  }
  
  /** Main method */
  public static void main(String[] args) {
	ArrayListUI frame = new ArrayListUI();
    frame.setTitle("ArrayList");
    frame.pack();
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}