package ethanmcmike.go.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ethanmcmike.go.R;

public class MainMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
    }

    public void onNewGame(View view){

        //Go to new game screen
        Intent intent = new Intent(this, NewGameActivity.class);
        startActivity(intent);
        finish();
    }

    public void onSettings(View view){

    }

    public void onRules(View view){

    }
}
