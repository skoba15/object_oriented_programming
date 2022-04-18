//
// TetrisGrid encapsulates a tetris board and has
// a clearRows() capability.
package assign1;

import java.util.ArrayList;
import java.util.List;

public class TetrisGrid {
	
	
	private boolean[][] grid;
	
	/**
	 * Constructs a new instance with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public TetrisGrid(boolean[][] grid) {
		this.grid=grid;
	}
	
	
	// clears the specific row //
	private void clearRow(int row){
		for(int i=0; i<grid.length; i++){
			grid[i][row]=false;
		}
	}
	
	// checks whether the row is full //
	private boolean isFull(int row){
		for(int i=0; i<grid.length; i++){
			if(!grid[i][row]){
				return false;
			}
		}
		
		return true;
	}
	
	
	// initializes array, putting -1 at first //
	private void initializeArray(int[] leftRows){
		for(int i=0; i<leftRows.length; i++){
			leftRows[i]=-1;
		}
	}
	
	// fills the destination row with elements from the source row //
	private void fillRow(int destRow, int srcRow){
		for(int i=0; i<grid.length; i++){
			grid[i][destRow]=grid[i][srcRow];
		}
	}
	
	// arranges table after detecting full rows //
	private void arrangeTable(int[] leftRows){
		int curRow=0;
		int curLeftRow=leftRows[curRow];
		for(int i=0; i<grid[0].length; i++){
			if(curLeftRow==-1){
				break;
			}else if(curLeftRow!=i){
				fillRow(i, curLeftRow);
				clearRow(curLeftRow);
				curRow++;
				curLeftRow=leftRows[curRow];
			}else{
				curRow++;
				curLeftRow=leftRows[curRow];
			}
		}
	}
	
	
	/**
	 * Does row-clearing on the grid (see handout).
	 */
	public void clearRows() {
		int[] leftRows=new int[grid[0].length+1];
		initializeArray(leftRows);
		int numOfLeftRows=0;
		for(int i=0; i<grid[0].length; i++){
			if(isFull(i)){
				clearRow(i);
			}
			else{
				leftRows[numOfLeftRows]=i;
				numOfLeftRows++;
			}
		}
		arrangeTable(leftRows);
	}
	
	/**
	 * Returns the internal 2d grid array.
	 * @return 2d grid array
	 */
	boolean[][] getGrid() {
		return grid; 
	}
}
