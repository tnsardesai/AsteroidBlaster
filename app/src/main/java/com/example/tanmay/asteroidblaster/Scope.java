package com.example.tanmay.asteroidblaster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


public class Scope {

    private Bitmap bitmap;

    private int x;
    private int y;

    private double speedX =0;
    private double speedY =0;


    private boolean moving;

    private int maxY;
    private int minY;
    private int maxX;
    private int minX;

    private int touchX;
    private int touchY;

    private final int MIN_SPEED = 0;
    private final int MAX_SPEED = 20;

    private final int SCOPE_SIZE = 200;

    public Scope(Context context, int screenX, int screenY){
        x = screenX/2 - SCOPE_SIZE/2;
        y = screenY/2 - SCOPE_SIZE/2;
        speedX = 0;
        speedY = 0;

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.game_scope);
        bitmap = Bitmap.createScaledBitmap(bitmap,SCOPE_SIZE,SCOPE_SIZE,true);

        minX = 0;
        maxX = screenX-SCOPE_SIZE;
        minY = 0;
        maxY = screenY-SCOPE_SIZE;

        moving = false;
    }

    public void setMoving(int X, int Y){
        moving = true;
        touchX = X;
        touchY = Y;
    }

    public void stopMoving() {
        moving = false;
    }

    public void update(){
        if(moving){
            double distance = Math.sqrt(Math.pow((double)(touchX - x),2) + Math.pow((double)(touchY - y),2));
            if (distance!=0){
                speedX = ((touchX-x - SCOPE_SIZE/2)/distance) ;
                speedY = ((touchY-y - SCOPE_SIZE/2)/distance) ;

                double speedMag = Math.sqrt(Math.pow((double)(speedX),2) + Math.pow((double)(speedY),2));

                speedX = 20*speedX/speedMag;
                speedY = 20*speedY/speedMag;

            }
        }
        else {
            if (speedX <=0.05 && speedX >= -0.05){
                speedX=0;
            }
            else if(speedX<-0.05){
                speedX += 0.1;
            }
            else if (speedX>0.05){
                speedX -= 0.1;
            }
            if (speedY <=0.05 && speedY >= -0.05){
                speedY=0;
            }
            else if(speedY<-0.05){
                speedY += 0.1;
            }
            else if (speedY>0.05){
                speedY -= 0.1;
            }

            Log.d("Speed Not moving", "speed x: " + String.valueOf(speedX) + "speed y: " + String.valueOf(speedY));
        }

        if (speedX >= MAX_SPEED){
            speedX = MAX_SPEED;
        }

        if (speedY >= MAX_SPEED){
            speedY = MAX_SPEED;
        }

        x += speedX;
        y += speedY;

        if (y<minY){
            y=minY;
        }
        if (y>maxY){
            y=maxY;
        }
        if (x<minX){
            x=minX;
        }
        if (x>maxX){
            x=maxX;
        }

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

    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

}
