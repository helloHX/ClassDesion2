package tree;

public interface  Tree<E extends Comparable<E>>{
	public boolean search(E e);
	
	public boolean insert(E e);
	
	public boolean delete(E e);
	
	public void inorder();
	
	public void postorder();
	
	public int getSize();
	
	public boolean isEmpty();
	
	public java.util.Iterator iterator();
}
