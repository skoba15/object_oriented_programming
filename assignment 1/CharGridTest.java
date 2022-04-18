// Test cases for CharGrid -- a few basic tests are provided.
package assign1;

import static org.junit.Assert.*;
import org.junit.Test;

public class CharGridTest {
	
	@Test
	public void testCharArea1() {
		// basic test for  testCharArea //
		char[][] grid = new char[][] {
				{'a', 'y', ' '},
				{'x', 'a', 'z'},
			};
		
		
		CharGrid cg = new CharGrid(grid);
				
		assertEquals(4, cg.charArea('a'));
		assertEquals(1, cg.charArea('z'));
		
		
		
	}
	
	
	@Test
	public void testCharArea2() {
		// Tests the case, where some letters occur on the same line //
		char[][] grid = new char[][] {
				{'c', 'a', ' '},
				{'b', ' ', 'b'},
				{' ', ' ', 'a'}
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(6, cg.charArea('a'));
		assertEquals(3, cg.charArea('b'));
		assertEquals(1, cg.charArea('c'));
	}
	
	
	
	@Test
	public void testCharArea3(){
		// tests the case, where four letters fix a rectangle //
		char[][] grid = new char[][] {
			{'c', 'a', 'c'},
			{'a', 'b', 'b'},
			{' ', 'c', 'a'},
			{' ', 'a', ' '}
		};
		CharGrid cg=new CharGrid(grid);
		assertEquals(12, cg.charArea('a'));
		assertEquals(2, cg.charArea('b'));
		assertEquals(9, cg.charArea('c'));
	}
	
	@Test
	public void testCharArea4(){
		// test for empty grid //
		char[][] grid = new char[][] {
			{}
		};
		CharGrid cg=new CharGrid(grid);
		assertEquals(0, cg.charArea('a'));
		assertEquals(0, cg.charArea('b'));
		assertEquals(0, cg.charArea('c'));
	}
	
	
	@Test
	public void testCharArea5(){
		// test for a grid of size 1x1 //
		char[][] grid = new char[][] {
			{'a'}
		};
		CharGrid cg=new CharGrid(grid);
		assertEquals(1, cg.charArea('a'));
		assertEquals(0, cg.charArea(' '));
		assertEquals(0, cg.charArea('c'));
	}
	
	
	
	@Test
	public void testCountPlus1(){
		// tests the method for a grid full of same chars //
		char [][] grid=new char[][] {
			{'a', 'a', 'a'},
			{'a', 'a', 'a'},
			{'a', 'a', 'a'}
			
		};
		
		CharGrid cg=new CharGrid(grid);
		
		assertEquals(1, cg.countPlus());
	}
	

	@Test
	public void testCountPlus2(){
		// advanced test for the method //
		char [][] grid=new char[][] {
			{' ', ' ', 'p', ' ', ' ', ' ', ' ', ' ', ' '},
			{' ', ' ', 'p', ' ', ' ', ' ', ' ', 'x', ' '},
			{'p', 'p', 'p', 'p', 'p', ' ', 'x', 'x', 'x'},
			{' ', ' ', 'p', ' ', ' ', 'y', ' ', 'x', ' '},
			{' ', ' ', 'p', ' ', 'y', 'y', 'y', ' ', ' '},
			{'z', 'z', 'z', 'z', 'z', 'y', 'z', 'z', 'z'},
			{' ', ' ', 'x', 'x', ' ', 'y', ' ', ' ', ' '},
			
		};
		
		CharGrid cg=new CharGrid(grid);
		
		assertEquals(2, cg.countPlus());
	}
	
	@Test
	public void testCountPlus3(){
		//advanced test for the method checks patterns which form a cross, but have distinct characters
		// and cases of crosses which have arms of different sizes //
		char [][] grid=new char[][] {
			{' ', ' ', ' ', ' ', 'a', ' ', ' ', 'x', ' '},
			{' ', ' ', 'b', 'a', 'a', 'a', 'x', 'x', 'x'},
			{' ', 'p', 'p', 'p', 'a', 'y', 'x', 'x', 'x'},
			{' ', ' ', 'p', ' ', ' ', 'y', ' ', 'x', ' '},
			{' ', 'z', ' ', ' ', 'y', 'y', 'y', 'y', ' '},
			{'z', 'z', 'z', 'z', 'z', 'y', 'z', 'z', 'z'},
			{' ', 'z', 'x', 'x', ' ', 'y', ' ', ' ', ' '},
			
		};
		
		CharGrid cg=new CharGrid(grid);
		
		assertEquals(1, cg.countPlus());
	}
	
	
	@Test
	public void testCountPlus4(){
		// tests some small cases //
		char [][] grid=new char[][] {
			{'a'},
		};
		
		CharGrid cg=new CharGrid(grid);
		
		assertEquals(0, cg.countPlus());
		
		char [][] grid1=new char[][] {
			{},
			
		};
		
		CharGrid cg1=new CharGrid(grid1);
		
		assertEquals(0, cg1.countPlus());
		
		
		char [][] grid2=new char[][] {
			{'a', 'a'}, 
			{'a', 'a'}, 
			{'a', 'a'}
			
		};
		
		CharGrid cg2=new CharGrid(grid2);
		
		assertEquals(0, cg2.countPlus());
		
		char [][] grid3=new char[][] {
			{'a', 'a', 'a'},
			
		};
		
		CharGrid cg3=new CharGrid(grid3);
		
		assertEquals(0, cg3.countPlus());
	}
	
	
	@Test
	public void testCountPlus5(){
		// test for some strange chars //
		char [][] grid=new char[][] {
			{'@', '#', ' ', ' ', 'a', ' ', ' ', '*', ' '},
			{'@', '#', '#', '<', '>', '.', '*', '*', '*'},
			{'(', '#', '#', '#', 'a', '^', 'x', '*', 'x'},
			{'#', '#', '#', ' ', ' ', '^', ' ', 'x', ' '},
			{' ', 'z', ' ', '^', '^', '^', '^', '^', ' '},
			{'z', '%', '%', 'z', '2', '^', '^', 'z', 'z'},
			{' ', 'z', 'x', 'x', ' ', '^', ' ', ' ', ' '},
			
		};
		
		CharGrid cg=new CharGrid(grid);
		
		assertEquals(3, cg.countPlus());
	}
	
	
}
