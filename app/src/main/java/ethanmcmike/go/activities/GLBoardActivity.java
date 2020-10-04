package ethanmcmike.go.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import ethanmcmike.go.views.GLGameView;

public class GLBoardActivity extends AppCompatActivity {

    GLGameView view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = new GLGameView(this);

        setContentView(view);
    }
}
