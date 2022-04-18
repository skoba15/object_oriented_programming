package assign3;

import java.util.*;

/*
 * Encapsulates a Sudoku grid to be solved.
 * CS108 Stanford.
 */
public class Sudoku {
	// Provided grid data for main/testing
	// The instance variable strategy is up to you.
	
	// Provided easy 1 6 grid
	// (can paste this text into the GUI too)
	public static final int[][] easyGrid = Sudoku.stringsToGrid(
	"1 6 4 0 0 0 0 0 2",
	"2 0 0 4 0 3 9 1 0",
	"0 0 5 0 8 0 4 0 7",
	"0 9 0 0 0 6 5 0 0",
	"5 0 0 1 0 2 0 0 8",
	"0 0 8 9 0 0 0 3 0",
	"8 0 9 0 4 0 2 0 0",
	"0 7 3 5 0 9 0 0 1",
	"4 0 0 0 0 0 6 7 9");
	
	
	// Provided medium 5 3 grid
	public static final int[][] mediumGrid = Sudoku.stringsToGrid(
	 "530070000",
	 "600195000",
	 "098000060",
	 "800060003",
	 "400803001",
	 "700020006",
	 "060000280",
	 "000419005",
	 "000080079");
	
	// Provided hard 3 7 grid
	// 1 solution this way, 6 solutions if the 7 is changed to 0
	public static final int[][] hardGrid = Sudoku.stringsToGrid(
	"3 7 0 0 0 0 0 8 0",
	"0 0 1 0 9 3 0 0 0",
	"0 4 0 7 8 0 0 0 3",
	"0 9 3 8 0 0 0 1 2",
	"0 0 0 0 4 0 0 0 0",
	"5 2 0 0 0 6 7 9 0",
	"6 0 0 0 2 1 0 4 0",
	"0 0 0 5 3 0 9 0 0",
	"0 3 0 0 0 0 0 5 1");
	
	
	public static final int SIZE = 9;  // size of the whole 9x9 puzzle
	public static final int PART = 3;  // size of each 3x3 part
	public static final int MAX_SOLUTIONS = 100;
	
	// Provided various static utility methods to
	// convert data formats to int[][] grid.
	
	/**
	 * Returns a 2-d grid parsed from strings, one string per row.
	 * The "..." is a Java 5 feature that essentially
	 * makes "rows" a String[] array.
	 * (provided utility)
	 * @param rows array of row strings
	 * @return grid
	 */
	public static int[][] stringsToGrid(String... rows) {
		int[][] result = new int[rows.length][];
		for (int row = 0; row<rows.length; row++) {
			result[row] = stringToInts(rows[row]);
		}
		return result;
	}
	
	
	/**
	 * Given a single string containing 81 numbers, returns a 9x9 grid.
	 * Skips all the non-numbers in the text.
	 * (provided utility)
	 * @param text string of 81 numbers
	 * @return grid
	 */
	public static int[][] textToGrid(String text) {
		int[] nums = stringToInts(text);
		if (nums.length != SIZE*SIZE) {
			throw new RuntimeException("Needed 81 numbers, but got:" + nums.length);
		}
		
		int[][] result = new int[SIZE][SIZE];
		int count = 0;
		for (int row = 0; row<SIZE; row++) {
			for (int col=0; col<SIZE; col++) {
				result[row][col] = nums[count];
				count++;
			}
		}
		return result;
	}
	
	
	/**
	 * Given a string containing digits, like "1 23 4",
	 * returns an int[] of those digits {1 2 3 4}.
	 * (provided utility)
	 * @param string string containing ints
	 * @return array of ints
	 */
	public static int[] stringToInts(String string) {
		int[] a = new int[string.length()];
		int found = 0;
		for (int i=0; i<string.length(); i++) {
			if (Character.isDigit(string.charAt(i))) {
				a[found] = Integer.parseInt(string.substring(i, i+1));
				found++;
			}
		}
		int[] result = new int[found];
		System.arraycopy(a, 0, result, 0, found);
		return result;
	}


	// Provided -- the deliverable main().
	// You can edit to do easier cases, but turn in
	// solving hardGrid.
	public static void main(String[] args) {
		Sudoku sudoku;
		sudoku = new Sudoku(hardGrid);
		
		System.out.println(sudoku); // print the raw problem
		int count = sudoku.solve();
		System.out.println("solutions:" + count);
		System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
		System.out.println(sudoku.getSolutionText());
	}
	
	
   /* private variables for this class */
	private int[][] grid;
	private ArrayList<HashSet<Integer> > columns;
	private ArrayList<HashSet<Integer> > rows;
	private ArrayList<HashSet<Integer> > smallSquares;
	private ArrayList<Spot> spots;
	private long starttime;
	private long endtime;
	private String solutionText="";
	
	
	/* updates the hashset of the given 3x3 small square, adding the number with value "value" */
	private void updateSmallSquare(int row, int column, int value){
		int squareNumber=getSquareNumber(row, column);
		smallSquares.get(squareNumber).add(value);
	}
	
	/* updates the hashset of the given row, adding the number with value "value" */
	private void updateRows(int row, int value){
		rows.get(row).add(value);
	}
	
	/* updates the hashset of the given column, adding the number with value "value" */
	private void updateColumns(int column, int value){
		columns.get(column).add(value);
	}
	
	
	/* adds a blank spot  on the given row and column in the arraylist */
	private void addBlankSpot(int row, int column){
		Spot spot=new Spot(row, column);
		spots.add(spot);
	}
	
	/* initializes grid based on the initial table, updtates the hashsets for rows columns and smallsquares and finds blank spots */
	private void initializeGrid(int[][] ints){
		grid=new int[SIZE][SIZE];
		for(int i=0; i<SIZE; i++){
			for(int j=0; j<SIZE; j++){
				grid[i][j]=ints[i][j];
				int curValue=grid[i][j];
				if(curValue!=0){
					updateSmallSquare(i, j, curValue);
					updateRows(i, curValue);
					updateColumns(j, curValue);
				}
				else{
					addBlankSpot(i, j);
				}
			}
		}
	}
	
	
	
	/* creates hashsets for all the rows */
	private void initializeRows(){
		rows=new ArrayList<HashSet<Integer>>();
		for(int i=0; i<SIZE; i++){
			HashSet<Integer> curHashSet=new HashSet<Integer>();
			rows.add(curHashSet);
		}
	}
	
	/* creates hashsets for all the columns */
	private void initializeColumns(){
		columns=new ArrayList<HashSet<Integer>>();
		for(int i=0; i<SIZE; i++){
			HashSet<Integer> curHashSet=new HashSet<Integer>();
			columns.add(curHashSet);
		}
	}
	
	
	/* creates hashsets for all the small squares */
	private void initializeSmallSquares(){
		smallSquares=new ArrayList<HashSet<Integer>>();
		for(int i=0; i<SIZE; i++){
			HashSet<Integer> curHashSet=new HashSet<Integer>();
			smallSquares.add(curHashSet);
		}
	}
	
	
	/* initializes arraylist for blankspots */
	private void initializeSpots(){
		spots=new ArrayList<Spot>();
	}
	
	/* returns the number of the small square, numbering begins from the top leftmost small square */
	private int getSquareNumber(int row, int column){
		int squareRow=row/3;
		int squareColumn=column/3;
		return squareRow*SIZE/3+squareColumn;
	}
	
	
	
	
	
	/* checks all the blank spots and adds available numbers that can be put in these spots */
	private void updateSpots(){
		int spotsSize=spots.size();
		for(int i=0; i<spotsSize; i++){
			Spot curspot=spots.get(i);
			for(int number=1; number<=9; number++){
				if(curspot.canTakeValue(number)){
					curspot.add(number);
				}
			}
		}
	}
	
	
	/**
	 * Sets up based on the given ints.
	 */
	public Sudoku(int[][] ints) {
		starttime=System.currentTimeMillis();
		initializeRows();
		initializeColumns();
		initializeSmallSquares();
		initializeSpots();
		initializeGrid(ints);
		updateSpots();
		Collections.sort(spots, new SpotComparator());
	}
	
	
	/* second constructor which takes the table as a string */
	public Sudoku(String s){
		this(textToGrid(s));
	}
	
	

	
	/* keeps the first solution of the grid */
	private void setSolutionText(){
		solutionText=this.toString();
	}
	
	
	
	/* helper method which recursilvely finds solutions of the given Sudoku table and returns the number of solutions */
	public int solve_helper(int curInd){
		if(curInd==spots.size()){
			if(solutionText.equals("")){
				setSolutionText();
			}
			return 1;
		}
		else{
			Spot curSpot=spots.get(curInd);
			int solutions=0;
			for(int i=1; i<=9; i++){
				if(curSpot.canTakeValue(i)){
					curSpot.setValue(i);
					solutions+=solve_helper(curInd+1);
					curSpot.eraseValue();
					if(solutions>=MAX_SOLUTIONS){
						return solutions;
					}
				}
			}
			return solutions;
		}
	}
	
	
	/**
	 * Solves the puzzle, invoking the underlying recursive search.
	 */
	
	public int solve() {
		int curInd=0;
		int result=solve_helper(curInd);
		return result;
	}
	
	
	
	/* returns the first solution of the sudoku table */
	public String getSolutionText() {
		return solutionText; 
	}
	
	
	/* returns time in milliseconds spent in finding all the solutions */
	public long getElapsed() {
		endtime=System.currentTimeMillis();
		return endtime-starttime; 
	}
	
	/* toString method of the class */
	@Override
	public String toString(){
		StringBuilder sb=new StringBuilder();
		for(int i=0; i<SIZE; i++){
			for(int j=0; j<SIZE; j++){
				sb.append(grid[i][j]+" ");
			}
			if(i!=SIZE-1)sb.append("\n");
		}
		return sb.toString();
	}
	
	
	private class Spot{
		private final int column;
		private final int row;
		private List<Integer> availableNumbers;
		
		
		/* constructor for the spot class */
		public Spot(int row, int column){
			this.column=column;
			this.row=row;
			availableNumbers=new ArrayList<Integer>();
		}
		
		
		/* adds the value to the list of available numbers for this spot */
		public void add(int value){
			availableNumbers.add(value);
		}
		
		
		/* returns the number of choices of available numbers for this spot */
		public int numberOfChoices(){
			return availableNumbers.size();
		}
		
		
		/* sets the value of the spot */
		public void setValue(int value){
			grid[row][column]=value;
			rows.get(row).add(value);
			columns.get(column).add(value);
			smallSquares.get(getSquareNumber(row, column)).add(value);
		}
		
		
		/* erases the value of the spot */
		public void eraseValue(){
			int value=grid[row][column];
			rows.get(row).remove(value);
			columns.get(column).remove(value);
			smallSquares.get(getSquareNumber(row, column)).remove(value);
			grid[row][column]=0;
		}
		
		
		/* returns true if this spot can be filled with "number" */
		public boolean canTakeValue(int number){
			int squareNum=getSquareNumber(row, column);
			return (!rows.get(row).contains(number) && !columns.get(column).contains(number) && !smallSquares.get(squareNum).contains(number));
		}
		
		
	}
	
	/* comparator class for comparing spot objects */
	class SpotComparator implements Comparator<Spot>{
		@Override
		public int compare(Spot o1, Spot o2) {
			return o1.numberOfChoices()-o2.numberOfChoices();
		}
	}

}
