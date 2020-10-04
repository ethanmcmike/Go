//package ethanmcmike.go.models;
//
//import javafx.event.Event;
//import javafx.event.EventHandler;
//import javafx.scene.Scene;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.paint.Color;
//import javafx.stage.Stage;
//
//public class GoDebugger extends Stage {
//
//    //Settings
//    public final String TITLE = "Go Debugger";
//    public final int WIDTH = 640;
//    public final int HEIGHT = 480;
//
//    private static Color BACKGROUND = Color.BLACK;
//    private static Color BOARD = Color.gray(0.2);
//    private static Color LINE = Color.WHITE;
//    private static int LINE_WIDTH = 1;
//
//    //Game
//    private MultiDimDriver board;
//    private boolean turn;
//
//    //Graphics
//    private GraphicsContext gc;
//
//    int boardSize;
//    float tileSize, gridSize, stoneSize, indicatorSize;
//    float gridStartX, gridStartY;
//
//    public GoDebugger(MultiDimDriver board){
//        this.board = board;
//
//        initViews();
//        resize();
//        render();
//    }
//
//    private void resize(){
//        boardSize = (WIDTH > HEIGHT) ? HEIGHT : WIDTH;
//        tileSize = boardSize / board.getSize();
//        gridSize = tileSize * (board.getSize() - 1);
//        stoneSize = 0.6f * tileSize;        //Radius
//        gridStartX = (WIDTH - gridSize)/2f;
//        gridStartY = (HEIGHT - gridSize)/2f;
//    }
//
//    private void initViews(){
//
//        Canvas canvas = new Canvas(WIDTH, HEIGHT);
//        gc = canvas.getGraphicsContext2D();
//
//        BorderPane bp = new BorderPane();
//        bp.setCenter(canvas);
//
//        Scene scene = new Scene(bp);
//        scene.setFill(BACKGROUND);
//
//        setScene(scene);
//
//        scene.setOnMouseClicked(onClick);
//
//        setTitle(TITLE);
//    }
//
//    private void render(){
//
//        clear();
//
//        gc.save();
//
//        //Translate to center
//        gc.translate(WIDTH/2f, HEIGHT/2f);
//
//        drawBoard();
//
//        //Translate to top left of board
//        gc.translate(-gridSize/2f, -gridSize/2f);
//
//        //Stones
//
//        for(int i=0; i < boardSize; i++){
//            for(int j=0; j < boardSize; j++){
//				int[] tile = {i, j};
//                Color color = getColor(board.board.getColor(tile));
//                drawStone(color, i, j);
//            }
//        }
//
//        gc.restore();
//    }
//
//    private void drawBoard() {
//        //Assumes in center of canvas
//
//        //Board background
//        float halfBoard = boardSize / 2f;
//
//        gc.setFill(BOARD);
//
//        gc.fillRect(-halfBoard, -halfBoard, boardSize, boardSize);
//
//        //Grid
//        gc.setStroke(LINE);
//        gc.setLineWidth(LINE_WIDTH);
//
//        gc.save();
//        gc.translate(-gridSize/2f, -gridSize/2f);
//
//        for(int i=0; i<=gridSize; i+=tileSize){
//            gc.strokeLine(0, i, gridSize, i);
//            gc.strokeLine(i, 0, i, gridSize);
//        }
//
//        gc.restore();
//    }
//
//    private void drawStone(Color color, int x, int y){
//        //Assumes translated to top left of grid
//
//        gc.setFill(color);
//
//        gc.save();
//        gc.translate(x*tileSize, y*tileSize);
//
//        gc.fillOval(-stoneSize/2, -stoneSize/2, stoneSize, stoneSize);
//
//        gc.restore();
//    }
//
//    public void clear(){
//        gc.setFill(BACKGROUND);
//        gc.fillRect(0, 0, WIDTH, HEIGHT);
//    }
//
//    private int getPosX(double x){
//        return (int)((x-gridStartX + tileSize/2f) / tileSize);
//    }
//
//    private int getPosY(double y){
//        return (int)((y-gridStartY + tileSize/2f) / tileSize);
//    }
//
//    private Color getColor(char id){
//		switch (id) {
//			case 'A':
//				return Color.WHITE;
//			case 'B':
//				return Color.BLACK;
//			default:
//				return Color.TRANSPARENT;
//		}
//    }
//
//    EventHandler onClick = new EventHandler(){
//        @Override
//        public void handle(Event event) {
//
//            double x = ((MouseEvent)event).getX();
//            double y = ((MouseEvent)event).getY();
//
//			int[] pos = {getPosX(x), getPosY(y)};
//
//            if(turn)
//				board.place(pos, 'A');
//            else
//				board.place(pos, 'B');
//
//            turn = !turn;
//
//            render();
//        }
//    };
//}