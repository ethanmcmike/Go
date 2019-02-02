package firstneuralnet.renderers;

import javafx.application.Application;
import javafx.stage.Stage;

public class GoMain extends Application{
    
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        
        Board board = new Board(17);
        
        GoDebugger debug = new GoDebugger(board);
        debug.show();
    }
}
