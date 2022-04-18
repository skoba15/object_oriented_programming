// Piece.java
package tetris;

import java.util.*;

/**
 An immutable representation of a tetris piece in a particular rotation.
 Each piece is defined by the blocks that make up its body.
 
 Typical client code looks like...
 <pre>
 Piece pyra = new Piece(PYRAMID_STR);		// Create piece from string
 int width = pyra.getWidth();			// 3
 Piece pyra2 = pyramid.computeNextRotation(); // get rotation, slow way
 
 Piece[] pieces = Piece.getPieces();	// the array of root pieces
 Piece stick = pieces[STICK];
 int width = stick.getWidth();		// get its width
 Piece stick2 = stick.fastRotation();	// get the next rotation, fast way
 </pre>
*/
public class Piece {
	// Starter code specs out a few basic things, leaving
	// the algorithms to be done.
	private TPoint[] body;
	private int[] skirt;
	private int width;
	private int height;
	private Piece next; // "next" rotation

	static private Piece[] pieces;	// singleton static array of first rotations

	
	// fills the body array//
	private void getBody(TPoint[] points){
		int size=points.length;
		body=new TPoint[size];
		for(int i=0; i<size; i++){
			TPoint current=points[i];
			body[i]=new TPoint(current.x, current.y);
		}
	}
	
	// counts skirts for all the columns //
	private void countSkirt(TPoint[] points){
		int size=points.length;
		HashMap<Integer, Integer> minimalOnX=new HashMap<Integer, Integer>();
		for(int i=0; i<size; i++){
			TPoint current=points[i];
			if(minimalOnX.containsKey(current.x)){
				int curValue=minimalOnX.get(current.x);
				int minimum=Math.min(curValue, current.y);
				minimalOnX.put(current.x, minimum);
			}
			else{
				minimalOnX.put(current.x, current.y);
			}
		}
		int width=this.getWidth();
		skirt=new int[width];
		for(int i=0; i<width; i++){
			skirt[i]=minimalOnX.get(i);
		}
	}
	
	// counts width of the piece, given the points of its parts //
	private void countWidth(TPoint[] points){
		int minIndex=0;
		int maxIndex=0;
		int size=points.length;
		for(int i=0; i<size; i++){
			TPoint current=points[i];
			minIndex=Math.min(minIndex, current.x);
			maxIndex=Math.max(maxIndex, current.x);
		}
		width=maxIndex-minIndex+1;
	}
	
	// counts the height of a piece//
	private void countHeight(TPoint[] points){
		int minHeight=0;
		int maxHeight=0;
		int size=points.length;
		for(int i=0; i<size; i++){
			TPoint current=points[i];
			minHeight=Math.min(minHeight, current.y);
			maxHeight=Math.max(maxHeight, current.y);
		}
		height=maxHeight-minHeight+1;
	}
	
	
	
	
	/**
	 Defines a new piece given a TPoint[] array of its body.
	 Makes its own copy of the array and the TPoints inside it.
	*/
	public Piece(TPoint[] points) {
		getBody(points);
		countWidth(points);
		countHeight(points);
		countSkirt(points);
		
	}
	

	
	
	/**
	 * Alternate constructor, takes a String with the x,y body points
	 * all separated by spaces, such as "0 0  1 0  2 0	1 1".
	 * (provided)
	 */
	public Piece(String points) {
		this(parsePoints(points));
	}

	/**
	 Returns the width of the piece measured in blocks.
	*/
	public int getWidth() {
		return width;
	}

	/**
	 Returns the height of the piece measured in blocks.
	*/
	public int getHeight() {
		return height;
	}

	/**
	 Returns a pointer to the piece's body. The caller
	 should not modify this array.
	*/
	public TPoint[] getBody() {
		int size=body.length;
		TPoint[] result=new TPoint[size];
		for(int i=0; i<size; i++){
			TPoint toCopy=body[i];
			result[i]=new TPoint(toCopy.x, toCopy.y);
		}
		return result;
	}

	/**
	 Returns a pointer to the piece's skirt. For each x value
	 across the piece, the skirt gives the lowest y value in the body.
	 This is useful for computing where the piece will land.
	 The caller should not modify this array.
	*/
	public int[] getSkirt() {
		int size=skirt.length;
		int[] result=new int[size];
		for(int i=0; i<size; i++){
			result[i]=skirt[i];
		}
		return result;
	}

	
	/**
	 Returns a new piece that is 90 degrees counter-clockwise
	 rotated from the receiver.
	 */
	public Piece computeNextRotation() {
		TPoint[] resultPoints=new TPoint[body.length];
		int maxHeightInd=this.getHeight()-1;
		int maxWidthInd=this.getWidth()-1;
		for(int i=0; i<body.length; i++){
			TPoint current=body[i];
			resultPoints[i]=new TPoint(maxHeightInd-current.y, current.x);
		}
		Piece resultPiece=new Piece(resultPoints);
		return resultPiece; 
	}

	/**
	 Returns a pre-computed piece that is 90 degrees counter-clockwise
	 rotated from the receiver.	 Fast because the piece is pre-computed.
	 This only works on pieces set up by makeFastRotations(), and otherwise
	 just returns null.
	*/	
	public Piece fastRotation() {
		return next;
	}
	


	/**
	 Returns true if two pieces are the same --
	 their bodies contain the same points.
	 Interestingly, this is not the same as having exactly the
	 same body arrays, since the points may not be
	 in the same order in the bodies. Used internally to detect
	 if two rotations are effectively the same.
	*/
	public boolean equals(Object obj) {
		// standard equals() technique 1
		if (obj == this) return true;
		
		// standard equals() technique 2
		// (null will be false)
		if (!(obj instanceof Piece)) return false;
		Piece other = (Piece)obj;
		
		
		for(int i=0; i<other.body.length; i++){
			TPoint otherCurPoint=other.body[i];
			boolean isHere=false;
			for(int j=0; j<this.body.length; j++){
				TPoint curPoint=this.body[j];
				if(curPoint.equals(otherCurPoint)){
					isHere=true;
				}
			}
			if(!isHere){
				return false;
			}
		}
		return true;
	}


	// String constants for the standard 7 tetris pieces
	public static final String STICK_STR	= "0 0	0 1	 0 2  0 3";
	public static final String L1_STR		= "0 0	0 1	 0 2  1 0";
	public static final String L2_STR		= "0 0	1 0 1 1	 1 2";
	public static final String S1_STR		= "0 0	1 0	 1 1  2 1";
	public static final String S2_STR		= "0 1	1 1  1 0  2 0";
	public static final String SQUARE_STR	= "0 0  0 1  1 0  1 1";
	public static final String PYRAMID_STR	= "0 0  1 0  1 1  2 0";
	
	// Indexes for the standard 7 pieces in the pieces array
	public static final int STICK = 0;
	public static final int L1	  = 1;
	public static final int L2	  = 2;
	public static final int S1	  = 3;
	public static final int S2	  = 4;
	public static final int SQUARE	= 5;
	public static final int PYRAMID = 6;
	
	/**
	 Returns an array containing the first rotation of
	 each of the 7 standard tetris pieces in the order
	 STICK, L1, L2, S1, S2, SQUARE, PYRAMID.
	 The next (counterclockwise) rotation can be obtained
	 from each piece with the {@link #fastRotation()} message.
	 In this way, the client can iterate through all the rotations
	 until eventually getting back to the first rotation.
	 (provided code)
	*/
	public static Piece[] getPieces() {
		// lazy evaluation -- create static array if needed
		if (Piece.pieces==null) {
			// use makeFastRotations() to compute all the rotations for each piece
			Piece.pieces = new Piece[] {
				makeFastRotations(new Piece(STICK_STR)),
				makeFastRotations(new Piece(L1_STR)),
				makeFastRotations(new Piece(L2_STR)),
				makeFastRotations(new Piece(S1_STR)),
				makeFastRotations(new Piece(S2_STR)),
				makeFastRotations(new Piece(SQUARE_STR)),
				makeFastRotations(new Piece(PYRAMID_STR)),
			};
		}
		
		
		return Piece.pieces;
	}
	


	/**
	 Given the "first" root rotation of a piece, computes all
	 the other rotations and links them all together
	 in a circular list. The list loops back to the root as soon
	 as possible. Returns the root piece. fastRotation() relies on the
	 pointer structure setup here.
	*/
	/*
	 Implementation: uses computeNextRotation()
	 and Piece.equals() to detect when the rotations have gotten us back
	 to the first piece.
	*/
	private static Piece makeFastRotations(Piece root) {
		
		Piece curPiece=root;
		while(true){
			Piece savePiece=curPiece;
			curPiece=curPiece.computeNextRotation();
			if(curPiece.equals(root)){
				savePiece.next=root;
				break;
			}
			savePiece.next=curPiece;
		}
		return root; 
	}
	
	

	/**
	 Given a string of x,y pairs ("0 0	0 1 0 2 1 0"), parses
	 the points into a TPoint[] array.
	 (Provided code)
	*/
	private static TPoint[] parsePoints(String string) {
		List<TPoint> points = new ArrayList<TPoint>();
		StringTokenizer tok = new StringTokenizer(string);
		try {
			while(tok.hasMoreTokens()) {
				int x = Integer.parseInt(tok.nextToken());
				int y = Integer.parseInt(tok.nextToken());
				
				points.add(new TPoint(x, y));
			}
		}
		catch (NumberFormatException e) {
			throw new RuntimeException("Could not parse x,y string:" + string);
		}
		
		// Make an array out of the collection
		TPoint[] array = points.toArray(new TPoint[0]);
		return array;
	}

	


}
