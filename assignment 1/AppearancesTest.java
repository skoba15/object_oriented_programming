package assign1;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.*;

public class AppearancesTest {
	// utility -- converts a string to a list with one
	// elem for each char.
	private List<String> stringToList(String s) {
		List<String> list = new ArrayList<String>();
		for (int i=0; i<s.length(); i++) {
			list.add(String.valueOf(s.charAt(i)));
			// note: String.valueOf() converts lots of things to string form
		}
		return list;
	}
	
	@Test
	public void testSameCount1() {
		List<String> a = stringToList("abbccc");
		List<String> b = stringToList("cccbba");
		assertEquals(3, Appearances.sameCount(a, b));
	}
	
	@Test
	public void testSameCount2() {
		// basic List<Integer> cases
		List<Integer> a = Arrays.asList(1, 2, 3, 1, 2, 3, 5);
		assertEquals(1, Appearances.sameCount(a, Arrays.asList(1, 9, 9, 1)));
		assertEquals(2, Appearances.sameCount(a, Arrays.asList(1, 3, 3, 1)));
		assertEquals(1, Appearances.sameCount(a, Arrays.asList(1, 3, 3, 1, 1)));
	}
	
	
	@Test
	public void testSameCount3(){
		// strings which have distinct number of chars of each one //
		List<String> a=stringToList("aabbcc");
		List<String> b=stringToList("abc");
		assertEquals(0, Appearances.sameCount(a, b));
	}
	
	@Test
	public void testSameCount4(){
		// advanced List<Integer> cases with an array of size 100//
		List<Integer> a = new ArrayList<Integer>();
		List<Integer> b = new ArrayList<Integer>();
		for(int i=0; i<100; i++){
			a.add(i%10);
			b.add(i%10);
		}
		assertEquals(10, Appearances.sameCount(a, b));
		
		a.add(1);
		assertEquals(9, Appearances.sameCount(a, b));
		
		a.add(2);
		assertEquals(8, Appearances.sameCount(a, b));
		
		b.add(3);
		assertEquals(7, Appearances.sameCount(a, b));
		
		for(int i=0; i<10; i++){
			a.add(10+i);
			b.add(20+i);
		}
		
		assertEquals(7, Appearances.sameCount(a, b));
		
	}
	
	@Test
	public void testSameCount5(){
		// empty lists checking and small cases //
		List<Integer> a = new ArrayList<Integer>();
		List<Integer> b = new ArrayList<Integer>();
		assertEquals(0, Appearances.sameCount(a, b));
		
		a.add(1);
		assertEquals(0, Appearances.sameCount(a, b));
		
		List<String> c = stringToList("a");
		List<String> d = stringToList("c");
		assertEquals(0, Appearances.sameCount(c, d));
		
		List<String> cc = stringToList("a");
		List<String> dd = stringToList("a");
		assertEquals(1, Appearances.sameCount(cc, dd));
	}
}
