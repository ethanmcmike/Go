package ethanmcmike.go.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ethanmcmike.go.R;
import ethanmcmike.go.models.Game;
import ethanmcmike.go.models.Player;
import ethanmcmike.go.models.Tessellation;
import ethanmcmike.go.views.GameView;

public class BoardActivity extends AppCompatActivity {

    //Models
    private Game game;

    //Views
    private GameView gameView;
    private TextView leftScore, rightScore;
    private Button settingsButton, undoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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
      
        game = new Game(19, players, tessellation);

        //Views
        gameView = findViewById(R.id.boardView);
        gameView.setGame(game);
        gameView.setActivity(this);
    }

    public void update(){

        int p1 = game.getScore(game.players[0]);

//        leftScore.setText(String.valueOf(driver));
//        rightScore.setText(String.valueOf(game.getScore(game.p2.id)));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_MENU:
                game.undo();
                gameView.update();
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
    public void onBackPressed() {
        onSettings();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.game_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.game_menu_newGame:
                onNewGame();
                break;

            case R.id.game_menu_undo:
                onUndo();
                break;
        }

        return false;
    }

    public void onUndo(){
        game.undo();
        gameView.update();
    }

    public void onNewGame(){
        gameView.update();
    }

    public void onSettings(){
//        Intent intent = new Intent(this, SettingsActivity.class);
//        startActivity(intent);
    }
}
