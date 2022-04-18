// TabooTest.java
// Taboo class tests -- nothing provided.
package assign1;

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class TabooTest<T> {
	
	private List<T> arrayToList(T[] arr){
		List<T> list=new ArrayList<T>();
		for(int i=0; i<arr.length; i++){
			list.add(arr[i]);
		}
		return list;
	}
	
	
	

	@Test
	public void TabooTest1(){
		// basic test for noFollow method //
		String[] rules={"a", "c", "a", "b"};
		Taboo t=new Taboo(arrayToList((T[]) rules));
		HashSet<String> noFollows=(HashSet<String>) t.noFollow("a");
		assertEquals(true, (noFollows.size()==2 && noFollows.contains("b") && noFollows.contains("c")));
		
		noFollows=(HashSet<String>) t.noFollow("c");
		assertEquals(true, noFollows.size()==1 && noFollows.contains("a"));
		
		noFollows=(HashSet<String>) t.noFollow("b");
		assertEquals(true, noFollows.size()==0);
	}
	
	@Test
	public void TabooTest2(){
		// Test for noFollow method of Integer array consisting of nulls //
		Integer[] rules={null, 1, 2, 3, 3, null, 1, null, 1, 4, 2, 5, null};
		Taboo t=new Taboo(arrayToList((T[]) rules));
		HashSet<String> noFollows=(HashSet<String>) t.noFollow(1);
		assertEquals(true, noFollows.size()==2 && noFollows.contains(2) && noFollows.contains(4));
		
		noFollows=(HashSet<String>) t.noFollow(2);
		assertEquals(true, noFollows.size()==2 && noFollows.contains(5) && noFollows.contains(3));
		
		noFollows=(HashSet<String>) t.noFollow(4);
		assertEquals(true, noFollows.size()==1 && noFollows.contains(2));
	}
	
	
	@Test
	public void TabooTest3(){
		// Test for noFollow method for small test cases //
		Integer[] rules={null, 1};
		Taboo t=new Taboo(arrayToList((T[]) rules));
		HashSet<String> noFollows=(HashSet<String>) t.noFollow(1);
		assertEquals(true, noFollows.size()==0);
		
		Integer[] rules1={2, 1};
		Taboo t1=new Taboo(arrayToList((T[]) rules1));
		HashSet<String> noFollows1=(HashSet<String>) t1.noFollow(2);
		assertEquals(true, noFollows1.size()==1 && noFollows1.contains(1));
		
		noFollows1=(HashSet<String>) t1.noFollow(1);
		assertEquals(true, noFollows.size()==0);
		
		noFollows1=(HashSet<String>) t1.noFollow(3); //checks the method for a non-existent element in the rules //
		assertEquals(true, noFollows1.size()==0);
	}
	
	
	@Test
	public void TabooTest4(){
		// Tests reduce method, basic test //
		String[] rules={"a", "c", "a", "b"};
		Taboo t=new Taboo(arrayToList((T[]) rules));
		String[] t1={"a", "c", "b", "x", "c", "a"};
		String[] arr={"a", "x", "c"};
		List<String> correctAns=(List<String>) arrayToList((T[]) arr);
		List<String> toReduce=(List<String>) arrayToList((T[]) t1);
		t.reduce(toReduce);
		assertEquals(true, toReduce.equals(correctAns));
		
		String[] t2={"dzz", "a", "c", "euf", "a"};
		String[] arr2={"dzz", "a", "euf", "a"};
		List<String> correctAns2=(List<String>) arrayToList((T[]) arr2);
		List<String> toReduce2=(List<String>) arrayToList((T[]) t2);
		t.reduce(toReduce2);
		assertEquals(true, toReduce2.equals(correctAns2));
		
		String[] t3={"den", "b", "c", "b", "a"};
		String[] arr3={"den", "b", "c", "b", "a"};
		List<String> correctAns3=(List<String>) arrayToList((T[]) arr3);
		List<String> toReduce3=(List<String>) arrayToList((T[]) t3);
		t.reduce(toReduce3);
		assertEquals(true, toReduce3.equals(correctAns3));
		
	}
	
	@Test
	public void TabooTest5(){
		// Tests reduce method for Integers, more advanced test //
		Integer[] rules={1, 2, null, 1, 3, 4, 1, null, 4, 2};
		Taboo t=new Taboo(arrayToList((T[]) rules));
		Integer[] t1={1, 3, 2, 2, 3, 3, 3, 2, 2, 2};
		Integer[] arr={1};
		List<Integer> correctAns=(List<Integer>) arrayToList((T[]) arr);
		List<Integer> toReduce=(List<Integer>) arrayToList((T[]) t1);
		t.reduce(toReduce);
		
		assertEquals(true, toReduce.equals(correctAns));
		
		Integer[] t2={1, 4, 3, 2, 5, 1, 701, 2, 1, 3};
		Integer[] arr2={1, 4, 3, 2, 5, 1, 701, 2, 1};
		List<Integer> correctAns2=(List<Integer>) arrayToList((T[]) arr2);
		List<Integer> toReduce2=(List<Integer>) arrayToList((T[]) t2);
		t.reduce(toReduce2);
		assertEquals(true, toReduce2.equals(correctAns2));
		
		Integer[] t3={1, 3, 4, 1, 2, 3, 512, 3};
		Integer[] arr3={1, 4, 3, 512, 3};
		List<Integer> correctAns3=(List<Integer>) arrayToList((T[]) arr3);
		List<Integer> toReduce3=(List<Integer>) arrayToList((T[]) t3);
		t.reduce(toReduce3);
		assertEquals(true, toReduce3.equals(correctAns3)); 
	}
	
	
	@Test
	public void TabooTest6(){
		// Tests reduce method for small cases, empty sets //
		String[] rules={};
		Taboo t=new Taboo(arrayToList((T[]) rules));
		String[] t1={"abcd", "eg", "sad"};
		String[] arr={"abcd", "eg", "sad"};
		List<String> correctAns=(List<String>) arrayToList((T[]) arr);
		List<String> toReduce=(List<String>) arrayToList((T[]) t1);
		t.reduce(toReduce);
		assertEquals(true, toReduce.equals(correctAns));
		
		Integer[] rules2={1, 2, 3};
		Integer[] t2={};
		Integer[] arr2={};
		List<Integer> correctAns2=(List<Integer>) arrayToList((T[]) arr2);
		List<Integer> toReduce2=(List<Integer>) arrayToList((T[]) t2);
		t.reduce(toReduce2);
		assertEquals(true, toReduce2.equals(correctAns2));
	}
	
	
}
