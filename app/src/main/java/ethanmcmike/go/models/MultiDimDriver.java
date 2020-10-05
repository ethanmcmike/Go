package ethanmcmike.go.models;

import java.util.ArrayList;

/**
 * @author Jacob Kelsey
 */
public class MultiDimDriver {
    public final int size, dimension, playerCount;
	public final Tessellation tessellation;
	public final Player[] players;
	private MultiDimBoard board;
	
	private Player currPlayer;
	private int turnNum;
	private int[] territory, area, prisoners;
	private ArrayList<char[]> moves;
	private ArrayList<int[]> captures;
	
	/**
	 * Constructor for any-D board with square tessellation only
	 * @param players		List of players participating in this game
	 * @param size			Number of intersections in each row
	 * @param dimensions	Number of dimensions, 2-D by default
	 */
	public MultiDimDriver(Player[] players, int size, int dimensions) {
		this.players = players;
		this.playerCount = players.length;
		this.size = size;
		this.dimension = dimensions;
		this.tessellation = Tessellation.SQUARE;
		
		board = new MultiDimBoard(dimensions, size, Tessellation.SQUARE);
		init();
	}
	/**
	 * Constructor for a 2-D board with square, triangle, or hexagon tessellation
	 * @param players	List of players participating in this game
	 * @param size		Number of intersections in each row and column
	 * @param tess		Tessellation of the game board, square by default
	 */
	public MultiDimDriver(Player[] players, int size, Tessellation tess) {
		this.players = players;
		this.playerCount = players.length;
		this.size = size;
		this.dimension = 2;
		this.tessellation = tess;
		
		board = new MultiDimBoard(2, size, tess);
		init();
	}
	
	private void init() {
		turnNum = 0;
		territory = new int[playerCount];
		area = new int[playerCount];
		prisoners = new int[playerCount];
		moves = new ArrayList();
		captures = new ArrayList();
	}
	
	/**
	 * Place a new stone
	 * @param loc Location to place new stone
	 * @param color Color of new stone
	 * @return True if successful, false if invalid move
	 */
	public boolean place(int[] loc) {
		currPlayer = players[turnNum%playerCount];
		if(!board.set(currPlayer.id, loc)) return false;
		
		for(int[] neighbor: board.adjacents(loc))
			if(!MultiDimDriver.this.check(neighbor)) remove(neighbor);
		board.demarkAll();
		if(!check(loc, currPlayer.id)) {
			board.clear(loc);
			board.demarkAll();
			return false;
		}
		board.demarkAll();
		
		moves.add(board.save());
		captures.add(prisoners.clone());
		
		//Counts up after successful placing
		turnNum++;
		return true;
	}
	/**
	 * Undoes the last move
	 * @return Boolean whether or not the operation was successful
	 */
	public boolean undo() {
		if (turnNum < 1) return false;
		//Counts down before undo
		turnNum--;

		board.load(moves.get(turnNum - 1));
		moves.remove(turnNum);
		prisoners = captures.get(turnNum - 1).clone();
		captures.remove(turnNum);
		return true;
	}
	
	/**
	 * For the first check in each direction when adding a new stone
 Ignores stones of the same color as the new one
 
 Otherwise is the same as check(int[], char)
	 */
	private boolean check(int[] loc) {
		if(!board.checkRange(loc)) return true;	//Don't care if off the edge
		char color = board.getColor(loc);
		if(color == ' ' || color == currPlayer.id || color > 0x5A) return true;	//Don't care if empty, same color, or already checked
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
		prisoners[currPlayer.id - 0x41]++;
		for(int[] neighbor : board.adjacents(loc))
			remove(neighbor, color);
	}
	private void remove(int[] loc, char color) {
		if(!board.checkRange(loc)) return;
		if(board.getColor(loc) != color) return;
		board.delete(loc);
		prisoners[currPlayer.id - 0x41]++;
		for(int[] neighbor : board.adjacents(loc))
			remove(neighbor, color);
	}
	
	//private void checkTerritory()
	/**
	 * Retrieve the number of prisoners a given player has captured
	 * @param color	The char color of player to check
	 * @return		int number of captured stones
	 */
	public int getPrisoners(char color) {
		return prisoners[color - 0x41];
	}
	/**
	 * Retrieve all captured scores
	 * @return int[] of all players' number of captured stones
	 */
	public int[] getPrisoners() {
		return prisoners;
	}
	
	/**
	 * Not implemented
	 * @param color
	 * @return 
	 */
	public int getTerritory(char color) {
		return territory[color - 0x41];
	}
	/**
	 * Not implemented
	 * @return 
	 */
	public int[] getTerritory() {
		return territory;
	}
	
	/**
	 * Not implemented
	 * @param color
	 * @return 
	 */
	public int getArea(char color) {
		return area[color - 0x41];
	}
	/**
	 * Not implemented
	 * @return 
	 */
	public int[] getArea() {
		return area;
	}
	
	/**
	 * Returns the character representation of the stone on given intersection
	 * @param coords	n-length int array where n is the number of dimensions
	 * @return			char of color
	 */
	public char getColor(int[] coords) {
		return board.getColor(coords);
	}
	
	/**
	 * Prints driver to System.out
	 */
	public void print() {
		System.out.println("Turn " + turnNum);
		System.out.println(board.toString());
		System.out.println("A:" + getPrisoners('A') + "  B:" + getPrisoners('B'));
		System.out.println();
	}
}
