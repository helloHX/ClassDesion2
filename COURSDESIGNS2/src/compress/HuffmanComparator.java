package compress;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map.Entry;

public class HuffmanComparator<E extends Entry>
         implements Comparator<E>,Serializable{

	@Override
	public int compare(E o1,
			E o2) {
		String s1 = (String) o1.getValue();
		String s2 = (String) o2.getValue();
		if(s1.length() < s2.length())
			return 1;
		else if(s1.length() == s2.length()){
			if(s1.compareTo(s2) < 0)
				return 1;
			else if(s1.compareTo(s2) == 0)
			return 0;
			else
				return -1;
		}
		else
			return -1;
	
	}

}
