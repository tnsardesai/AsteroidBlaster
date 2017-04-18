package com.example.tanmay.asteroidblaster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

/**
 * Created by tanmay on 4/18/17.
 */

public class Scope {

    private Bitmap bitmap;

    private int x;
    private int y;

    private int speed =0;

    public Scope(Context context, int height, int width){
        x = width/2 - 100;
        y = height/2 - 100;
        speed = 1;

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.game_scope);
        bitmap = Bitmap.createScaledBitmap(bitmap,200,200,true);
    }

    public void update(){
        x++;
        y--;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }


}
