package ethanmcmike.go.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import ethanmcmike.go.R;
import ethanmcmike.go.models.Game;
import ethanmcmike.go.views.GameView;

public class BoardActivity extends Activity {

    public static Game game;
    GameView view;

    TextView leftScore, rightScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("NEW ACTIVITY");
        setContentView(R.layout.main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if(game == null) {
            game = new Game();
            System.out.println("NEW GAME");
        }

        init();
    }

    private void init(){
        view = findViewById(R.id.boardView);
        view.setGame(game);
        view.setActivity(this);

        leftScore = findViewById(R.id.left_score);
        rightScore = findViewById(R.id.right_score);
    }

    public void update(){
        leftScore.setText(String.valueOf(game.getScore(game.p1.id)));
        rightScore.setText(String.valueOf(game.getScore(game.p2.id)));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_MENU:
                game.undo();
                view.update();
                System.out.println("UNDO");
//                PopupMenu popup = new PopupMenu(this, view);
//                getMenuInflater().inflate(R.menu.game_menu, popup.getMenu());
                break;
        }

        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        switch(item.getItemId()){
            case R.id.game_menu_newGame:
                game = new Game();
                break;

            case R.id.game_menu_undo:
                game.undo();
                break;
        }

        return false;
    }
}
