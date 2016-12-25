package compress;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public  class HuffmanTree<E> implements Serializable{
	private LinkedList<Node<E>> list = new LinkedList<Node<E>>();
	private Node<E>[] nodes;
	private Node<E> head;
	private int size;
	
	public HuffmanTree(Node<E>[] nodes){
		this.nodes = nodes;
		for(int i = 0; i < nodes.length;i++){
			if(nodes[i].weight != 0){
				list.add(nodes[i]);
				size++;
			}
		}
		toTree();
	}
	
	
	public void toTree(){
		Node<E> minNode1;
		Node<E> minNode2;
		while( list.size() > 1){
			minNode1 = list.get(0);
		for(int i = 0;i <list.size();i++){
			int weight = list.get(i).weight;
			if(minNode1.weight >= weight){
				minNode1 =  list.get(i);
			}
		  }
		list.remove(minNode1);
		
		minNode2 = list.get(0);
		for(int i = 0;i < list.size();i++){
			int weight = list.get(i).weight;
			if(minNode2.weight >= weight){
				minNode2 = list.get(i);
			}
		  }
		list.remove(minNode2);
		list.add(buildTree(minNode1,minNode2));
		}
		head = list.get(0);
	}
	
	
	public Node<E> getHead(){
		return this.head;
	}
	
	public Map<E, String> getEncodeMap(){
		Map<E, String>  encodeMap = new HashMap<E,String>();
		for(int i = 0;i < nodes.length;i++){
			encodeMap.put(nodes[i].data, count(nodes[i]));
		}
		return encodeMap;
	}
	
	@Override
	public String toString(){
		String s = "";
		for(int i = 0;i < nodes.length;i++){
			s += nodes[i].data + " : " + count(nodes[i]) + "\n";
		}
		
		return s;
	}
	
	public String count(Node<E> currentNode){
		Node<E> currentChild = currentNode;
		Node<E> currentParent = currentChild.parent;
		String s ="";
		while(currentParent != null){
			if(currentParent.Lchild.equals(currentChild)){
				s = "1" + s;
			}else{
				s = "0" + s;
			}
			currentChild = currentParent;
			currentParent = currentParent.parent;
		}
		return s;
	}
	
	public Node<E> buildTree(Node<E> LTree,Node<E> RTree){
		Node<E> node = new Node<E>();
		node.weight = LTree.weight + RTree.weight;
		node.Lchild = LTree;
		node.Rchild = RTree;
		LTree.parent = node;
		RTree.parent = node;
		return node;
	}
	
	public static class Node<E> implements Serializable{
		E data;
		int weight;
		Node<E> parent;
		Node<E> Rchild;
		Node<E> Lchild;
		public Node(){
			
		}
		public Node(E e) {
			data = e;
		}
	}
}
	
	


