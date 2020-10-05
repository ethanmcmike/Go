package ethanmcmike.go.models;

public class Console_Test_Main{// extends Application{
    public static void main(String[] args) {
		Player[] players = {new Player('A', 1), new Player('B', 2)};
				
		MultiDimDriver board = new MultiDimDriver(players, 3, Tessellation.SQUARE);
		board.place(new int[]{0, 0});	board.print();
		board.place(new int[]{0, 1});	board.print();
		board.place(new int[]{0, 2});	board.print();
		
		board.place(new int[]{1, 0});	board.print();
		board.place(new int[]{1, 1});	board.print();
		board.place(new int[]{1, 2});	board.print();
		board.place(new int[]{2, 0});	board.print();
		board.place(new int[]{2, 1});	board.print();
		board.place(new int[]{2, 2});	board.print();
		board.undo(); board.print();
//	@Override
//	public void start(Stage primaryStage) throws Exception {
//		
//		Board driver = new Board(17);
//		
//		GoDebugger debug = new GoDebugger(driver);
//		debug.show();
	}
}
