package assign1;

import java.util.*;

public class Appearances {
	
	/**
	 * Returns the number of elements that appear the same number
	 * of times in both collections. Static method. (see handout).
	 * @param <T>
	 * @return number of same-appearance elements
	 * 
	 */
	
	
	private static <T> void informationProcessing(Collection<T> a, HashMap<Integer, Integer> map){
		Iterator it=a.iterator();
		while(it.hasNext()){
			T obj=(T) it.next();
		    int code=obj.hashCode();
		    if(map.containsKey(code)){
		    	map.put(code, map.get(code)+1);
		    }
		    else{
		    	map.put(code, 1);
		    }
		}
	}
	
	private static <T> int calculateResult(HashMap<Integer, Integer> map1, HashMap<Integer, Integer> map2){
		int result=0;
		for(Integer key : map1.keySet()){
			if(map2.containsKey(key)){
				if(map1.get(key)==map2.get(key)){
					result++;
				}
			}
		}
		return result;
	}
	
	public static <T> int sameCount(Collection<T> a, Collection<T> b) {
		int result=0;
		HashMap<Integer, Integer> map1=new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> map2=new HashMap<Integer, Integer>();
		informationProcessing(a, map1);
		informationProcessing(b, map2);
		result=calculateResult(map1, map2);
		return result;
	}
	
}
