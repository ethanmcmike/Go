package ethanmcmike.go.models;

import java.util.Arrays;

public class Board {
	private int size;
	private char newStone;
	
    private Intersection[][] board;
    private char[][] old1 = null, old2;
	
	private int[] captures;
	private int[] oldCap1, oldCap2;
	
	public Board(int size) {
		this.size = size;
		this.captures = new int[26];
		board = new Intersection[size][size];
		for(int r = 0; r < size; r++)
			for(int c = 0; c < size; c++)
				board[r][c] = new Intersection();
		old2 = toArray();
		oldCap2 = new int[26];
	}
	
	public void print() {
		System.out.println("");
		for(Intersection[] row : board) {
			for(Intersection corner : row)
				System.out.print(" " + corner.getChar());
			System.out.println("");
		}
		for (int i = 0; i < size; i++)
			System.out.print("==");
		System.out.print("=");
	}
	
	public boolean set(int row, int col, char color) {
		if(!board[row][col].set(color)) return false;
		newStone = color;
		
		if(!check(row-1, col))	//North
			clear(row-1, col);
		reset();
		if(!check(row, col+1))	//East
			clear(row, col+1);
		reset();
		if(!check(row+1, col))	//South
			clear(row+1, col);
		reset();
		if(!check(row, col-1))	//West
			clear(row, col-1);
		reset();
		
		if(!check(row, col, color))
			clear(row, col);
		reset();
		
		char[][] temp = toArray();
		if(Arrays.deepEquals(temp, old2)) {	//If(Ko) undo and return false
			fromArray(old1);
			captures = oldCap1.clone();
			return false;
		} else {							//else save previous configs to test for Ko
			if(old1 != null) {
				old2 = old1.clone();
				oldCap2 = oldCap1.clone();
			}
			old1 = temp.clone();
			oldCap1 = captures.clone();
			return true;
		}
	}
	
	/**
	 * For standard recursive checking
	 * 
	 * Checks if the given location is live or dead
	 * Returns dead if the space is not @color
	 */
	private boolean check(int row, int col, char color) {
		if(row<0 || row>=size || col<0 || col>=size) return false;	//Dead if off the edge
		char c = board[row][col].check();
		if(c == '+') return true;									//Live if empty
		else if(c == color)											//Check surrounding spaces if the same color
			return check(row-1, col  , color) || check(row  , col+1, color) || check(row+1, col  , color) || check(row  , col-1, color);
		else return false;											//Dead if already checked or other color
	}
	
	/**
	 * For the first check in each direction when adding a new stone
	 * Ignores stones of the same color as the new one
	 * 
	 * Otherwise is the same as check(int, int, char)
	 */
	private boolean check(int row, int col) {
		if(row<0 || row>=size || col<0 || col>=size) return true;	//Don't care if off the edge
		char color = board[row][col].check();
		if(color == '+' || newStone == color) return true;			//Don't care if empty or same color
		return check(row-1, col  , color) || check(row  , col+1, color) || check(row+1, col  , color) || check(row  , col-1, color);
	}
	
	private void reset() {
		for(Intersection[] rows : board)
			for(Intersection corner : rows)
				corner.reset();
	}
	
	private void clear(int row, int col, char color) {
		if(row<0 || row>=size || col<0 || col>=size) return;
		
		if(board[row][col].getColor() == color) {
			board[row][col].clear();
			captures[color - 0x41]++;
			
			clear(row+1, col, color);
			clear(row, col+1, color);
			clear(row-1, col, color);
			clear(row, col-1, color);
		}
	}
	private void clear(int row, int col) {
		if(row<0 || row>=size || col<0 || col>=size) return;
		
		char color = board[row][col].getColor();
		
		board[row][col].clear();
		captures[color - 0x41]++;
		
		clear(row+1, col, color);
		clear(row, col+1, color);
		clear(row-1, col, color);
		clear(row, col-1, color);
	}
	
	public int getSize() {
		return size;
	}
	
	public char[][] toArray() {
		char[][] temp = new char[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				temp[i][j] = board[i][j].getChar();
		return temp;
	}
	
	private void fromArray(char[][] charArray) {
		Intersection[][] temp = new Intersection[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				temp[i][j] = new Intersection();
				temp[i][j].set(charArray[i][j]);
			}
		board = temp;
	}
	
	public boolean undo() {
		if(old1 == null) return false;
		
		fromArray(old2);
		captures = oldCap2.clone();
		
		old1 = null;
		return true;
	}
	
	public int getCaptures(char color) {
		return captures[color - 0x41];
	}
}
