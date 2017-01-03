package tool;

public class MyStack<E> {
	
	private E[] elements;
	private int size;
	
	public MyStack() {
		
	}
	
	@SuppressWarnings("unchecked")
	public MyStack(int capacity) {
		elements = (E[])new Object[capacity];
	}
	
	public void push(E value) {
		if(size >= elements.length) {
			@SuppressWarnings("unchecked")
			E[] temp = (E[]) new Object[elements.length * 2];
			System.arraycopy( elements , 0 , temp , 0 , elements.length );
			elements = temp ;
		}
		elements[size++] = value;
	}
	
	public E pop() {
		return elements[size--] ;
	}
	
	public E getElement(int index) {
		return elements[index];
	}
	
	public E peek() {
		return elements[size-1] ;
	}
	
	public boolean empty() {
		return size == 0;
	}
	
	public int getSize() {
		return size ;
	}

}
