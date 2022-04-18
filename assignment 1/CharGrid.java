// HW1 2-d array Problems
// CharGrid encapsulates a 2-d grid of chars and supports
// a few operations on the grid.

package assign1;

public class CharGrid {
	private char[][] grid;

	/**
	 * Constructs a new CharGrid with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public CharGrid(char[][] grid) {
		this.grid = grid;
	}
	
	/**
	 * Returns the area for the given char in the grid. (see handout).
	 * @param ch char to look for
	 * @return area for given char
	 */
	public int charArea(char ch) {
		int mostLeft=grid[0].length;
		int mostRight=0;
		int mostDown=0;
		int mostUp=grid.length;
		for(int i=0; i<grid.length; i++){
			for(int j=0; j<grid[i].length; j++){
				char currentChar=grid[i][j];
				if(currentChar==ch){
					mostLeft=Math.min(mostLeft, j);
					mostRight=Math.max(mostRight, j);
					mostUp=Math.min(mostUp, i);
					mostDown=Math.max(mostDown, i);
				}
			}
		}
		int area=(mostDown-mostUp+1)*(mostRight-mostLeft+1);
		return area;
	}
	
	/**
	 * Returns the count of '+' figures in the grid (see handout).
	 * @return number of + in grid
	 */
	
	
	private boolean isCross(int x, int y){
		char centerChar=grid[x][y];
		int leftLength=0;
		int rightLength=0;
		int upLength=0;
		int downLength=0;
		
		for(int i=y; i>=0; i--){
			char curChar=grid[x][i];
			if(curChar!=centerChar){
				break;
			}
			else{
				leftLength++;
			}
		}
		
		
		for(int i=y; i<grid[x].length; i++){
			char curChar=grid[x][i];
			if(curChar!=centerChar){
				break;
			}
			else{
				rightLength++;
			}
		}
		
		
		for(int i=x; i<grid.length; i++){
			char curChar=grid[i][y];
			if(curChar!=centerChar){
				break;
			}
			else{
				downLength++;
			}
		}
		
		
		for(int i=x; i>=0; i--){
			char curChar=grid[i][y];
			if(curChar!=centerChar){
				break;
			}
			else{
				upLength++;
			}
		}
		
		
		if(leftLength==rightLength && rightLength==upLength && upLength==downLength && upLength>=2){
			return true;
		}
		return false;
	}
	
	
	public int countPlus() {
		int countCrosses=0;
		for(int i=0; i<grid.length; i++){
			for(int j=0; j<grid[i].length; j++){
				if(isCross(i, j)){
					countCrosses++;
				}
			}
		}
		return countCrosses; // TODO ADD YOUR CODE HERE
	}
	
}
