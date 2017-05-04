package com.example.tanmay.asteroidblaster;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Grinched.ttf");


        final TextView ins = (TextView) findViewById(R.id.instructions);
        final Button how_to = (Button)findViewById(R.id.howto_button);
        final CheckBox sound = (CheckBox) findViewById(R.id.sound_checkbox);

        ins.setTypeface(custom_font);
        how_to.setTypeface(custom_font);
        sound.setTypeface(custom_font);

        String instructions = "The objective of the game is save the planet located at the center of the screen." +
                "The planet is under attack from the asteroids and we have to destroy them from our lunar base." +
                "To shoot down the asteroid hold touch on the screen to move the scope. If the touch is left the scope starts slowing down." +
                "Once the scope comes in contact with the asteroids it is destroyed. If the asteroid comes in contact with the planet people die." +
                "Powerups can be collected along the way to save the planet.";

        ins.setText(instructions);
    }

    public void openMain(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void openHowTo(View view){
        final Button how_to = (Button)findViewById(R.id.howto_button);
        final CheckBox sound = (CheckBox) findViewById(R.id.sound_checkbox);
        final ImageButton ins_close = (ImageButton) findViewById(R.id.instructions_close);
        final TextView ins = (TextView) findViewById(R.id.instructions);

        how_to.setVisibility(View.INVISIBLE);
        sound.setVisibility(View.INVISIBLE);
        ins.setVisibility(View.VISIBLE);
        ins_close.setVisibility(View.VISIBLE);
    }

    public void closeHowTo(View view) {
        final Button how_to = (Button)findViewById(R.id.howto_button);
        final CheckBox sound = (CheckBox) findViewById(R.id.sound_checkbox);
        final ImageButton ins_close = (ImageButton) findViewById(R.id.instructions_close);
        final TextView ins = (TextView) findViewById(R.id.instructions);

        how_to.setVisibility(View.VISIBLE);
        sound.setVisibility(View.VISIBLE);
        ins.setVisibility(View.INVISIBLE);
        ins_close.setVisibility(View.INVISIBLE);

    }
}
