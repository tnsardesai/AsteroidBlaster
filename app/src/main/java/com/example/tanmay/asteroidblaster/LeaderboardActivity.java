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

public class LeaderboardActivity extends AppCompatActivity {


    private static final String DATABASE_NAME = "AsteroidBlaster.db";

    private static final String TABLE_HIGH = "highscores";

    private static final String HIGH_KEY_ID = "id";
    private static final String HIGH_SCORE = "score";

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
                HIGH_SCORE
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
        TextView high1 = (TextView)findViewById(R.id.High_score_1);
        TextView high2 = (TextView)findViewById(R.id.High_score_2);
        TextView high3 = (TextView)findViewById(R.id.High_score_3);
        TextView high4 = (TextView)findViewById(R.id.High_score_4);
        TextView high5 = (TextView)findViewById(R.id.High_score_5);

        high1.setTypeface(custom_font);
        high2.setTypeface(custom_font);
        high3.setTypeface(custom_font);
        high4.setTypeface(custom_font);
        high5.setTypeface(custom_font);

        cursor.moveToNext();
        high1.setText(String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(HIGH_SCORE))));
        cursor.moveToNext();
        high2.setText(String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(HIGH_SCORE))));
        cursor.moveToNext();
        high3.setText(String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(HIGH_SCORE))));
        cursor.moveToNext();
        high4.setText(String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(HIGH_SCORE))));
        cursor.moveToNext();
        high5.setText(String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow(HIGH_SCORE))));

        cursor.close();

    }

    public void openMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
