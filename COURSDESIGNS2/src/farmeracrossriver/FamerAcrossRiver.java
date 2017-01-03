package farmeracrossriver;

import java.util.List;


public class FamerAcrossRiver {
	public static void main(String[] args) {
		System.out.println("The Famer Across The River");
		char[] initialNode= {0,0,0,0};//��ʼ�������ж��󶼷����ϰ�
		
		FamerAcrossRiverModel mode = new FamerAcrossRiverModel();
		int index = FamerAcrossRiverModel.getIndex(initialNode);
		List<Integer> path = 
				mode.getShortestPath(index);
		
		for (int i = 0; i < path.size(); i++) {
			FamerAcrossRiverModel.printNode(
					FamerAcrossRiverModel.getNode(path.get(i).intValue()));
		}
	}
}
