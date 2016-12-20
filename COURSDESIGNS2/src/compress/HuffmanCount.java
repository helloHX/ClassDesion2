package compress;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import compress.HuffmanTree.Node;

public class HuffmanCount {

	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner(System.in);
		System.out.println("enter file path");
		
		String filename = input.nextLine();
		BufferedInputStream fileInput = new BufferedInputStream(
				new FileInputStream(new File(filename)));
		
		Map<Character,Integer> weightingMap = new HashMap<>();
		int ASCOLLletter;
		
		while((ASCOLLletter = fileInput.read()) != -1){
			char letter = (char) ASCOLLletter;
			int Amont = 0;
			if(weightingMap.get(letter) != null)
				Amont = weightingMap.get(letter);
			weightingMap.put(letter,Amont + 1);
		}
		
		Set<Entry<Character, Integer>> entrySet = weightingMap.entrySet();//获取entry对象
		List<Entry<Character, Integer>> entryArray = new ArrayList<>(entrySet);//方便遍历
		
		Node[] nodes = new Node[entrySet.size()];
		
		for (int i = 0; i < entrySet.size(); i++) {
			Map.Entry<Character, Integer> oneEntry = entryArray.get(i);
			nodes[i] = new Node(oneEntry.getKey());
			nodes[i].weight = oneEntry.getValue();
		}
		
		System.out.println(new HuffmanTree<>(nodes));
		
	}
}
