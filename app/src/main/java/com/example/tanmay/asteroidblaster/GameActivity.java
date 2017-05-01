package com.example.tanmay.asteroidblaster;

import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display =getWindowManager().getDefaultDisplay();

        Point size =new Point();
        display.getSize(size);

        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(Color.BLACK);


        gameView = new GameView(this,size.x,size.y,linearLayout);

        linearLayout.addView(gameView);

        setContentView(linearLayout);


        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.game);
        mp.setLooping(true);
        mp.start();

    }

    @Override
    public void onBackPressed(){
        //do nothing
    }

    @Override
    protected void onPause(){
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        gameView.resume();
    }

}
