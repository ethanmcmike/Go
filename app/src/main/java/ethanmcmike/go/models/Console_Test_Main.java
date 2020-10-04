package ethanmcmike.go.models;

//import java.util.Arrays;
//import javafx.application.Application;
//import javafx.stage.Stage;

public class Console_Test_Main{// extends Application{
    public static void main(String[] args) {
		
//		launch(args);
		
		MultiDimDriver board = new MultiDimDriver(3, 2, 3);
		//System.out.println(Arrays.deepToString(driver.driver.adjacents(new int[]{1,0})));
		//driver.place(new int[]{1, 0}, 'O');	driver.print();
		board.place(new int[]{0, 0}, 'O');	//driver.print();
		board.place(new int[]{0, 1}, 'O');	//driver.print();
		board.place(new int[]{0, 2}, 'O');	//driver.print();
		
		board.place(new int[]{1, 0}, 'O');	//driver.print();
		board.place(new int[]{1, 1}, 'O');	board.print();
		board.place(new int[]{1, 2}, 'X');	board.print();
		board.place(new int[]{2, 0}, 'X');	board.print();
		board.place(new int[]{2, 1}, 'X');	board.print();
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
