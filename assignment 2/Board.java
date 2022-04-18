// Board.java
package tetris;

import java.util.Arrays;

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
*/
public class Board	{
	// Some ivars are stubbed out for you:
	private int width;
	private int height;
	private boolean[][] grid;
	private boolean DEBUG = true;
	boolean committed;
	private int[] widths;
	private int[] heights;
	private int maxHeight;
	private boolean[][] xGrid;
	private int xWidth;
	private int xHeight;
	private int[] xWidths;
	private int[] xHeights;
	private int xMaxHeight;
	
	
	
	
	// initiliazes widths array //
	private void InitializeWidths(){
		widths=new int[height];
		for(int i=0; i<height; i++){
			widths[i]=0;
		}
	}
	
	// initializes heights array //
	private void InitializeHeights(){
		heights=new int[width];
		for(int i=0; i<width; i++){
			heights[i]=0;
		}
	}
	
	
	
	
	
	// initializes backup the board //
	private void InitializeBackup(){
		xWidth=width;
		xHeight=height;
		xWidths=new int[xHeight];
		xHeights=new int[xWidth];
		xGrid=new boolean[xWidth][xHeight];
		for(int i=0; i<height; i++){
			xWidths[i]=widths[i];
		}
		for(int i=0; i<width; i++){
			xHeights[i]=heights[i];
		}
		xMaxHeight=maxHeight;
	}
	
	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	*/
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new boolean[width][height];
		committed = true;
		maxHeight=0;
		InitializeWidths();
		InitializeHeights();
		InitializeBackup();
		
	}
	
	
	/**
	 Returns the width of the board in blocks.
	*/
	public int getWidth() {
		return width;
	}
	
	
	/**
	 Returns the height of the board in blocks.
	*/
	public int getHeight() {
		return height;
	}
	
	
	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	*/
	public int getMaxHeight() {	 
		return maxHeight; 
	}
	
	
	
	
	
	
	

	// checks the board consistency, compares maxheights, widths and heights//
	
	private void checkBoard(int[] newRows, int[] newCols){
		int newMaxheight=0;
		for(int i=0; i<width; i++){
			
			for(int j=0; j<height; j++){
				if(grid[i][j]){
					newRows[j]++;
					newCols[i]=j+1;
				}
			}
			
			newMaxheight=Math.max(newMaxheight, newCols[i]);
		}
		if(maxHeight!=newMaxheight || !Arrays.equals(widths, newRows) || !Arrays.equals(heights, newCols))throw new RuntimeException("description");
	}
	
	
	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	*/
	public void sanityCheck() {
		if (DEBUG) {
			int[] newRows=new int[height];
			int[] newCols=new int[width];
			 checkBoard(newRows, newCols);
		}
	}
	
	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.
	 
	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	*/
	public int dropHeight(Piece piece, int x) {
		int[] skirt=piece.getSkirt();
		int skirtInd=-1;
		int maxSum=-5;
		for(int i=0; i<skirt.length; i++){
			if(heights[x+i]-skirt[i]>maxSum){
				maxSum=heights[x+i]-skirt[i];
				skirtInd=i;
			}
		}
		
		return heights[skirtInd+x]-skirt[skirtInd]; 
	}
	
	
	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	*/
	public int getColumnHeight(int x) {
		
		return heights[x]; 
	}
	
	
	/**
	 Returns the number of filled blocks in
	 the given row.
	*/
	public int getRowWidth(int y) {
		 return widths[y];
	}
	
	
	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	*/
	public boolean getGrid(int x, int y) {
		if(x>=width || y>=height)return true;
		else return grid[x][y]; 
	}
	
	
	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;
	
	
	
	
	
	// checks whether the piece with given height and width can be placed on the given coordinates //
	private boolean isNotOutOfBounds(int x, int y, int w, int h){
		return (((x>=0 && x<=width-1) && (y>=0 && y<=height-1)) && (x+w<=width && y+h<=height));
	}
	
	
	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.
	 
	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	*/
	public int place(Piece piece, int x, int y) {
		// flag !committed problem
		if (!committed) throw new RuntimeException("place commit problem");
		
		copyToBackup();
		int result = PLACE_OK;
		
		if(!isNotOutOfBounds(x, y, piece.getWidth(), piece.getHeight())){
			result=PLACE_OUT_BOUNDS;
		}
		else{
			TPoint[] pointsOfPiece=piece.getBody();
			int size=pointsOfPiece.length;
			boolean rowFilled=false;
			for(int i=0; i<size; i++){
				TPoint current=pointsOfPiece[i];
				int Xcoord=current.x+x;
				int Ycoord=current.y+y;
				if(grid[Xcoord][Ycoord]){
					return PLACE_BAD;
				}
				heights[Xcoord]=Math.max(heights[Xcoord], Ycoord+1);
				widths[Ycoord]++;
				if(widths[Ycoord]==this.width){
					rowFilled=true;
				}
				grid[Xcoord][Ycoord]=true;
				maxHeight=Math.max(maxHeight, Ycoord+1);
			}
			if(rowFilled)result=PLACE_ROW_FILLED;
		}
		committed=false;
		sanityCheck();
		return result;
	}
	
	
	
	
	// finds the first filled row above the given index //
	private int findFilledRow(int from){
		for(int i=from; i<maxHeight; i++){
			if(widths[i]==width){
				return i;
			}
		}
		return -1;
	}
	
	// finds the first row which is not filled completely and returns its index //
	private int findNonFilledRow(int from){
		for(int i=from; i<maxHeight; i++){
			if(widths[i]!=width){
				return i;
			}
		}
		return -1;
	}
	
	// copies the row with index from to the row with an index to //
	private void makeCopy(int to, int from){
		for(int i=0; i<width; i++){
			grid[i][to]=grid[i][from];
		}
		widths[to]=widths[from];
	}
	
	// counts the number of filled rows on the board //
	public int countFilledRows(){
		int result=0;
		for(int i=0; i<height; i++){
			if(widths[i]==width){
				result++;
			}
		}
		return result;
	}
	
	
	// computes the heights of all the columns //
	private void updateHeights(){
		maxHeight=0;
		for(int i=0; i<width; i++){
			heights[i]=0;
			
			for(int j=0; j<height; j++){
				if(grid[i][j]){
					heights[i]=j+1;
					
				}
			}
		}
		for(int i=0; i<width; i++){
			maxHeight=Math.max(maxHeight, heights[i]);
		}
	}
	
	
	// arranges the table after clearRows operation and updates the information //
	private void arrangeTable(int TO, int rowsCleared){
		
		for(int i=TO; i<maxHeight; i++){
			widths[i]=0;
			for(int j=0; j<width; j++){
				grid[j][i]=false;
			}
		}
		updateHeights();
	}
	
	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	*/
	public int clearRows() {
		if(committed){
			copyToBackup();
		}
		int rowsCleared=countFilledRows();
		int TO=findFilledRow(0);
		if(TO==-1){
			return 0;
		}
		int FROM=findNonFilledRow(TO+1);
		while(true){
			
			if(FROM>=maxHeight || FROM==-1){
				break;
			}
			makeCopy(TO, FROM);
			TO++;
			FROM=findNonFilledRow(FROM+1);
		}
		arrangeTable(TO, rowsCleared);
		committed=false;
		sanityCheck();
		return rowsCleared;
	}



	/**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	*/
	public void undo() {
		for(int i=0; i<width; i++){
			for(int j=0; j<height; j++){
				grid[i][j]=xGrid[i][j];
			}
		}
		width=xWidth;
		height=xHeight;
		maxHeight=xMaxHeight;
		for(int i=0; i<height; i++){
			widths[i]=xWidths[i];
		}
		for(int i=0; i<width; i++){
			heights[i]=xHeights[i];
		}
		committed=true;
	}
	
	
	/**
	 Puts the board in the committed state.
	*/
	public void commit() {
		committed = true;
	}
	
	// copies the information of the original board to the backup board //
	private void copyToBackup(){
		for(int i=0; i<xWidth; i++){
			for(int j=0; j<xHeight; j++){
				xGrid[i][j]=grid[i][j];
			}
		}
		xWidth=width;
		xHeight=height;
		xMaxHeight=maxHeight;
		for(int i=0; i<height; i++){
			xWidths[i]=widths[i];
		}
		for(int i=0; i<width; i++){
			xHeights[i]=heights[i];
		}
	}


	
	/*
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility) 
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}
}


