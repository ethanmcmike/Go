package ethanmcmike.go.models;

public class Console_Test_Main {
    public static void main(String[] args) {
        Board board = new Board(4);
		
		board.set(0, 1, 'A');
		board.set(1, 0, 'A');
		board.set(2, 1, 'A');
		board.set(0, 2, 'B');
		board.set(0, 3, 'B');
		board.set(1, 3, 'B');
		board.set(2, 2, 'B');
		
		board.print();
		
		board.set(1, 2, 'A');	board.print();
		board.set(1, 1, 'B');	board.print();
		board.set(1, 2, 'A');	board.print();
		board.undo();	board.print();
    }
}
