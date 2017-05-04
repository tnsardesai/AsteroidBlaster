package com.example.tanmay.asteroidblaster;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;

    LinearLayout linearLayout;

    private static final String TABLE_SOUND = "sound";
    private static final String SOUND_ID = "id";
    private static final String SOUND_STATE = "state";

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display =getWindowManager().getDefaultDisplay();

        Point size =new Point();
        display.getSize(size);

        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(Color.BLACK);

        String name =  getIntent().getStringExtra("USERNAME");

        gameView = new GameView(this,size.x,size.y,linearLayout,name);

        linearLayout.addView(gameView);

        setContentView(linearLayout);


        mp = MediaPlayer.create(getApplicationContext(), R.raw.game);
        mp.setLooping(true);
        mp.start();

        DbHelper mDbHelper = new DbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String[] projection = {
                SOUND_STATE
        };

        Cursor cursor = db.query(
                TABLE_SOUND,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToNext();
        if(cursor.getLong(cursor.getColumnIndexOrThrow(SOUND_STATE)) == 0){
            Log.d("GAME","entered");
            mp.setVolume(0,0);
        }
        cursor.close();

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
