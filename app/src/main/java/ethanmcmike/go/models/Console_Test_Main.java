package ethanmcmike.go.models;

//import java.util.Arrays;
//import javafx.application.Application;
//import javafx.stage.Stage;

public class Console_Test_Main{// extends Application{
    public static void main(String[] args) {
		
//		launch(args);
		
		MultiDimDriver board = new MultiDimDriver(3, 2);
		//System.out.println(Arrays.deepToString(board.board.adjacents(new int[]{1,0})));
		board.place(new int[]{1, 1}, 'O');//	board.print();
		board.place(new int[]{1, 2}, 'O');//	board.print();
		board.place(new int[]{2, 1}, 'O');//	board.print();
		board.place(new int[]{2, 2}, 'O');//	board.print();
		
		
		board.place(new int[]{0, 0}, 'X');//	board.print();
		board.place(new int[]{0, 1}, 'X');//	board.print();
		board.place(new int[]{0, 2}, 'X');//	board.print();
		//board.place(new int[]{1, 0}, 'X');		board.print();
		
		board.place(new int[]{2, 0}, 'Z');		board.print();
		
		board.place(new int[]{1, 0}, 'O');		board.print();
		board.undo();	board.print();
		board.place(new int[]{1, 0}, 'X');		board.print();
		board.undo();	board.print();
		board.place(new int[]{1, 0}, 'Z');		board.print();
		board.undo();	board.print();
		

//		int s = 5;
//		int d = 4;
//		char[] a = new char[(int)Math.pow(s, d)];
//		for(int i = 0; i < a.length; i++)
//			a[i] = (char)(0x41 + i);
//		MultiDimBoard x = new MultiDimBoard(d, s, a);
//		System.out.println(x.toString());
//    }
//
//	@Override
//	public void start(Stage primaryStage) throws Exception {
//		
//		Board board = new Board(17);
//		
//		GoDebugger debug = new GoDebugger(board);
//		debug.show();
	}
}
