package com.example.tanmay.asteroidblaster;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    MediaPlayer mp;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startMusic();

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/Grinched.ttf");

        final TextView play = (TextView)findViewById(R.id.play_main_button);
        final TextView leader = (TextView)findViewById(R.id.leadersb_main_button);
        final TextView shop = (TextView)findViewById(R.id.shop_main_button);
        final TextView exit = (TextView)findViewById(R.id.exit_main_button);
        final EditText name = (EditText)findViewById(R.id.edit_name);
        final Button select = (Button)findViewById(R.id.send_name);

        play.setTypeface(custom_font);
        leader.setTypeface(custom_font);
        shop.setTypeface(custom_font);
        exit.setTypeface(custom_font);
        name.setTypeface(custom_font);
        select.setTypeface(custom_font);

    }

    // Called when user clicks game_main_button
    public void playGame(View view){
        final Button edit_button = (Button) findViewById(R.id.send_name);
        final EditText edit_name = (EditText) findViewById(R.id.edit_name);

        final TextView play = (TextView)findViewById(R.id.play_main_button);
        final TextView leader = (TextView)findViewById(R.id.leadersb_main_button);
        final TextView shop = (TextView)findViewById(R.id.shop_main_button);
        final TextView exit = (TextView)findViewById(R.id.exit_main_button);

        edit_button.setVisibility(View.VISIBLE);
        edit_name.setVisibility(View.VISIBLE);
        play.setVisibility(View.INVISIBLE);
        leader.setVisibility(View.INVISIBLE);
        shop.setVisibility(View.INVISIBLE);
        exit.setVisibility(View.INVISIBLE);

        edit_button.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        name = edit_name.getText().toString().toUpperCase();
                        if (name.equals("")){
                            edit_name.setBackgroundColor(Color.RED);
                        }
                        else {

                            Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                            intent.putExtra("USERNAME",name);
                            startActivity(intent);
                        }
                    }
                });


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

    public void startMusic(){
        mp = MediaPlayer.create(getApplicationContext(), R.raw.main);
        mp.setLooping(true);
        mp.start();
    }

    @Override
    public void onBackPressed(){
        //do nothing
    }

    @Override
    public void onStop(){
        super.onStop();
        mp.stop();
        mp.reset();
        mp.release();
    }

    @Override
    public void onResume(){
        super.onResume();
        startMusic();
    }

    // Called when user clicks exit_main_button
    public void exit(View view){

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
