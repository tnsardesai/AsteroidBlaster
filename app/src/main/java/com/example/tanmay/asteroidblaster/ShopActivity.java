package com.example.tanmay.asteroidblaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shop);

    }

    @Override
    public void onBackPressed(){
        //do nothing
    }
}
