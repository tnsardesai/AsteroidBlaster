package com.example.tanmay.asteroidblaster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.Random;

public class Powerup {

    private Bitmap bitmap;

    private int x;
    private int y;

    private int position_x;
    private int position_y;

    private int collisionX;
    private int collisionY;

    private final int SIZE = 150;

    private int maxX;
    private int maxY;

    private long startTime;

    private long time_to_spawn;

    private boolean captured;

    //constructor
    public Powerup(Context context, int screenX, int screenY) {
        //getting boom image from drawable resource
        bitmap = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.heart);
        bitmap = Bitmap.createScaledBitmap(bitmap, SIZE, SIZE, true);

        maxX = screenX - SIZE;
        maxY = screenY - SIZE;

        Random generator = new Random();

        x = -250;
        y = -250;
        position_x = x;
        position_y = y;

        collisionX = position_x + SIZE/2;
        collisionY = position_y + SIZE/2;

        startTime = System.currentTimeMillis();

        captured = false;

        time_to_spawn = generator.nextInt(15000) + 2000;

    }

    public void update(){
        Random generator = new Random();

        long currentTime = System.currentTimeMillis();

        if (currentTime-startTime < time_to_spawn && !captured){

        }
        else if (currentTime-startTime > time_to_spawn && currentTime-startTime < 3000 + time_to_spawn && !captured){
            if (x==-250){
                x = generator.nextInt(maxX);
                position_x = x;
                collisionX = position_x + SIZE/2;
            }
            if (y == -250){
                y = generator.nextInt(maxY);
                position_y = y;
                collisionY = position_y + SIZE/2;
            }
        }
        else if (currentTime-startTime > 3000 +time_to_spawn && currentTime-startTime < 3200 + time_to_spawn && !captured){
            x = -250;
            y = -250;
        }
        else if (currentTime-startTime > 3200 +time_to_spawn && currentTime-startTime < 3700 + time_to_spawn && !captured){
            x = position_x;
            y = position_y;
        }
        else if (currentTime-startTime > 3700 +time_to_spawn && currentTime-startTime < 4000 + time_to_spawn && !captured){
            x = -250;
            y = -250;
        }
        else if (currentTime-startTime > 4000 +time_to_spawn && currentTime-startTime < 4400 + time_to_spawn && !captured){
            x = position_x;
            y = position_y;
        }
        else if (currentTime-startTime > 4400 +time_to_spawn && currentTime-startTime < 4500 + time_to_spawn && !captured){
            x = -250;
            y = -250;
        }
        else if (currentTime-startTime > 4500 + time_to_spawn && currentTime-startTime < 4600 + time_to_spawn && !captured){
            x = position_x;
            y = position_y;
        }
        else if (currentTime-startTime > 4600 +time_to_spawn && currentTime-startTime < 4650 + time_to_spawn && !captured){
            x = -250;
            y = -250;
        }
        else if (currentTime-startTime > 4650 + time_to_spawn && currentTime-startTime < 4700 + time_to_spawn && !captured){
            x = position_x;
            y = position_y;
        }
        else if (currentTime-startTime > 4700 + time_to_spawn || captured){
            x = -250;
            y = -250;
            position_x = x;
            position_y = y;
            collisionY = position_y + SIZE/2;
            collisionY = position_y + SIZE/2;
            startTime = currentTime;
            time_to_spawn = generator.nextInt(15000) + 2000;
            captured = false;
        }


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

    public int getSIZE(){return SIZE; }

    public boolean isCaptured(){
        return captured;
    }

    public void setCaptured(boolean captured){
        this.captured = captured;
    }

    public int getPosition_x(){
        return position_x;
    }

    public int getPosition_y(){
        return position_y;
    }

}
