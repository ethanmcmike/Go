package ethanmcmike.go.renderers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.ScaleGestureDetector;

import ethanmcmike.go.models.Board;
import ethanmcmike.go.models.Game;
import ethanmcmike.go.models.Player;

public class GameRenderer {

    private static int BACKGROUND = Color.BLACK;
    private static int BOARD = Color.DKGRAY;
    private static int LINE = Color.WHITE;
    private static int TEXT_SIZE = 20;

    private Game game;
    private Board board;
    private Paint paint;

    int width, height, boardSize;
    float tileSize, gridSize, stoneSize, indicatorSize, marginSize;
    float gridStartX, gridStartY;
    float scaleFactor;

    public GameRenderer(Game game){
        this.game = game;
        this.board = game.getBoard();
        paint = new Paint();
        paint.setTextSize(TEXT_SIZE);
    }

    private void setPaint(int color, int width, Paint.Style style){
        paint.setColor(color);
        paint.setStrokeWidth(width);
        paint.setStyle(style);
    }

    public void render(Canvas canvas){
        clear(canvas);

        resize(canvas);

        //Translate to center
        canvas.save();
        canvas.translate(canvas.getWidth()/2, canvas.getHeight()/2);

        drawBoard(canvas);

        canvas.translate(-gridSize/2f, -gridSize/2f);
        //Stones
        char[][] tiles = board.toArray();
        for(int i=0; i<board.getSize(); i++){
            for(int j=0; j<board.getSize(); j++){
                char id = tiles[j][i];
                int color = game.getColor(id);
                drawStone(color, i, j, canvas);
            }
        }

        canvas.restore();

        //Turn indicator
        int playerColor = game.turn.color;
//            //Backdrop
//            canvas.save();
//            canvas.translate(width/2f, 0);
//            setPaint(BOARD, 1, Paint.Style.FILL);
//            canvas.drawRect(-marginSize/2f, 0, marginSize/2f, marginSize, paint);
//            canvas.restore();
        setPaint(BOARD, 1, Paint.Style.FILL);
        canvas.drawCircle(width/2f, marginSize/2f, indicatorSize+20, paint);
        setPaint(playerColor, 1, Paint.Style.FILL);
        canvas.drawCircle(width/2f, marginSize/2f, indicatorSize, paint);

        //Scores
//        canvas,

        canvas.scale(scaleFactor, scaleFactor);
    }

    private void drawBoard(Canvas canvas) {
        //Assumes in center of canvas

        //Board background
        float halfBoard = boardSize / 2f;
        setPaint(BOARD, 1, Paint.Style.FILL);
        canvas.drawRect(-halfBoard, -halfBoard, halfBoard, halfBoard, paint);

        //Grid
        setPaint(LINE, 1, Paint.Style.STROKE);
        canvas.save();
        canvas.translate(-gridSize/2f, -gridSize/2f);

        for(int i=0; i<=gridSize; i+=tileSize){
            canvas.drawLine(0, i, gridSize, i, paint);
            canvas.drawLine(i, 0, i, gridSize, paint);
        }

        canvas.restore();
    }

    private void drawStone(int color, int x, int y, Canvas canvas){
        //Assumes translated to top left of grid

        setPaint(color, 1, Paint.Style.FILL);

        canvas.save();
        canvas.translate(x*tileSize, y*tileSize);

        canvas.drawCircle(0, 0, stoneSize, paint);

        canvas.restore();
    }

    private void resize(Canvas canvas){
        width = canvas.getWidth();
        height = canvas.getHeight();

        if(width < height){
            boardSize = width;
            marginSize = (height - boardSize) / 2f;
        } else{
            boardSize = height;
            marginSize = (width - boardSize) / 2f;
        }

        tileSize = boardSize / board.getSize();
        gridSize = tileSize * (board.getSize() - 1);
        stoneSize = 0.4f * tileSize;        //Radius
        gridStartX = (width - gridSize)/2f;
        gridStartY = (height - gridSize)/2f;

        indicatorSize = 0.25f * marginSize;
    }

    public void clear(Canvas canvas){
        canvas.drawColor(BACKGROUND);
    }

    public int getPosX(float x){
        return (int)((x-gridStartX + tileSize/2f) / tileSize);
    }

    public int getPosY(float y){
        return (int)((y-gridStartY + tileSize/2f) / tileSize);
    }

    public void scale(ScaleGestureDetector detector){

        scaleFactor *= detector.getScaleFactor();

        //Bound the scale factor
        scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 1000.0f));
        System.out.println("SCALE: " + scaleFactor);
    }
}
