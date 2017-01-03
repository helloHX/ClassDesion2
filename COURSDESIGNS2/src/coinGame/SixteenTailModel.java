package coinGame;

import java.util.ArrayList;
import java.util.List;

import tool.AbstractGraph;
import tool.UnweightedGraph;

public class SixteenTailModel {
	//总共有2的16次方个结点
	public static final int SIZE = (int) Math.pow(2,16);
	protected AbstractGraph<Integer>.Tree tree;
	
	public SixteenTailModel(boolean isDiag){
		//每一条边对应着一个结点到另一个结点的移动
		List<AbstractGraph.Edge> edges = getEdges(isDiag);
		//创建一个图，含参数为结点之间的边和结点个数
		UnweightedGraph<Integer> graph = new UnweightedGraph<Integer>(edges,SIZE);
		//得到一个以目标结点2^16为根结点的广度优先搜索树
		tree = graph.bfs(SIZE - 1);
	}
	//查看结点u能否翻转到结点v
	public List<AbstractGraph.Edge> getEdges(boolean isDiag){
		//定义一个邻接线性表来存储边
		List<AbstractGraph.Edge> edges = new ArrayList<AbstractGraph.Edge>();
		for(int u = 0;u < SIZE;u ++){
			//这里的i是代表16个单元格里的字符的位置
			for(int i = 0;i < 16;i ++){
				//得到指定位置的结点，即是一串以‘T’和‘H’组成的字符串
				char[] node = getNode(u);
				//其中‘H’代表0，正面朝上
				if(node[i] == 'H'){
					//得到翻转后的结点v的位置
					int v = 0;
					if(isDiag)
					    v = getDiagFlippedNode(node,i);
					else
						v = getNBHFlippedNode(node,i);
					//如果存在边的话，将这条边添加到线性表中去
					edges.add(new AbstractGraph.Edge(v,u));
				}
			}
		}
		return edges;
	}
	
	//找出翻转得到的结点的位置(0~2^16中的一个)
	public static int getDiagFlippedNode(char[] node,int i){
		//得到初始结点的位置(16个单元格里的字符的位置)的行和列
		int row = i / 4;
		int column = i % 4;
		//真正翻转一个‘H’格子和它的相邻结点
		//本身和对角线上的邻居被翻转
		flipACell(node, row, column);
		//左上角
		flipACell(node,row - 1,column - 1);
		//右下角
		flipACell(node,row + 1,column + 1);
		//右上角
		flipACell(node,row - 1,column + 1);
		//左下角
		flipACell(node,row + 1,column - 1);
		//在翻转的过程中，字符数组中元素发生改变
		return getIndex(node);
	}
	
	//找出翻转得到的结点的位置(0~2^16中的一个)
		public static int getNBHFlippedNode(char[] node,int i){
			//得到初始结点的位置(16个单元格里的字符的位置)的行和列
			int row = i / 4;
			int column = i % 4;
			//真正翻转一个‘H’格子和它的相邻结点
			//本身和对角线上的邻居被翻转
			flipACell(node,row,column);
			flipACell(node,row - 1,column);
			flipACell(node,row + 1,column);
			flipACell(node,row,column - 1);
			flipACell(node,row,column + 1);
			//在翻转的过程中，字符数组中元素发生改变
			return getIndex(node);
		}
		
	
	//真正翻转一个‘H’格子和它的相邻结点
	public static void flipACell(char[] node,int row,int column){
		if(row >= 0 && row <= 3 && column >= 0 && column <= 3){
			//行列与当前结点在16个单元格中的位置的关系为4*row +　column = i
			if(node[4*row + column] == 'H'){
				node[4*row + column] = 'T';
			}
			else{
				node[4*row + column] = 'H';
			}
		}
	}
	//得到结点的位置（0~2^16），将相当于把二进制转换为十进制
	public static int getIndex(char[] node){
		int index = 0;
		for(int i = 0;i < 16;i ++){
			//'T'对应的是1，为背面，'H'对应的是0，为正面
			if(node[i] == 'H'){
				index = index * 2 + 0;
			}
			else{
				index = index * 2 + 1;
			}
		}
		return index;
	}
	//得到指定位置的结点，一串字符
	public static char[] getNode(int index){
		char[] nodes = new char[16];
		for(int i = 0;i < 16;i ++){
			int num = index % 2;
			if(num == 0){
				nodes[15 - i] = 'H';
			}
			else{
				nodes[15 - i] = 'T';
			}
			index /= 2;
		}
		return nodes;
	}
	//得到指定结点和目标结点之间最短路径的顶点
	public List<Integer> getShortestPath(int index){
		return tree.getPath(index);
	}
	//在控制台上显示一个结点
	public static void printNode(char[] node){
		for(int i = 0;i < 16;i ++){
			if(i % 4 == 3){
				System.out.println(node[i]);
			}
			else{
				System.out.print(node[i]);
			}
		}
		System.out.println();
	}
}
