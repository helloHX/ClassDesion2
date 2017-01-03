package coinGame;

import java.util.ArrayList;
import java.util.List;

import tool.AbstractGraph;
import tool.UnweightedGraph;

public class SixteenTailModel {
	//�ܹ���2��16�η������
	public static final int SIZE = (int) Math.pow(2,16);
	protected AbstractGraph<Integer>.Tree tree;
	
	public SixteenTailModel(boolean isDiag){
		//ÿһ���߶�Ӧ��һ����㵽��һ�������ƶ�
		List<AbstractGraph.Edge> edges = getEdges(isDiag);
		//����һ��ͼ��������Ϊ���֮��ıߺͽ�����
		UnweightedGraph<Integer> graph = new UnweightedGraph<Integer>(edges,SIZE);
		//�õ�һ����Ŀ����2^16Ϊ�����Ĺ������������
		tree = graph.bfs(SIZE - 1);
	}
	//�鿴���u�ܷ�ת�����v
	public List<AbstractGraph.Edge> getEdges(boolean isDiag){
		//����һ���ڽ����Ա����洢��
		List<AbstractGraph.Edge> edges = new ArrayList<AbstractGraph.Edge>();
		for(int u = 0;u < SIZE;u ++){
			//�����i�Ǵ���16����Ԫ������ַ���λ��
			for(int i = 0;i < 16;i ++){
				//�õ�ָ��λ�õĽ�㣬����һ���ԡ�T���͡�H����ɵ��ַ���
				char[] node = getNode(u);
				//���С�H������0�����泯��
				if(node[i] == 'H'){
					//�õ���ת��Ľ��v��λ��
					int v = 0;
					if(isDiag)
					    v = getDiagFlippedNode(node,i);
					else
						v = getNBHFlippedNode(node,i);
					//������ڱߵĻ�������������ӵ����Ա���ȥ
					edges.add(new AbstractGraph.Edge(v,u));
				}
			}
		}
		return edges;
	}
	
	//�ҳ���ת�õ��Ľ���λ��(0~2^16�е�һ��)
	public static int getDiagFlippedNode(char[] node,int i){
		//�õ���ʼ����λ��(16����Ԫ������ַ���λ��)���к���
		int row = i / 4;
		int column = i % 4;
		//������תһ����H�����Ӻ��������ڽ��
		//����ͶԽ����ϵ��ھӱ���ת
		flipACell(node, row, column);
		//���Ͻ�
		flipACell(node,row - 1,column - 1);
		//���½�
		flipACell(node,row + 1,column + 1);
		//���Ͻ�
		flipACell(node,row - 1,column + 1);
		//���½�
		flipACell(node,row + 1,column - 1);
		//�ڷ�ת�Ĺ����У��ַ�������Ԫ�ط����ı�
		return getIndex(node);
	}
	
	//�ҳ���ת�õ��Ľ���λ��(0~2^16�е�һ��)
		public static int getNBHFlippedNode(char[] node,int i){
			//�õ���ʼ����λ��(16����Ԫ������ַ���λ��)���к���
			int row = i / 4;
			int column = i % 4;
			//������תһ����H�����Ӻ��������ڽ��
			//����ͶԽ����ϵ��ھӱ���ת
			flipACell(node,row,column);
			flipACell(node,row - 1,column);
			flipACell(node,row + 1,column);
			flipACell(node,row,column - 1);
			flipACell(node,row,column + 1);
			//�ڷ�ת�Ĺ����У��ַ�������Ԫ�ط����ı�
			return getIndex(node);
		}
		
	
	//������תһ����H�����Ӻ��������ڽ��
	public static void flipACell(char[] node,int row,int column){
		if(row >= 0 && row <= 3 && column >= 0 && column <= 3){
			//�����뵱ǰ�����16����Ԫ���е�λ�õĹ�ϵΪ4*row +��column = i
			if(node[4*row + column] == 'H'){
				node[4*row + column] = 'T';
			}
			else{
				node[4*row + column] = 'H';
			}
		}
	}
	//�õ�����λ�ã�0~2^16�������൱�ڰѶ�����ת��Ϊʮ����
	public static int getIndex(char[] node){
		int index = 0;
		for(int i = 0;i < 16;i ++){
			//'T'��Ӧ����1��Ϊ���棬'H'��Ӧ����0��Ϊ����
			if(node[i] == 'H'){
				index = index * 2 + 0;
			}
			else{
				index = index * 2 + 1;
			}
		}
		return index;
	}
	//�õ�ָ��λ�õĽ�㣬һ���ַ�
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
	//�õ�ָ������Ŀ����֮�����·���Ķ���
	public List<Integer> getShortestPath(int index){
		return tree.getPath(index);
	}
	//�ڿ���̨����ʾһ�����
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
