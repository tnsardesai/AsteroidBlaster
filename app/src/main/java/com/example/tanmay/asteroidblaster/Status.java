package com.example.tanmay.asteroidblaster;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
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
        health = 10;

        final RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

        status_text = new TextView(context);
        status_text.setText("SCORE: " + String.valueOf(score) + "\tCOINS: " + String.valueOf(coins) + "\tHEALTH: " + String.valueOf(health));
        status_text.setTextColor(Color.WHITE);
        linearLayout.addView(status_text);

    }

    private final Runnable mUpdateScore = new Runnable() {
        public void run() {
            // do whatever you want to change here, like:
            score++;
            status_text.setText("SCORE: " + String.valueOf(score) + "\tCOINS: " + String.valueOf(coins) + "\tHEALTH: " + String.valueOf(health));
        }
    };

    private final Runnable mUpdateCoins = new Runnable() {
        public void run() {
            // do whatever you want to change here, like:
            coins++;
            status_text.setText("SCORE: " + String.valueOf(score) + "\tCOINS: " + String.valueOf(coins) + "\tHEALTH: " + String.valueOf(health));
        }
    };

    private final Runnable mUpdateHealth = new Runnable() {
        public void run() {
            // do whatever you want to change here, like:
            health--;
            status_text.setText("SCORE: " + String.valueOf(score) + "\tCOINS: " + String.valueOf(coins) + "\tHEALTH: " + String.valueOf(health));
        }
    };

    private final Handler mHandler = new Handler();

    public void update_score(){
        mHandler.post(mUpdateScore);
        //score_value.setText(String.valueOf(score));
    }

    public void update_coins(){
        mHandler.post(mUpdateCoins);
        //score_value.setText(String.valueOf(score));
    }

    public void update_health(){
        mHandler.post(mUpdateHealth);
        //score_value.setText(String.valueOf(score));
    }

    public int getHealth(){
        return health;
    }
}
