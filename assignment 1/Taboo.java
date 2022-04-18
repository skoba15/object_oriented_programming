/*
 HW1 Taboo problem class.
 Taboo encapsulates some rules about what objects
 may not follow other objects.
 (See handout).
*/
package assign1;

import java.util.*;

public class Taboo<T> {
	private HashMap<T, HashSet<T> > map=new HashMap<T, HashSet<T> >();
	
	/**
	 * Constructs a new Taboo using the given rules (see handout.)
	 * @param rules rules for new Taboo
	 */
	public Taboo(List<T> rules) {
		
		Iterator it=rules.iterator();
		T previous=null;
		while(it.hasNext()){
			T obj=(T)it.next();
			if(previous!=null && obj!=null){
				if(map.containsKey(previous)){
					map.get(previous).add(obj);
				}
				else{
					HashSet<T> h=new HashSet<T>();
					map.put(previous, h);
					map.get(previous).add(obj);
				}
			}
			previous=obj;
		}
	}
	
	/**
	 * Returns the set of elements which should not follow
	 * the given element.
	 * @param elem
	 * @return elements which should not follow the given element
	 */
	public Set<T> noFollow(T elem) {
		 if(map.containsKey(elem)){
			 return map.get(elem);
		 }
		 HashSet<T> emptySet=new HashSet<T>();
		 return emptySet;
	}
	
	/**
	 * Removes elements from the given list that
	 * violate the rules (see handout).
	 * @param list collection to reduce
	 */
	public void reduce(List<T> list) {
		T previous=null;
		for(int i=0; i<list.size(); i++){
			T curObj=list.get(i);
			if(previous!=null){
				if(map.containsKey(previous) && map.get(previous).contains(curObj)){
					list.remove(i);
					i--;
				}
			}
			previous=list.get(i);
		}
	}
}
