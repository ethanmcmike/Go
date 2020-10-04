package ethanmcmike.go.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import ethanmcmike.go.activities.BoardActivity;
import ethanmcmike.go.models.Game;
import ethanmcmike.go.renderers.GameRenderer;

public class GameView extends View {

    private static int BACKGROUND = Color.BLACK;
    private static int LINE = Color.GRAY;

    Context context;
    BoardActivity boardActivity;
    Game game;
    GameRenderer renderer;

    public GameView(Context context) {
        super(context);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context = context;
    }

    public void setActivity(BoardActivity boardActivity){
        this.boardActivity = boardActivity;
    }

    public void setGame(Game game){
        this.game = game;
        renderer = new GameRenderer(game);
    }

    public void update(){
        boardActivity.update();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        renderer.render(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        int posX = renderer.getPosX(x);
        int posY = renderer.getPosY(y);

        int size = game.getDriver().getSize();

        if(posX>=0 && posX<size && posY>=0 && posY<size)
            game.play(posX, posY);

        update();

        return super.onTouchEvent(event);
    }
}
