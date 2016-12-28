package maze;

import java.util.LinkedList;

public class Stack<T>
{
	LinkedList<T> list = new LinkedList<T>();
	
	public T pop()
	{
		return list.remove(0);
	}
	
	public void push(T t)
	{
		list.add(0, t);
	}
	
	public T peek()
	{
		return list.get(0);
	}
	
	public boolean empty()
	{
		return list.size() == 0;
	}
}
