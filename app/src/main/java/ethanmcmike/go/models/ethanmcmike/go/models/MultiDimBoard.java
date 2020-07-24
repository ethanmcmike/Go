package ethanmcmike.go.models;

import java.util.Arrays;

/**
 * @author Jacob Kelsey
 */
public class MultiDimBoard {
    private char[] board;
	private final int size, dim;
	
	public MultiDimBoard(int dimensions, int size) {
		if(dimensions < 1) throw new ExceptionInInitializerError("dimensions must be positive");
		this.dim = dimensions;
		this.size = size;
		this.board = new char[(int)Math.pow(this.size, dim)];
		Arrays.fill(board, ' ');
	}
	public MultiDimBoard(int dimensions, int size, char[] array) {
		this(dimensions, size);
		if(board.length != array.length) throw new ExceptionInInitializerError("Array length does not match dimensions and size");
		board = array.clone();
	}
	
//	Methods previously in the intersections class
//	These are for the manipulation of individual tiles and pieces on the board
//	============================================================================
	private int getIndex(int[] coordinates) {
		if(coordinates.length != dim) throw new ArrayIndexOutOfBoundsException("Wrong number of coordinates");
		int index = 0;
		for (int i = 0; i < dim; i++) {
			if(coordinates[i] >= size) throw new ArrayIndexOutOfBoundsException();
			index += Math.pow(size, i)*coordinates[i];
		}
		return index;
	}
	boolean checkRange(int[] coordinates) {
		if(coordinates.length != dim) throw new ArrayIndexOutOfBoundsException("Wrong number of coordinates");
		for (int i : coordinates) if(i >= size || i < 0) return false;
		return true;
	}
	
	private char get(int index){
		return board[index];
	}
	char get(int[] coordinates){
		return board[getIndex(coordinates)];
	}
	public char getColor(int[] coordinates){
		char temp = get(coordinates);
		if(temp >= 0x60) temp -= 0x20;
//		temp &= 0xDF;	//Cant use because it messes with the space characters
		return temp;
	}
	char check(int[] coordinates, char color){
		char temp = get(coordinates);
		if(temp == color) mark(color, coordinates);
		return temp;
	}
	
	private void set(char element, int index) {
		board[index] = element;
	}
	boolean set(char element, int[] coordinates) {
		if(element < 0x41 || element > 0x5A) return false;
		int temp = getIndex(coordinates);
		if(get(temp) != ' ') return false;
		set(element, temp);
		return true;
	}
	void clear(int[] coordinates) {
		set(' ', getIndex(coordinates));
	}
	void delete(int[] coordinates) {
		set('@', getIndex(coordinates));
	}
	void mark(char color, int[] coordinates) {
		if(color == 0x20 || color >= 0x41 && color <= 0x5A)
			set((char)(color+0x20), getIndex(coordinates));
	}
	void mark(int[] coordinates) {
		mark(get(coordinates), coordinates);
	}
	void demark(char color, int[] coordinates) {
		if(color >= 0x60 && color <= 0x7A)
			set((char)(color-0x20), getIndex(coordinates));
	}
	void demark(int[] coordinates) {
		demark(get(coordinates), coordinates);
	}
	void demarkAll() {
		for(int i = 0; i < board.length; i++)
			if(board[i] == 0x40 || board[i] >= 0x61 && board[i] <= 0x7A)
				board[i] -= 0x20;
	}
//	End of individual intersections=============================================
	
//	Methods for the interactions between multiple intersections
//	============================================================================
	/**
	 * Returns an int array of size 2*coordinates x coordinates
	 * @param coordinates
	 * @return the coordinates of all adjacent elements in any dimension
	 */
	int[][] adjacents(int[] coordinates) {
		if(coordinates.length != dim) throw new ArrayIndexOutOfBoundsException("Wrong number of coordinates");
		int[][] points = new int[2*dim][dim];
		for (int i = 0; i < dim; i++) {
			points[i] = coordinates.clone();
			points[i][i]--;
			
			points[i+dim] = coordinates.clone();
			points[i+dim][i]++;
		}
		return points;
	} 
//	End of intersection interactions============================================
	
	/**
	 * Saves a copy of the current board array
	 * @return  char[] of board
	 */
	public char[] save() {
		return board.clone();
	}
	/**
	 * Loads a saved board
	 * @param board The saved board to load
	 * @return Whether or not the operation was successful
	 */
	boolean load(char[] board) {
		try {
			if (this.board.length == board.length) {
				this.board = board.clone();
				return true;
			}
		} catch (Exception e) {}
		return false;
	}
	
	@Override
	public String toString() {
		int maxDim = dim - (dim%2 == 0 || dim == 1 ? 0 : 1);
		return recursiveString(maxDim, new int[dim]);
	}
	private String recursiveString(int dim, int[] coordinates) {
		if(dim < 0) return "" + get(coordinates);
		
		int nextDim = dim - 2;
		if(nextDim == 0) nextDim = this.dim - (this.dim%2 == 0 ? 1 : 0);
		
		String temp = "";
		for(int i = 0; i < size; i++) {
			boolean even = dim%2 == 0;
			if(i != 0)
				for(int j = 0; j < dim/(even?2:1); j++)
					temp += even ? "\n" : " ";
			coordinates[dim-1] = i;
			temp += recursiveString(nextDim, coordinates);
		}
		return temp;
	}
}