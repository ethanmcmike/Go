package ethanmcmike.go.models;

import java.util.Arrays;

/**
 * @author Jacob Kelsey
 */
public class MultiDimBoard {
    private char[] board;
	private final int size, dim;
	private Tessellation tessellation;
	
	public MultiDimBoard(int dimensions, int size, Tessellation tess) {
		if(dimensions < 1) throw new ExceptionInInitializerError("Dimensions must be positive.");
		if(dimensions != 2 && tess != Tessellation.SQUARE) throw new ExceptionInInitializerError("In order to use alternate tessellations, you must use a 2-D driver.");
		this.dim = dimensions;
		this.size = size;
		this.tessellation = tess;
		this.board = new char[(int)Math.pow(this.size, dim)];
		Arrays.fill(board, ' ');
	}
	public MultiDimBoard(int dimensions, int size, Tessellation tess, char[] array) {
		this(dimensions, size, tess);
		if(board.length != array.length) throw new ExceptionInInitializerError("Array length does not match");
		board = array.clone();
	}
	
//	Methods previously in the intersections class
//	These are for the manipulation of individual tiles and pieces on the driver
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
	char getColor(int[] coordinates){
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
		int[][] points;
		
		if(tessellation == Tessellation.SQUARE) {
			points = new int[2*dim][dim];
			for(int i = 0; i < dim; i++) {
				points[i] = coordinates.clone();
				points[i][i]--;

				points[i+dim] = coordinates.clone();
				points[i+dim][i]++;
			}
		} else {
			int row = coordinates[0];
			int col = coordinates[1];
			
			points = new int[tessellation.id][2];
			
			if(tessellation == Tessellation.TRIANGLE) {
				int[][] mods = {{-1,0},{1,0}, {0,-1},{0,1}, {-1,-1},{1,-1}};
				for(int i = 0; i < mods.length; i++) {
					points[i][0] = row + mods[i][0];
					points[i][1] = col + (row%2==0 ? mods[i][1] : -mods[i][1]);
				}
			}
			if(tessellation == Tessellation.HEXAGON) {
				points[0][0] = row - 1;	//up
				points[0][1] = col;
				
				points[1][0] = row + 1;	//down
				points[1][1] = col;
				
				points[2][0] = row;		//right or left depending on position
				points[2][1] = col + ((row+col)%2==0 ? 1:-1);
			}
		}
		return points;
	} 
//	End of intersection interactions============================================
	
	/**
	 * Saves a copy of the current driver array
	 * @return  char[] of driver
	 */
	public char[] save() {
		return board.clone();
	}
	/**
	 * Loads a saved driver
	 * @param board The saved driver to load
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
		if(tessellation == Tessellation.SQUARE) {
			int maxDim = dim - (dim%2 == 0 || dim == 1 ? 0 : 1);
			return recursiveString(maxDim, new int[dim]);
		}
		
		String temp = "";
		if(tessellation == Tessellation.TRIANGLE) {
			for(int i = 0; i < size; i++) {
				if(i%2==1) temp += " ";
				for(int j = 0; j < size; j++)
					temp += get(new int[] {i,j}) + " ";
				temp += "\n";
			}
		} if(tessellation == Tessellation.HEXAGON) {
			for(int i = 0; i < size; i++) {
				for(int j = 0; j < size; j++) {
					if((i+j)%2 == 0)	temp += " " + get(new int[] {i, j});
					else				temp += get(new int[] {i, j}) + " ";
				}
				temp += "\n";
			}
		}
		return temp;
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