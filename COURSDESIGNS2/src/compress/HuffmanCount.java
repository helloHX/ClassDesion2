package compress;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
		
		BufferedInputStream fileInputStrem = new BufferedInputStream(
				new FileInputStream(new File(filename)));
		
	    BufferedReader fileInput =new BufferedReader(
				    new InputStreamReader(fileInputStrem,"utf-8"));

		Map<Character,Integer> weightingMap = new HashMap<>();
		String ASCOLLletter;
		
		while((ASCOLLletter = fileInput.readLine()) != null){
//			String letter =  ASCOLLletter;
			char[] letters = ASCOLLletter.toCharArray();
			
			for (int i = 0; i < letters.length; i++) {
				int Amont = 0;
				if(weightingMap.get(letters[i]) != null)
					Amont = weightingMap.get(letters[i]);
				weightingMap.put(letters[i],Amont + 1);
			}	
			
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
