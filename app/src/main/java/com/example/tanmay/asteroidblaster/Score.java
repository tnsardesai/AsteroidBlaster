package com.example.tanmay.asteroidblaster;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by tanmay on 4/20/17.
 */

public class Score {

    TextView score_text;
    TextView score_value;
    private int score;

    Context context;
    LinearLayout linearLayout;

    public Score(Context context, LinearLayout linearLayout){
        this.context = context;
        this.linearLayout = linearLayout;
        score=0;

        score_text = new TextView(context);
        score_text.setText("SCORE: ");
        score_text.setTextColor(Color.WHITE);
        linearLayout.addView(score_text);


        score_value = new TextView(context);
        score_value.setText(String.valueOf(score));
        score_value.setTextColor(Color.WHITE);
        linearLayout.addView(score_value);

    }

    private final Runnable mUpdateScore = new Runnable() {
        public void run() {
            // do whatever you want to change here, like:
            score++;
            score_value.setText(String.valueOf(score));
        }
    };
    private final Handler mHandler = new Handler();

    public void update(){
        mHandler.post(mUpdateScore);
        //score_value.setText(String.valueOf(score));
    }
}
