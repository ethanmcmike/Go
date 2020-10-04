package ethanmcmike.go.activities;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ethanmcmike.go.R;
import ethanmcmike.go.models.Game;
import ethanmcmike.go.models.Player;
import ethanmcmike.go.models.Tessellation;
import ethanmcmike.go.views.GameView;

public class BoardActivity extends Activity {

    public static Game game;
    private Button settingsButton, undoButton;
    GameView gameView;

    TextView leftScore, rightScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("NEW ACTIVITY");
        setContentView(R.layout.main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Game data
        Intent intent = getIntent();
        int numPlayers = intent.getIntExtra(NewGameActivity.EXTRA_PLAYERS, 2);
        int dimensions = intent.getIntExtra(NewGameActivity.EXTRA_DIMENSIONS, 2);
        Tessellation tessellation = Tessellation.valueOf(intent.getStringExtra(NewGameActivity.EXTRA_TESSELLATION));
        int[] colors = intent.getIntArrayExtra(NewGameActivity.EXTRA_COLORS);

        Player[] players = new Player[numPlayers];

        for(int i=0; i<numPlayers; i++){
            players[i] = new Player((char)('A' + i), colors[i]);
        }

        settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            }
        );

        undoButton = findViewById(R.id.undoButton);
        undoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        game.undo();
                        view.update();
                    }
                }
        );
      
        game = new Game(19, players, tessellation);

        //Views
        gameView = findViewById(R.id.boardView);
        gameView.setGame(game);
        gameView.setActivity(this);

        leftScore = findViewById(R.id.left_score);
        rightScore = findViewById(R.id.right_score);
    }

    public void update(){
//        leftScore.setText(String.valueOf(game.getScore(game.p1.id)));
//        rightScore.setText(String.valueOf(game.getScore(game.p2.id)));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_MENU:
                game.undo();
                view.update();
//                PopupMenu popup = new PopupMenu(this, view);
                gameView.update();
                System.out.println("UNDO");
//                PopupMenu popup = new PopupMenu(this, gameView);
//                getMenuInflater().inflate(R.menu.game_menu, popup.getMenu());
                break;
        }

        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        switch(item.getItemId()){
            case R.id.game_menu_newGame:
//                game = new Game();
                break;

            case R.id.game_menu_undo:
                game.undo();
                break;
        }

        return false;
    }

    public void OnUndo(View view){
        game.undo();
        this.gameView.update();
    }
}
