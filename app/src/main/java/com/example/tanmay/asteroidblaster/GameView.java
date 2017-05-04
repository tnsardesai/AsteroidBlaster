package com.example.tanmay.asteroidblaster;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;

import java.util.ArrayList;

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

    private Blast powerup;
    private Bitmap powerup_bitmap;

    private Planet planet;

    private Powerup heart;

    private Powerup coin;
    private Bitmap coin_bitmap;

    private Powerup plus5;
    private Bitmap plus5_bitmap;

    private Bitmap pause;
    private Bitmap play;
    private Bitmap restart;
    private Bitmap exit;

    private int screenX;
    private int screenY;

    private Status status;

    Context context;

    private static final String DATABASE_NAME = "AsteroidBlaster.db";

    private static final String TABLE_HIGH = "highscores";

    private static final String HIGH_KEY_ID = "id";
    private static final String HIGH_SCORE = "score";
    private static final String HIGH_NAME = "name";

    private int score_recorded;

    String name;

    public GameView (Context context, int screenX, int screenY, LinearLayout linearLayout,String name) {
        super(context);

        this.context = context;
        this.name = name;
        this.screenX = screenX;
        this.screenY = screenY;

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

        heart = new Powerup(context,screenX,screenY);

        coin = new Powerup(context,screenX,screenY);
        coin_bitmap = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.coin);
        coin_bitmap = Bitmap.createScaledBitmap(coin_bitmap, coin.getSIZE(), coin.getSIZE(), true);
        coin.setBitmap(coin_bitmap);

        plus5 = new Powerup(context,screenX,screenY);
        plus5_bitmap = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.plus5);
        plus5_bitmap = Bitmap.createScaledBitmap(plus5_bitmap, plus5.getSIZE(), plus5.getSIZE(), true);
        plus5.setBitmap(plus5_bitmap);

        powerup = new Blast(context);
        powerup_bitmap = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.powerup);
        powerup_bitmap = Bitmap.createScaledBitmap(powerup_bitmap, 400, 400, true);
        powerup.setBitmap(powerup_bitmap);

        pause = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.pause);
        pause = Bitmap.createScaledBitmap(pause, 200, 200, true);
        play = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.play);
        play = Bitmap.createScaledBitmap(play, 200, 200, true);
        restart = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.restart);
        restart = Bitmap.createScaledBitmap(restart, 200, 200, true);
        exit = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.exit);
        exit = Bitmap.createScaledBitmap(exit, 200, 200, true);


        status = new Status(context,linearLayout);

        score_recorded = 0;

    }

    @Override
    public void run(){
        while (true){
            if(playing) {
                update();

                draw();

                control();
            }
            else {
                draw();
            }
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

                if (touchX >= screenX-200 && touchY >= screenY-300){
                    if (playing) {
                        playing = false;
                    }
                    else {
                        playing = true;
                    }
                }
                else if (touchX >= screenX-450 && touchX <= screenX-250 && touchY >= screenY-300 && !playing){

                    Intent intent = new Intent(context, GameActivity.class);
                    intent.putExtra("USERNAME",name);
                    context.startActivity(intent);
                    android.os.Process.killProcess(android.os.Process.myPid());

                }
                else if (touchX >= screenX-700 && touchX <= screenX-500 && touchY >= screenY-300 && !playing){

                    android.os.Process.killProcess(android.os.Process.myPid());

                }
                else {
                    scope.setMoving(touchX,touchY);
                }
                break;
        }
        return true;
    }

    private void update() {
        scope.update();
        heart.update();
        coin.update();
        plus5.update();

        blast.setX(-500);
        blast.setY(-500);

        powerup.setX(-500);
        powerup.setY(-500);


        //Updating the stars with player speed
        for (Star s : stars) {
            s.update();
        }


        double distance_scope_heart = Math.sqrt(Math.pow((double)(
                scope.getCollisionX() - heart.getCollisionX()),2) +
                Math.pow((double)(scope.getCollisionY() - heart.getCollisionY()),2));

        double distance_scope_coin = Math.sqrt(Math.pow((double)(
                scope.getCollisionX() - coin.getCollisionX()),2) +
                Math.pow((double)(scope.getCollisionY() - coin.getCollisionY()),2));

        double distance_scope_plus5 = Math.sqrt(Math.pow((double)(
                scope.getCollisionX() - plus5.getCollisionX()),2) +
                Math.pow((double)(scope.getCollisionY() - plus5.getCollisionY()),2));


        if (distance_scope_heart < 150){
            heart.setCaptured(true);
            status.setHealth(status.getHealth() + 1);
            status.update_health();
            powerup.setX(heart.getPosition_x() - 125);
            powerup.setY(heart.getPosition_y() - 125);
        }

        if (distance_scope_coin < 150){
            coin.setCaptured(true);
            status.update_coins();
            powerup.setX(coin.getPosition_x() - 125);
            powerup.setY(coin.getPosition_y() - 125);
        }

        if (distance_scope_plus5 < 150){
            plus5.setCaptured(true);
            status.setScore(status.getScore() + 5);
            status.update_score();
            powerup.setX(plus5.getPosition_x() - 125);
            powerup.setY(plus5.getPosition_y() - 125);
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
                status.setScore(status.getScore() + 1);
                status.update_score();

                asteroids[i].place();
            }

            double distance_asteroid_planet = Math.sqrt(Math.pow((double)(
                    asteroids[i].getCollisionX() - planet.getCollisionX()),2) +
                    Math.pow((double)(asteroids[i].getCollisionY() - planet.getCollisionY()),2));

            if(distance_asteroid_planet < 300 ){
                blast.setX(asteroids[i].getX());
                blast.setY(asteroids[i].getY());
                MediaPlayer mp = MediaPlayer.create(context, R.raw.damage);

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

                status.setHealth(status.getHealth() - 1);
                status.update_health();

                if (status.getHealth() <= 0){

                    if(score_recorded == 0) {
                        score_recorded = 1;
                        DbHelper mDbHelper = new DbHelper(context);

                        SQLiteDatabase db = mDbHelper.getWritableDatabase();

                        ContentValues values = new ContentValues();
                        values.put(HIGH_SCORE, status.getScore());
                        values.put(HIGH_NAME, name);

                        db.insert(TABLE_HIGH, null, values);

                    }

                    Intent intent = new Intent(context, LeaderboardActivity.class);
                    context.startActivity(intent);

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
                    powerup.getBitmap(),
                    powerup.getX(),
                    powerup.getY(),
                    paint
            );

            canvas.drawBitmap(
                    planet.getBitmap(),
                    planet.getX(),
                    planet.getY(),
                    paint
            );

            canvas.drawBitmap(heart.getBitmap(),heart.getX(),heart.getY(),paint);

            canvas.drawBitmap(coin_bitmap,coin.getX(),coin.getY(),paint);

            canvas.drawBitmap(plus5_bitmap,plus5.getX(),plus5.getY(),paint);

            canvas.drawBitmap(scope.getBitmap(),scope.getX(),scope.getY(),paint);

            if (playing){
                canvas.drawBitmap(pause,screenX-200,screenY-300,paint);
            }
            else{
                canvas.drawBitmap(play,screenX-200,screenY-300,paint);
                canvas.drawBitmap(restart,screenX-450,screenY-300,paint);
                canvas.drawBitmap(exit,screenX-700,screenY-300,paint);
            }

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

