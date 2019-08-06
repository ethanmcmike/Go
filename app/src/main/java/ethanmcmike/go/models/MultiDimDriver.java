package ethanmcmike.go.models;

import java.util.ArrayList;

/**
 * @author Jacob Kelsey
 */
public class MultiDimDriver {
    final int size, dimension;
	public MultiDimBoard board;
	
	private char currPlayer;
	private int turnNum;
	private int[] territory, area, prisoners;
	private ArrayList<char[]> moves;
	private ArrayList<int[]> captures;
	
	public MultiDimDriver() {
		this(19, 2);
	}
	public MultiDimDriver(int size) {
		this(size, 2);
	}
	public MultiDimDriver(int size, int dimensions) {
		this.size = size;
		this.dimension = dimensions;
		board = new MultiDimBoard(dimensions, size);
		
		turnNum = 0;
		territory = new int[26];
		area = new int[26];
		prisoners = new int[26];
		moves = new ArrayList();
		captures = new ArrayList();
	}
	
	/**
	 * Place a new stone
	 * @param loc Location to place new stone
	 * @param color Color of new stone
	 * @return True if successful, false if invalid move
	 */
	boolean place(int[] loc, char color) {
		currPlayer = color;
		if(!board.set(currPlayer, loc)) return false;
		
		for(int[] neighbor: board.adjacents(loc))
			if(!MultiDimDriver.this.check(neighbor)) remove(neighbor);
		board.demarkAll();
		if(!check(loc, currPlayer)) {
			board.clear(loc);
			board.demarkAll();
			return false;
		}
		board.demarkAll();
		
		moves.add(board.save());
		captures.add(prisoners.clone());
		turnNum++;
		return true;
	}
	/**
	 * Undoes the last move
	 * @return Whether or not the operation was successful
	 */
	boolean undo() {
		try {
			turnNum--;
			
			board.load(moves.get(turnNum - 1));
			moves.remove(turnNum);
			prisoners = captures.get(turnNum - 1);
			captures.remove(turnNum);
			return true;
		} catch(Exception e) {}
		return false;
	}
	
	/**
	 * For the first check in each direction when adding a new stone
 Ignores stones of the same color as the new one
 
 Otherwise is the same as check(int[], char)
	 */
	private boolean check(int[] loc) {
		if(!board.checkRange(loc)) return true;	//Don't care if off the edge
		char color = board.getColor(loc);
		if(color == ' ' || color == currPlayer || color > 0x5A) return true;	//Don't care if empty or same color or already checked
		board.mark(color, loc);
		for(int[] neighbor : board.adjacents(loc))	//If different color: check each direction
			if(check(neighbor, color)) return true;	//Return true if live
		return false;	//Else return false
	}
	/**
	 * For standard recursive checking
	 * 
	 * Checks if the given location is live or dead
	 * Returns dead if the space is not @color
	 */
	private boolean check(int[] loc, char color) {
		if(!board.checkRange(loc)) return false;	//Dead if off the edge
		char c = board.check(loc, color);	//Get new color and mark as checked
		if(c == ' ') return true;				//Live if empty
		if(c == color)	//If same color, check neighbors
			for(int[] neighbor : board.adjacents(loc))
				if(check(neighbor, color)) return true;	//Return true if live
		return false;	//Else return false
	}
	
	private void remove(int[] loc) {
		if(!board.checkRange(loc)) return;
		char color = board.getColor(loc);
		board.delete(loc);
		prisoners[currPlayer - 0x41]++;
		for(int[] neighbor : board.adjacents(loc))
			remove(neighbor, color);
	}
	private void remove(int[] loc, char color) {
		if(!board.checkRange(loc)) return;
		if(board.getColor(loc) != color) return;
		board.delete(loc);
		prisoners[currPlayer - 0x41]++;
		for(int[] neighbor : board.adjacents(loc))
			remove(neighbor, color);
	}
	
	//private void checkTerritory()
	public int getPrisoners(char color) {
		return prisoners[color - 0x41];
	}
	public int[] getPrisoners() {
		return prisoners;
	}
	
	public int getTerritory(char color) {
		return territory[color - 0x41];
	}
	public int[] getTerritory() {
		return territory;
	}
	
	public int getArea(char color) {
		return area[color - 0x41];
	}
	public int[] getArea() {
		return area;
	}
	
	public int getSize() {
		return size;
	}
	
	/**
	 * Prints board to System.out
	 */
	public void print() {
		System.out.println(board.toString());
		System.out.println("X:" + getPrisoners('X') + "  O:" + getPrisoners('O') + "  Z:" + getPrisoners('Z'));
		System.out.println();
	}
}
