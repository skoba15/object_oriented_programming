package assign1;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.*;

public class TetrisGridTest {
	
	// Provided simple clearRows() test
	// width 2, height 3 grid
	@Test
	public void testClear1() {
		boolean[][] before =
		{	
			{true, true, false, },
			{false, true, true, }
		};
		
		boolean[][] after =
		{	
			{true, false, false},
			{false, true, false}
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	
	
	// 4x4 table, tetris grid with a full top row //
	@Test
	public void testClear2(){
		boolean[][] before =
			{	
				{true, true, false, true},
				{false, true, true, true},
				{true, true, true, true},
				{true, true, true, true}
			};
			
			boolean[][] after =
			{	
				{true,  false, false, false},
				{false,  true, false, false},
				{true,  true, false, false},
				{true, true, false, false}
			};
			
			TetrisGrid tetris = new TetrisGrid(before);
			tetris.clearRows();
			assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	
	
	// 5x5 table, tetris grid with full bottom and top rows //
	@Test
	public void testClear3(){
		boolean[][] before =
			{	
				{true, false, false, true, true},
				{true, true, true, true, true},
				{true, false, true, false, true},
				{true, false, false, true, true},
				{true, false, true, false, true}
			};
			
			boolean[][] after =
			{	
				{false,  false, true, false, false},
				{true,  true, true, false, false},
				{false,  true, false, false, false},
				{false, false, true, false, false},
				{false, true, false, false, false}
			};
			
			TetrisGrid tetris = new TetrisGrid(before);
			tetris.clearRows();
			assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	
	// 5x5 table, tetris grid, without full rows //
	@Test
	public void testClear4(){
		boolean[][] before =
			{	
				{true, false, false, false, false},
				{true, true, true, false, false},
				{false, false, true, false, false},
				{true, false, false, false, false},
				{true, false, true, false, false}
			};
			
			boolean[][] after =
			{	
				{true, false, false, false, false},
				{true, true, true, false, false},
				{false, false, true, false, false},
				{true, false, false, false, false},
				{true, false, true, false, false}
			};
			
			TetrisGrid tetris = new TetrisGrid(before);
			tetris.clearRows();
			assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
	}
	
	
	@Test
	public void testClear5() {
		// small tests //
		boolean[][] before =
		{	
			{true, true, false}
		};
		
		boolean[][] after =
		{	
			{false, false, false}
		};
		
		TetrisGrid tetris = new TetrisGrid(before);
		tetris.clearRows();

		assertTrue( Arrays.deepEquals(after, tetris.getGrid()) );
		
		
		boolean[][] before1 =
			{	
				{false}
			};
			
		boolean[][] after1 =
			{	
					{false}
			};

		TetrisGrid tetris1 = new TetrisGrid(before1);
		tetris1.clearRows();

		assertTrue( Arrays.deepEquals(after1, tetris1.getGrid()) );
		
		
		
		boolean[][] before2 =
			{	
				{true}
			};
			
		boolean[][] after2 =
			{	
					{false}
			};

		TetrisGrid tetris2 = new TetrisGrid(before2);
		tetris2.clearRows();

		assertTrue( Arrays.deepEquals(after2, tetris2.getGrid()) );
	}
	
	
}
