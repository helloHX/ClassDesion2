package chapter27;

import java.util.List;
import java.util.Scanner;

public class NineTail {

	public static void main(String[] args){
		System.out.println("Enter an initail 16 coin H'");
		Scanner input = new Scanner(System.in);
		String s = input.nextLine();
		char[] initialNode = s.toCharArray();
	
		NineTailModel mode = new NineTailModel();
		List<Integer> path = 
				mode.getShortestPath(NineTailModel.getIndex(initialNode));
		System.out.println("The steps to flip the coins are");
		for (int i = 0; i < path.size(); i++) {
			NineTailModel.printNode(
					NineTailModel.getNode(path.get(i).intValue()));
		}
	}
}
