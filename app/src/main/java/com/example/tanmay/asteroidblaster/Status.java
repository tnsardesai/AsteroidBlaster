package com.example.tanmay.asteroidblaster;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by tanmay on 4/20/17.
 */

public class Status {

    TextView status_text;
    private int score;
    private int coins;
    private int health;


    Context context;
    LinearLayout linearLayout;



    public Status(Context context, LinearLayout linearLayout){
        this.context = context;
        this.linearLayout = linearLayout;
        score=0;
        coins = 0;
        health = 5;

        final RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

        status_text = new TextView(context);
        status_text.setText("SCORE: " + String.valueOf(score) + "\tCOINS: " + String.valueOf(coins) + "\tHEALTH: " + String.valueOf(health));
        status_text.setTextColor(Color.WHITE);
        status_text.setGravity(Gravity.CENTER);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/Grinched.ttf");
        status_text.setTypeface(custom_font);
        status_text.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        linearLayout.addView(status_text);

    }

    private final Runnable mUpdateScore = new Runnable() {
        public void run() {
            // do whatever you want to change here, like:
            //score++;
            status_text.setText("SCORE: " + String.valueOf(score) + "\t\tCOINS: " + String.valueOf(coins) + "\t\tHEALTH: " + String.valueOf(health));
        }
    };

    private final Runnable mUpdateCoins = new Runnable() {
        public void run() {
            // do whatever you want to change here, like:
            coins++;
            status_text.setText("SCORE: " + String.valueOf(score) + "\t\tCOINS: " + String.valueOf(coins) + "\t\tHEALTH: " + String.valueOf(health));
        }
    };

    private final Runnable mUpdateHealth = new Runnable() {
        public void run() {
            // do whatever you want to change here, like:
            //health--;
            status_text.setText("SCORE: " + String.valueOf(score) + "\t\tCOINS: " + String.valueOf(coins) + "\t\tHEALTH: " + String.valueOf(health));
        }
    };

    private final Handler mHandler = new Handler();

    public void update_score(){
        mHandler.post(mUpdateScore);
    }

    public void update_coins(){
        mHandler.post(mUpdateCoins);
    }

    public void update_health(){
        mHandler.post(mUpdateHealth);
    }

    public int getHealth(){
        return health;
    }

    public int getScore(){
        return score;
    }

    public int getCoins(){
        return coins;
    }

    public void setScore(int score){
        this.score = score;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public void setCoins(int coins){
        this.coins = coins;
    }
}
