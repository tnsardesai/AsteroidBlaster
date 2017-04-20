package com.example.tanmay.asteroidblaster;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.RunnableFuture;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class GameView extends SurfaceView implements Runnable{

    volatile boolean playing;

    private Thread gameThread = null;

    private Scope scope;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private ArrayList<Star> stars = new ArrayList<Star>();

    private Asteroid[] asteroids;

    private int asteroidCount = 4;

    //defining a boom object to display blast
    private Blast blast;

    private Planet planet;

    private Status status;

    Context context;


    public GameView (Context context, int screenX, int screenY, LinearLayout linearLayout) {
        super(context);

        this.context = context;

        surfaceHolder = getHolder();
        paint = new Paint();

        //adding 100 stars you may increase the number
        int starNums = 100;
        for (int i = 0; i < starNums; i++) {
            Star s  = new Star(screenX, screenY);
            stars.add(s);
        }

        //initializing enemy object array
        asteroids = new Asteroid[asteroidCount];
        for(int i=0; i<asteroidCount; i++){
            asteroids[i] = new Asteroid(context, screenX, screenY);

        }

        scope = new Scope(context,screenX,screenY);

        blast = new Blast(context);

        planet = new Planet(context,screenX,screenY);

        status = new Status(context,linearLayout);


    }

    @Override
    public void run(){
        while (playing){
            update();

            draw();

            control();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                //When the user presses on the screen
                //we will do something here
                scope.stopMoving();
                break;
            case MotionEvent.ACTION_DOWN:
                //When the user releases the screen
                //do something here
                int touchX = Math.round(motionEvent.getX());
                int touchY = Math.round(motionEvent.getY());
                scope.setMoving(touchX,touchY);
                break;
        }
        return true;
    }

    private void update() {
        scope.update();

        blast.setX(-500);
        blast.setY(-500);


        //Updating the stars with player speed
        for (Star s : stars) {
            s.update();
        }

        //updating the enemy coordinate with respect to player speed
        for(int i=0; i<asteroidCount; i++){
            asteroids[i].update();

            double distance_scope_asteroid = Math.sqrt(Math.pow((double)(
                    scope.getCollisionX() - asteroids[i].getCollisionX()),2) +
                    Math.pow((double)(scope.getCollisionY() - asteroids[i].getCollisionY()),2));

            if(distance_scope_asteroid < 100){
                blast.setX(asteroids[i].getX());
                blast.setY(asteroids[i].getY());
                //score++;
                //score_value.setText(String.valueOf(score));
                status.update_score();

                asteroids[i].place();
            }

            double distance_asteroid_planet = Math.sqrt(Math.pow((double)(
                    asteroids[i].getCollisionX() - planet.getCollisionX()),2) +
                    Math.pow((double)(asteroids[i].getCollisionY() - planet.getCollisionY()),2));

            if(distance_asteroid_planet < 300 ){
                blast.setX(asteroids[i].getX());
                blast.setY(asteroids[i].getY());
                MediaPlayer mp = MediaPlayer.create(context, R.raw.damage);;
                //if(mp!=null) {
                //    mp.reset();
                    //mp.prepare();
                //    mp.release();
                //    mp = null;
                //}

                mp.start();

                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.stop();
                        if (mp != null) {
                            mp.release();
                        }

                    }
                });

                status.update_health();

                if (status.getHealth() == 1){
                    android.os.Process.killProcess(android.os.Process.myPid());
                }

                asteroids[i].place();
            }

        }
    }



    private void draw() {
        if(surfaceHolder.getSurface().isValid()){

            canvas = surfaceHolder.lockCanvas();

            canvas.drawColor(Color.BLACK);

            //setting the paint color to white to draw the stars
            paint.setColor(Color.WHITE);

            //drawing all stars
            for (Star s : stars) {
                paint.setStrokeWidth(s.getStarWidth());
                canvas.drawPoint(s.getX(), s.getY(), paint);
            }

            //drawing the enemies
            for (int i = 0; i < asteroidCount; i++) {
                canvas.drawBitmap(
                        asteroids[i].getBitmap(),
                        asteroids[i].getX(),
                        asteroids[i].getY(),
                        paint
                );
            }

            //drawing boom image
            canvas.drawBitmap(
                    blast.getBitmap(),
                    blast.getX(),
                    blast.getY(),
                    paint
            );

            canvas.drawBitmap(
                    planet.getBitmap(),
                    planet.getX(),
                    planet.getY(),
                    paint
            );

            canvas.drawBitmap(scope.getBitmap(),scope.getX(),scope.getY(),paint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        //when the game is paused
        //setting the variable to false
        playing = false;
        try {
            //stopping the thread
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        //when the game is resumed
        //starting the thread again
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}

