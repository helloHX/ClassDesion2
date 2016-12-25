package farmeracrossriver;

import java.util.ArrayList;
import java.util.List;

import chapter27.AbstractGraph;
import chapter27.UnweightedGraph;

public class FamerAcrossRiverModel {
	public final static int NUMBER_OF_NODE = 16;
	protected AbstractGraph<Integer>.Tree tree;

	public FamerAcrossRiverModel() {
		List<AbstractGraph.Edge> edges = getEdges();
		UnweightedGraph<Integer> graph = new UnweightedGraph<Integer>(edges,
				NUMBER_OF_NODE);
		tree = graph.bfs(15);
	}

	private List<AbstractGraph.Edge> getEdges() {
		List<AbstractGraph.Edge> edges = new ArrayList<AbstractGraph.Edge>();
		for (int u = 0; u < NUMBER_OF_NODE; u++) {
			for (int k = 0; k < 4; k++) {
				char[] node = getNode(u);
				if(node[0] == node[k]){
				int v = getMoveNode(node, k);
				if(v != -1){
				edges.add(new AbstractGraph.Edge(v, u));
				  }
			  }
			}
		}
		return edges;
	}

	public static int getMoveNode(char[] node, int posistion) {
		char[] tempNode = new char[node.length];
		
		System.arraycopy(node, 0, tempNode, 0, node.length);
		
		if (posistion != 0) {
			MoveThing(tempNode, 0);
			MoveThing(tempNode, posistion);
		} else
			MoveThing(tempNode,0);
		
		boolean sheepWolf = (tempNode[1] == tempNode[2]);
		boolean sheepCabbage = (tempNode[2] == tempNode[3]);
		
		if((sheepWolf && (tempNode[1] != tempNode[0])) 
				|| (sheepCabbage && (tempNode[2] != tempNode[0]))){//狼羊不能够在一起，羊菜不能够在一起，除非人在场
			return -1;
		}else{
			System.arraycopy(tempNode, 0,node, 0, tempNode.length);
			return getIndex(node);
		}
	}

	public static void MoveThing(char[] node, int posistion) {
		if (node[posistion] == '0')
			node[posistion] = '1';
		else
			node[posistion] = '0';

	}

	public static char[] getNode(int index) {
		char[] result = new char[4];

		for (int i = 0; i < 4; i++) {
			int digit = index % 2;
			if (digit == 0)
				result[3 - i] = '0';
			else
				result[3 - i] = '1';
			index = index / 2;
		}
		
		return result;
	}

	public static int getIndex(char[] node) {
		int result = 0;
		for (int i = 0; i < 4; i++)
			if (node[i] == '1')
				result = result * 2 + 1;
			else
				result = result * 2 + 0;

		return result;
	}

	public List<Integer> getShortestPath(int nodeIndex) {
		return tree.getPath(nodeIndex);
	}

	public static void printNode(char[] node) {

		for (int i = 0; i < 4; i++) {
			String position = node[i] == '0' ? "南" : "北";
			System.out.print(node[i]);
			switch (i) {
			case 0:
				System.out.print("农夫在" + position);
				break;
			case 1:
				System.out.print(",狼在" + position);
				break;
			case 2:
				System.out.print(",羊在" + position);
				break;
			case 3:
				System.out.print(",白菜在" + position);
				break;
			}
		}
		System.out.println();

	}

}
