// StringCodeTest
// Some test code is provided for the early HW1 problems,
// and much is left for you to add.

package assign1;

import static org.junit.Assert.*;
import org.junit.Test;

public class StringCodeTest {
	//
	// blowup
	//
	@Test
	public void testBlowup1() {
		// basic cases
		assertEquals("xxaaaabb", StringCode.blowup("xx3abb"));
		assertEquals("xxxZZZZ", StringCode.blowup("2x3Z"));
		assertEquals("xxaxx", StringCode.blowup("1xa1x"));
		assertEquals("xxa", StringCode.blowup("1xa"));
		assertEquals("xxxxxx", StringCode.blowup("5x"));
		assertEquals("x", StringCode.blowup("x"));
	}
	
	@Test
	public void testBlowup2() {
		// things with digits
		assertEquals("1xxx", StringCode.blowup("11xx"));
		assertEquals("xxx", StringCode.blowup("2x"));
		assertEquals("2112xxx", StringCode.blowup("1212x"));
		
		// digit at end
		assertEquals("axxx", StringCode.blowup("a2x3"));
		assertEquals("bb233", StringCode.blowup("bb123"));
		assertEquals("2xxx", StringCode.blowup("12x2"));
		
		// digits next to each other
		assertEquals("a33111", StringCode.blowup("a231"));
		assertEquals("112222233333333", StringCode.blowup("111222333"));
		assertEquals("b233bbbb", StringCode.blowup("b123b"));
		
		// try a 0
		assertEquals("aabb", StringCode.blowup("aa0bb"));
		assertEquals("", StringCode.blowup("0"));
		assertEquals("abcd", StringCode.blowup("0a0b0cd"));
	}
	
	@Test
	public void testBlowup3() {
		// weird chars, empty string
		assertEquals("AB&&,- ab", StringCode.blowup("AB&&,- ab"));
		assertEquals("", StringCode.blowup(""));
		
		// string with only digits
		assertEquals("", StringCode.blowup("2"));
		assertEquals("33", StringCode.blowup("23"));
	}
	
	
	//
	// maxRun
	//
	@Test
	public void testRun1() {
		assertEquals(2, StringCode.maxRun("hoopla"));
		assertEquals(3, StringCode.maxRun("hoopllla"));
		assertEquals(1, StringCode.maxRun("b"));
		assertEquals(10, StringCode.maxRun("bbbbbbbbbb"));
		assertEquals(1, StringCode.maxRun("hopla"));
	}
	
	@Test
	public void testRun2() {
		assertEquals(3, StringCode.maxRun("abbcccddbbbxx"));
		assertEquals(0, StringCode.maxRun(""));
		assertEquals(3, StringCode.maxRun("hhhooppoo"));
		assertEquals(3, StringCode.maxRun("hhhppp"));
	}
	
	@Test
	public void testRun3() {
		// "evolve" technique -- make a series of test cases
		// where each is change from the one above.
		assertEquals(1, StringCode.maxRun("123"));
		assertEquals(2, StringCode.maxRun("1223"));
		assertEquals(2, StringCode.maxRun("112233"));
		assertEquals(3, StringCode.maxRun("1112233"));
		assertEquals(4, StringCode.maxRun("1112222333"));
		assertEquals(5, StringCode.maxRun("111112222333"));
	}

	@Test
	public void testIntersect1(){
		// basic cases //
		assertEquals(true, StringCode.stringIntersect("abcdefg", "abcdefg", 7));
		assertEquals(false, StringCode.stringIntersect("abc", "def", 3));

	}
	
	@Test
	public void testIntersect2(){
		// more advanced cases //
		assertEquals(true, StringCode.stringIntersect("abcdef", "kxabcdefghijk", 3));
		assertEquals(false, StringCode.stringIntersect("abcd", "ktabcdefg", 5));
		assertEquals(true, StringCode.stringIntersect("aaabcccc", "dddddbfffff", 1));
		assertEquals(false, StringCode.stringIntersect("abababa", "abcaabcaabca", 3));
	}
	
	
	@Test
	public void testIntersect3(){
		// small cases including empty strings //
		assertEquals(false, StringCode.stringIntersect("", "", 1));
		assertEquals(false, StringCode.stringIntersect("", "abcd", 1));
		assertEquals(true, StringCode.stringIntersect("a", "a", 1));
		assertEquals(false, StringCode.stringIntersect("a", "b", 1));
		assertEquals(false, StringCode.stringIntersect("ab", "ba", 2));
	}
	
}
