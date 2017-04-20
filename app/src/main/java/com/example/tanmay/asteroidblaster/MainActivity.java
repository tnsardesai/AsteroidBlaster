package com.example.tanmay.asteroidblaster;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.main);
        mp.setLooping(true);
        mp.start();
    }

    // Called when user clicks game_main_button
    public void playGame(View view){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    // Called when user clicks leaderboard_main_button
    public void openLeadersboard(View view){
        Intent intent = new Intent(this, LeaderboardActivity.class);
        startActivity(intent);
    }
    // Called when user clicks shop_main_button
    public void openShop(View view){
        Intent intent = new Intent(this, ShopActivity.class);
        startActivity(intent);
    }

    // Called when user clicks settings_main_button
    public void openSettings(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        //do nothing
    }

    // Called when user clicks exit_main_button
    public void exit(View view){
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
