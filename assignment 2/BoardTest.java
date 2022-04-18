package tetris;

import static org.junit.Assert.*;

import org.junit.*;

public class BoardTest {
	Board b, bAdvanced, bAdvanced2;
	Piece pyr1, pyr2, pyr3, pyr4, s, sRotated, stick1, stick2, L21, L22, L23, L24, S21, S11, S12, L11, L12, L13;

	// This shows how to build things in setUp() to re-use
	// across tests.
	
	// In this case, setUp() makes shapes,
	// and also a 3X6 board, with pyr placed at the bottom,
	// ready to be used by tests.
	@Before
	public void setUp() throws Exception {
		b = new Board(3, 6);
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		
		
		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();
		b.place(pyr1, 0, 0);
		
		
		bAdvanced=new Board(5, 6);
		stick1=new Piece(Piece.STICK_STR);
		stick2=stick1.computeNextRotation();
		L21=new Piece(Piece.L2_STR);
		L22=L21.computeNextRotation();
		L23=L22.computeNextRotation();
		L24=L23.computeNextRotation();
		
		
		bAdvanced2=new Board(8, 10);
		S21=new Piece(Piece.S2_STR);
		S11=new Piece(Piece.S1_STR);
		S12=S11.computeNextRotation();
		L11=new Piece(Piece.L1_STR);
		L12=L11.computeNextRotation();
		L13=L12.computeNextRotation();
	}
	
	// Check the basic width/height/max after the one placement
	@Test
	public void testSample1() {
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(2, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
	}
	
	// Place sRotated into the board, then check some measures
	@Test
	public void testSample2() {
		b.commit();
		int result = b.place(sRotated, 1, 1);
		assertEquals(Board.PLACE_OK, result);
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());
	}
	
	
	// tests simple dropheight case and places the second shape, checks methods like getcolumnheight, getmaxheigth and getgrid //
	@Test
	public void testBasic1() {
		b.commit();
		assertEquals(3, b.getWidth());
		assertEquals(6, b.getHeight());
		assertEquals(1, b.dropHeight(pyr2, 1));
		int result=b.place(pyr2, 1, 1);
		assertEquals(Board.PLACE_OK, result);
		assertEquals(3, b.getColumnHeight(1));
		assertEquals(4, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());
		assertEquals(2, b.getRowWidth(1));
		assertEquals(2, b.getRowWidth(2));
		assertEquals(1, b.getRowWidth(3));
		assertFalse(b.getGrid(0, 1));
		assertTrue(b.getGrid(2, 3));
	}
	
	
	
	// checks the place condition place_row_filled, places 3 more shapes and computes the new results//
	@Test
	public void testBasic2(){
		b.commit();
		int result=b.place(pyr3, 0, 4);
		assertEquals(Board.PLACE_ROW_FILLED, result);
		for(int i=0; i<3; i++)
		{
			assertEquals(6, b.getColumnHeight(i));
		}
		assertEquals(0, b.getRowWidth(3));
		assertEquals(0, b.getRowWidth(2));
		assertEquals(6, b.getMaxHeight());
		assertFalse(b.getGrid(2,  4));
		b.commit();
		result=b.place(pyr4, 0, 2);
		
		assertEquals(Board.PLACE_OK, result);
		assertEquals(6, b.getColumnHeight(0));
		assertEquals(1, b.getRowWidth(2));
		assertEquals(2, b.getRowWidth(3));
		assertEquals(2, b.getRowWidth(4));
		assertEquals(6, b.getMaxHeight());
		b.commit();
		result=b.place(pyr2, 1, 1);
		assertEquals(Board.PLACE_ROW_FILLED, result);
		assertFalse(b.getGrid(0,  1));
		assertFalse(b.getGrid(2, 4));
		assertEquals(2, b.getRowWidth(1));
		assertEquals(3, b.getRowWidth(2));
		assertEquals(3, b.getRowWidth(3));
	}
	
	
	
	//  tests for dropheight method, gradually adding some shapes//
	@Test
	public void testBasic3(){
		
		
		b.commit();
		assertEquals(1, b.dropHeight(pyr4, 0));
		assertEquals(2, b.dropHeight(pyr3, 0));
		assertEquals(2, b.dropHeight(pyr4, 1));
		b.place(pyr2, 1, 1);
		assertEquals(3, b.dropHeight(pyr4, 1));
		assertEquals(2, b.dropHeight(pyr4, 0));
		b.commit();
		b.place(pyr4, 0, 2);
		b.commit();
		assertEquals(4, b.dropHeight(pyr3, 0));
		b.place(pyr4,  0,  4);
		
	}
	
	
	// tests clearRows and undo, simple cases //
	@Test
	public void testBasic4(){
		Board before=b;
		String first=b.toString();
		b.commit();
		b.clearRows();
		assertEquals(1, b.getMaxHeight());
		assertTrue(b.getGrid(1, 0));
		assertEquals(1, b.getRowWidth(0));
		assertEquals(0, b.getRowWidth(1));
		assertEquals(0, b.getColumnHeight(0));
		assertEquals(1, b.getColumnHeight(1));
		assertEquals(0, b.getColumnHeight(2));
		b.undo();
		String second=b.toString();
		assertTrue(second.equals(first));
		b.place(pyr3, 0, 2);
		b.clearRows();
		before.undo();
		String third=b.toString();
		assertTrue(third.equals(first));
	}
	
	
	//more advanced test o 5x6 board, tests the case for different types of shapes, mixes a stick, pyramid and L.//
	@Test
	public void testAdvanced1(){
		bAdvanced.place(pyr1, 0, 0);
		bAdvanced.commit();
		bAdvanced.place(stick1, 0, 1);
		bAdvanced.commit();
		String before=bAdvanced.toString();
		bAdvanced.place(L22, 2, 0);
		assertEquals(5, bAdvanced.getColumnHeight(0));
		assertEquals(2, bAdvanced.getColumnHeight(1));
		assertEquals(2, bAdvanced.getColumnHeight(2));
		assertEquals(4, bAdvanced.getRowWidth(0));
		bAdvanced.clearRows();
		assertEquals(1, bAdvanced.getRowWidth(1));
		assertEquals(4, bAdvanced.getColumnHeight(0));
		assertEquals(4, bAdvanced.getMaxHeight());
		bAdvanced.undo();
		String after=bAdvanced.toString();
		assertTrue(before.equals(after));
		
	}
	
	
	// adds 2 L shapes along with a stick, tests the case, where two rows get filled //
	//calls clearRows method and tests commit() and undo() methods //
	@Test
	public void testAdvanced2(){
		bAdvanced.clearRows();
		bAdvanced.place(L24, 0, 0);
		String before1=bAdvanced.toString();
		bAdvanced.commit();
		bAdvanced.place(L22, 1, 0);
		bAdvanced.undo();
		String now1=bAdvanced.toString();
		assertTrue(before1.equals(now1));
		bAdvanced.undo();
		bAdvanced.undo();
		bAdvanced.clearRows();
		assertTrue(before1.equals(now1));
		
		bAdvanced.place(L22, 1, 0);
		String before2=bAdvanced.toString();
		bAdvanced.commit();
		bAdvanced.place(stick1, 4, 0);
		bAdvanced.clearRows();
		assertEquals(2, bAdvanced.getMaxHeight());
		assertEquals(2, bAdvanced.getColumnHeight(4));
		bAdvanced.clearRows();
		bAdvanced.undo();
		String now2=bAdvanced.toString();
		assertTrue(now2.equals(before2));
	}
	
	
	// adds some shapes of different types, checks all the methods //
	@Test
	public void testAdvanced3(){
		bAdvanced2.place(S21, 0, 0);
		bAdvanced2.commit();
		assertEquals(2, bAdvanced2.dropHeight(stick2, 1));
		bAdvanced2.place(stick2, 1, 2);
		bAdvanced2.commit();
		assertEquals(2, bAdvanced2.dropHeight(L23, 0));
		bAdvanced2.place(L23, 0, 0);
		bAdvanced2.commit();
		String before1=bAdvanced2.toString();
		bAdvanced2.clearRows();
		bAdvanced2.undo();
		String after1=bAdvanced2.toString();
		assertTrue(before1.equals(after1));
		assertEquals(2, bAdvanced2.dropHeight(S12, 4));
		bAdvanced2.place(S12, 4, 2);
		bAdvanced2.commit();
		assertEquals(0, bAdvanced2.dropHeight(stick1, 7));
		bAdvanced2.place(stick1, 7, 0);
		bAdvanced2.commit();
		assertEquals(2, bAdvanced2.dropHeight(L13, 5));
		String before2=bAdvanced2.toString();
		bAdvanced2.place(L13, 5, 2);
		bAdvanced2.clearRows();
		bAdvanced2.undo();
		String after2=bAdvanced2.toString();
		
		assertTrue(before2.equals(after2));
		bAdvanced2.place(L13, 5, 2);
		bAdvanced2.commit();
		bAdvanced2.place(L23, 0, 2);
		bAdvanced2.clearRows();
		bAdvanced2.commit();
		
		assertEquals(4, bAdvanced2.getMaxHeight());
		assertEquals(3, bAdvanced2.getColumnHeight(7));
		assertFalse(bAdvanced2.getGrid(3, 0));
		assertEquals(3, bAdvanced2.getRowWidth(0));
	}
	
	
	
}
