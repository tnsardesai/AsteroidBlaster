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

    private final int PLANET_HEIGHT = 400;
    private final int PLANET_WIDTH = 400;



    //constructor
    public Planet(Context context, int screenX, int screenY) {
        //getting boom image from drawable resource
        bitmap = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.earth);
        bitmap = Bitmap.createScaledBitmap(bitmap, PLANET_HEIGHT, PLANET_WIDTH, true);

        x = screenX/2 - bitmap.getWidth()/2;
        y = screenY/2 - bitmap.getHeight()/2;

        collisionX = screenX/2;
        collisionY = screenY/2;

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


    public int getCollisionX(){
        return collisionX;
    }

    public int getCollisionY(){
        return  collisionY;
    }




}
