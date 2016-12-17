package chapter27;

import java.util.List;

public interface Graph<V>{

	public int getSize();
	
	public List<V> getVertices();
	
	public V getVertex(int index);
	
	public int getIndex(V v);
	
	public List<Integer> getNeighbors(int index);
	
	public int getDegree(int v);
	
	public int[][] getAdjacencyMatrix();
	
	public void printAdjacencyMatrix();
	
	public void printEdges();
	
	public AbstractGraph<V>.Tree dfs(int v);
	
	public AbstractGraph<V>.Tree bfs(int v);
}
