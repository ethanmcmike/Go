package ethanmcmike.go.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import ethanmcmike.go.R;
import ethanmcmike.go.models.Tessellation;

public class NewGameActivity extends Activity {

    //Extras
    public static final String EXTRA_PLAYERS = "players";
    public static final String EXTRA_DIMENSIONS = "dimensions";
    public static final String EXTRA_TESSELLATION = "tessellation";
    public static final String EXTRA_COLORS = "colors";

    //Views
    Spinner playersView, dimensionsView, tessellationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_game);

        playersView = findViewById(R.id.players);
        dimensionsView = findViewById(R.id.dimensions);
        tessellationView = findViewById(R.id.tessellation);
    }

    public void onStart(View view){

        int numPlayers = Integer.valueOf(playersView.getSelectedItem().toString());
        int dimensions;
        Tessellation tessellation;

        switch(dimensionsView.getSelectedItem().toString()){
            case "3D":
                dimensions = 3;
                break;

            default:
                dimensions = 2;
                break;
        }

        switch(tessellationView.getSelectedItem().toString()){
            case "Hexagons":
                tessellation = Tessellation.HEXAGON;
                break;

            case "Triangles":
                tessellation = Tessellation.TRIANGLE;
                break;

            default:
                tessellation = Tessellation.SQUARE;
                break;
        }

        int[] colors = new int[]{Color.BLACK, Color.WHITE, Color.RED, Color.GREEN, Color.CYAN};

        Intent intent = new Intent(this, BoardActivity.class);
        intent.putExtra(EXTRA_PLAYERS, numPlayers);
        intent.putExtra(EXTRA_DIMENSIONS, dimensions);
        intent.putExtra(EXTRA_TESSELLATION, tessellation.toString());
        intent.putExtra(EXTRA_COLORS, colors);

        startActivity(intent);
        finish();
    }
}
