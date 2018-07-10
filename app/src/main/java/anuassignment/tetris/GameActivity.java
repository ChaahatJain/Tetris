package anuassignment.tetris;

/**
 * Created by chaahatjain on 10/7/18.
 */

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Display;

/**
 * Purpose of this class
 * Have basic pause/resume functionality
 * Send everything over to the view
 */

public class GameActivity extends Activity {
    private TetrisView tetrisView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get the size of the screen
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // create a view for the actual game
        tetrisView = new TetrisView(this, size.x, size.y);
        setContentView(tetrisView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        tetrisView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tetrisView.resume();
    }
}
