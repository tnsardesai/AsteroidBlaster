package com.example.tanmay.asteroidblaster;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {


    private static final String DATABASE_NAME = "AsteroidBlaster.db";

    private static final String TABLE_HIGH = "highscores";

    private static final String HIGH_KEY_ID = "id";
    private static final String HIGH_SCORE = "score";
    private static final String HIGH_NAME = "name";

    public DbHelper mDbHelper = new DbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String deleteQuery = "DELETE FROM " + TABLE_HIGH + " WHERE " + HIGH_KEY_ID +
                " NOT IN (SELECT " + HIGH_KEY_ID + " FROM " + TABLE_HIGH  + " ORDER BY " +
                HIGH_SCORE + " DESC " + " LIMIT 5)";

        db.execSQL(deleteQuery);

        String[] projection = {
                HIGH_KEY_ID,
                HIGH_SCORE,
                HIGH_NAME
        };

        String sortOrder =
                HIGH_SCORE + " DESC";

        Cursor cursor = db.query(
                TABLE_HIGH,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Grinched.ttf");

        ArrayList<TextView> scores = new ArrayList<TextView>(5);
        scores.add((TextView)findViewById(R.id.High_score_1));
        scores.add((TextView)findViewById(R.id.High_score_2));
        scores.add((TextView)findViewById(R.id.High_score_3));
        scores.add((TextView)findViewById(R.id.High_score_4));
        scores.add((TextView)findViewById(R.id.High_score_5));

        ArrayList<TextView> names = new ArrayList<TextView>(5);
        names.add((TextView)findViewById(R.id.High_name_1));
        names.add((TextView)findViewById(R.id.High_name_2));
        names.add((TextView)findViewById(R.id.High_name_3));
        names.add((TextView)findViewById(R.id.High_name_4));
        names.add((TextView)findViewById(R.id.High_name_5));

        for (int i=0; i<5;i++){
            scores.get(i).setTypeface(custom_font);
            names.get(i).setTypeface(custom_font);
        }

        int i=0;
        while(cursor.moveToNext()){
            scores.get(i).setText(String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(HIGH_SCORE))));
            names.get(i).setText(cursor.getString(cursor.getColumnIndexOrThrow(HIGH_NAME)));
            Log.d("Score","name: " + cursor.getString(cursor.getColumnIndexOrThrow(HIGH_NAME)) + "score: " + String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(HIGH_SCORE))));
            i++;
        }
        cursor.close();

    }

    public void openMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
