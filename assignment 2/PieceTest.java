package tetris;

import static org.junit.Assert.*;
import java.util.*;

import org.junit.*;

/*
  Unit test for Piece class -- starter shell.
 */
public class PieceTest {
	// You can create data to be used in the your
	// test cases like this. For each run of a test method,
	// a new PieceTest object is created and setUp() is called
	// automatically by JUnit.
	// For example, the code below sets up some
	// pyramid and s pieces in instance variables
	// that can be used in tests.
	private Piece pyr1, pyr2, pyr3, pyr4, stick1, stick2,  L11, L12, L13, L14, L21, L22, L23, L24, S11, S12, S21, S22, Square;
	private Piece s, sRotated;

	@Before
	public void setUp() throws Exception {
		Piece[] p=Piece.getPieces();
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		stick1=new Piece(Piece.STICK_STR);
		stick2=stick1.computeNextRotation();
		L11=p[Piece.L1];
		L12=L11.computeNextRotation();
		L21=new Piece(Piece.L2_STR);
		L22=L21.computeNextRotation();
		S11=new Piece(Piece.S1_STR);
		S12=S11.computeNextRotation();
		S21=new Piece(Piece.S2_STR);
		S22=S21.computeNextRotation();
		Square=p[Piece.SQUARE];
		L23=L22.computeNextRotation();
		L24=L23.computeNextRotation();
		L13=L12.computeNextRotation();
		L14=L13.computeNextRotation();
		
		
		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();
		
		
	}
	
	// Here are some sample tests to get you started
	
	@Test
	public void testSampleSize() {
		// Check size of pyr piece
		assertEquals(3, pyr1.getWidth());
		assertEquals(2, pyr1.getHeight());
		
		// Now try after rotation
		// Effectively we're testing size and rotation code here
		assertEquals(2, pyr2.getWidth());
		assertEquals(3, pyr2.getHeight());
		
		// Now try with some other piece, made a different way
		Piece l = new Piece(Piece.STICK_STR);
		assertEquals(1, l.getWidth());
		assertEquals(4, l.getHeight());
	}
	
	
	// Test the skirt returned by a few pieces
	@Test
	public void testSampleSkirt() {
		// Note must use assertTrue(Arrays.equals(... as plain .equals does not work
		// right for arrays.
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, sRotated.getSkirt()));
	}
	
	
	@Test
	public void testWidth(){
		// tests the widths of root pieces //
		assertEquals(1, stick1.getWidth());
		assertEquals(2, L11.getWidth());
		assertEquals(2, L21.getWidth());
		assertEquals(3, S11.getWidth());
		assertEquals(3, S21.getWidth());
		assertEquals(2, Square.getWidth());
		assertEquals(3, pyr1.getWidth());
		
		// tests the widths of  rotated pieces //
		assertEquals(4, stick2.getWidth());
		assertEquals(3, L12.getWidth());
		assertEquals(2, L13.getWidth());
		assertEquals(3, L14.getWidth());
		assertEquals(3, L22.getWidth());
		assertEquals(2, L23.getWidth());
		assertEquals(3, L24.getWidth());
		assertEquals(2, S12.getWidth());
		assertEquals(2,	S22.getWidth());
		assertEquals(2,	pyr2.getWidth());
		assertEquals(3,	pyr3.getWidth());
		assertEquals(2,	pyr4.getWidth());
	}
	
	

	@Test
	public void testHeight(){
		// tests the Heights of root pieces //
		assertEquals(4, stick1.getHeight());
		assertEquals(3, L11.getHeight());
		assertEquals(3, L21.getHeight());
		assertEquals(2, S11.getHeight());
		assertEquals(2, S21.getHeight());
		assertEquals(2, Square.getHeight());
		assertEquals(2, pyr1.getHeight());
		
		// tests the Heights of  rotated pieces //
		assertEquals(1, stick2.getHeight());
		assertEquals(2, L12.getHeight());
		assertEquals(3, L13.getHeight());
		assertEquals(2, L14.getHeight());
		assertEquals(2, L22.getHeight());
		assertEquals(3, L23.getHeight());
		assertEquals(2, L24.getHeight());
		assertEquals(3, S12.getHeight());
		assertEquals(3,	S22.getHeight());
		assertEquals(3,	pyr2.getHeight());
		assertEquals(2,	pyr3.getHeight());
		assertEquals(3,	pyr4.getHeight());
	}
	
	
	@Test
	public void testSkirt(){
		//tests skirts of root pieces//
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {0}, stick1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0}, L11.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0}, L21.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0, 1}, S11.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 0}, S21.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0}, Square.getSkirt()));
		
		
		//tests some rotated pieces //
		assertTrue(Arrays.equals(new int[] {1, 0}, pyr2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 1}, pyr4.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0, 0, 0}, stick2.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, L12.getSkirt()));
		assertTrue(Arrays.equals(new int[] {2, 0}, L13.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 1, 1}, L14.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 1, 0}, L22.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 2}, L23.getSkirt()));
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, L24.getSkirt()));
	}
	
	
	
	@Test
	public void testFastRotation(){
		// tests fast rotation method//
		Piece[] p=Piece.getPieces();
		assertTrue(p[Piece.STICK].fastRotation().equals(stick2));
		assertTrue(p[Piece.STICK].fastRotation().fastRotation().equals(p[Piece.STICK]));
		assertTrue(p[Piece.L1].fastRotation().equals(L12));
		assertTrue(p[Piece.L1].fastRotation().fastRotation().equals(L13));
		assertTrue(p[Piece.L1].fastRotation().fastRotation().fastRotation().equals(L14));
		assertTrue(p[Piece.L1].fastRotation().fastRotation().fastRotation().fastRotation().equals(p[Piece.L1]));
		assertTrue(p[Piece.L2].fastRotation().equals(L22));
		assertTrue(p[Piece.L2].fastRotation().fastRotation().equals(L23));
		assertTrue(p[Piece.L2].fastRotation().fastRotation().fastRotation().equals(L24));
		assertTrue(p[Piece.L2].fastRotation().fastRotation().fastRotation().fastRotation().equals(p[Piece.L2]));
		assertTrue(p[Piece.S1].fastRotation().equals(S12));
		assertTrue(p[Piece.S1].fastRotation().fastRotation().equals(p[Piece.S1]));
		assertTrue(p[Piece.S2].fastRotation().equals(S22));
		assertTrue(p[Piece.S2].fastRotation().fastRotation().equals(p[Piece.S2]));
		assertTrue(p[Piece.SQUARE].fastRotation().equals(p[Piece.SQUARE]));
		assertTrue(p[Piece.PYRAMID].fastRotation().equals(pyr2));
		assertTrue(p[Piece.PYRAMID].fastRotation().fastRotation().equals(pyr3));
		assertTrue(p[Piece.PYRAMID].fastRotation().fastRotation().fastRotation().equals(pyr4));
		assertTrue(p[Piece.PYRAMID].fastRotation().fastRotation().fastRotation().fastRotation().equals(p[Piece.PYRAMID]));
	}
	
	
	
	@Test
	public void testEquals(){
		// tests root pieces for equality //
		assertTrue(stick1.equals(new Piece(Piece.STICK_STR)));
		assertTrue(!stick1.equals(new Piece(Piece.STICK_STR+"1")));
		assertTrue(L11.equals(new Piece(L11.getBody())));
		assertTrue(L21.equals(new Piece(Piece.L2_STR)));
		assertTrue(S11.equals(new Piece(Piece.S1_STR)));
		assertTrue(S21.equals(new Piece(Piece.S2_STR)));
		assertTrue(Square.equals(new Piece(Square.getBody())));
		assertTrue(!Square.equals(new Piece(Piece.SQUARE_STR+"0")));
		assertFalse(L24.equals(L23));
		
		
		// tests rotated pieces //
		assertTrue(stick2.equals(new Piece("0 0 1 0 2 0 3 0")));
		assertTrue(L12.equals(new Piece("0 0 1 0 2 0 2 1")));
		assertTrue(L13.equals(new Piece("0 2 1 2 1 1 1 0")));
		assertTrue(L14.equals(new Piece("0 0 0 1 1 1 2 1")));
		assertTrue(S22.equals(new Piece("0 0 0 1 1 1 1 2")));
		assertTrue(Square.equals(new Piece("0 0 0 1 1 1 1 0")));
		assertTrue(pyr2.equals(new Piece("0 1 1 0 1 1 1 2")));
		assertTrue(pyr3.equals(new Piece("0 1 1 0 1 1 2 1")));
		assertTrue(pyr4.equals(new Piece("0 0 0 1 0 2 1 1")));
		
	}
	
	
	
	
	
}
