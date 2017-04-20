package com.example.tanmay.asteroidblaster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Planet{

    private Bitmap bitmap;

    private int x;
    private int y;

    private int collisionX;
    private int collisionY;

    //constructor
    public Planet(Context context, int screenX, int screenY) {
        //getting boom image from drawable resource
        bitmap = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.planet);

        x = screenX/2 - bitmap.getWidth()/2;
        y = screenY/2 - bitmap.getHeight()/2;

    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    //getters
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


}
