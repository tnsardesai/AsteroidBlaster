package com.example.tanmay.asteroidblaster;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

public class Asteroid {

    private Bitmap bitmap;

    private int x;
    private int y;

    private double speedX =0;
    private double speedY =0;

    private int maxY;
    private int minY;
    private int maxX;
    private int minX;

    private int position;

    private final int ASTEROID_SIZE = 300;

    private int collisionX;
    private int collisionY;

    public Asteroid(Context context, int screenX, int screenY) {

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.asteroid);
        bitmap = Bitmap.createScaledBitmap(bitmap, ASTEROID_SIZE, ASTEROID_SIZE, true);

        minX = 0;
        maxX = screenX - ASTEROID_SIZE;
        minY = 0;
        maxY = screenY - ASTEROID_SIZE;

        Random generator = new Random();

        speedX = 0;
        speedY = 0;

        position = generator.nextInt(4);

        if (position == 0){
            x = minX;
            y = generator.nextInt(maxY);
        }
        else if(position == 1){
            x = maxX;
            y = generator.nextInt(maxY);

        }
        else if(position == 2){
            x = generator.nextInt(maxX);
            y = minY;
        }
        else if(position == 3){
            x = generator.nextInt(maxX);
            y = maxY;
        }


        collisionX = x + ASTEROID_SIZE/2;
        collisionY = y + ASTEROID_SIZE/2;



    }

    public void update() {

        double distance = Math.sqrt(Math.pow((x-maxX/2),2) + Math.pow((y-maxY/2),2));
        speedX = ((x-maxX/2)/distance) ;
        speedY = ((y-maxY/2)/distance) ;

        double speedMag = Math.sqrt(Math.pow((speedX),2) + Math.pow((speedY),2));

        speedX = 5*speedX/speedMag;
        speedY = 5*speedY/speedMag;


        x -=speedX;
        y -=speedY;

        collisionX = x + ASTEROID_SIZE/2;
        collisionY = y + ASTEROID_SIZE/2;

    }

    //adding a setter to x coordinate so that we can change it after collision
    public void place(){
        Random generator = new Random();

        position = generator.nextInt(4);

        if (position == 0){
            x = minX;
            y = generator.nextInt(maxY);
        }
        else if(position == 1){
            x = maxX;
            y = generator.nextInt(maxY);

        }
        else if(position == 2){
            x = generator.nextInt(maxX);
            y = minY;
        }
        else if(position == 3){
            x = generator.nextInt(maxX);
            y = maxY;
        }

        collisionX = x + ASTEROID_SIZE/2;
        collisionY = y + ASTEROID_SIZE/2;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getCollisionX(){
        return collisionX;
    }

    public int getCollisionY(){
        return  collisionY;
    }

    //getters
    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

}
