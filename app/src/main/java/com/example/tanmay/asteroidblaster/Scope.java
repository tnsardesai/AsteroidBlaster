package com.example.tanmay.asteroidblaster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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

    private final int SCOPE_SIZE = 200;

    private int collisionX;
    private int collisionY;

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


        collisionX = x + SCOPE_SIZE/2;
        collisionY = y + SCOPE_SIZE/2;
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

                double speedMag = Math.sqrt(Math.pow((speedX),2) + Math.pow((speedY),2));

                speedX = 25*speedX/speedMag;
                speedY = 25*speedY/speedMag;

            }
        }
        else {
            if (speedX <=0.2 && speedX >= -0.02){
                speedX=0;
            }
            else if(speedX<-0.2){
                speedX += 0.4;
            }
            else if (speedX>0.2){
                speedX -= 0.4;
            }
            if (speedY <=0.2 && speedY >= -0.2){
                speedY=0;
            }
            else if(speedY<-0.2){
                speedY += 0.4;
            }
            else if (speedY>0.2){
                speedY -= 0.4;
            }

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

        collisionX = x + SCOPE_SIZE/2;
        collisionY = y + SCOPE_SIZE/2;

    }

    //adding a setter to x coordinate so that we can change it after collision
    public void setX(int x){
        this.x = x;
    }

    public int getCollisionX(){
        return collisionX;
    }

    public int getCollisionY(){
        return  collisionY;
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
