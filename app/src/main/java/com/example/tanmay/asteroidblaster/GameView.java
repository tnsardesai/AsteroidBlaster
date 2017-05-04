package com.example.tanmay.asteroidblaster;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
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


    private static final String TABLE_COIN = "coin";
    private static final String COIN_ID = "id";
    private static final String COIN_AMOUNT = "coins";

    private int score_recorded;


    private static final String TABLE_SOUND = "sound";
    private static final String SOUND_ID = "id";
    private static final String SOUND_STATE = "state";


    private static final String TABLE_SHOP = "shop";
    private static final String SHOP_KEY_ID = "ID";
    private static final String SHOP_ITEM = "item";
    private static final String SHOP_SELECTED = "selected";
    private static final String SHOP_BOUGHT = "bought";

    String name;

    public GameView (Context context, int screenX, int screenY, LinearLayout linearLayout,String name) {
        super(context);

        this.context = context;
        this.name = name;
        this.screenX = screenX;
        this.screenY = screenY;

        surfaceHolder = getHolder();
        paint = new Paint();

        DbHelper mDbHelper = new DbHelper(context);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();


        String[] projection = {
                SHOP_KEY_ID,
                SHOP_ITEM,
                SHOP_SELECTED,
                SHOP_BOUGHT
        };

        String selection = SHOP_SELECTED + " = ?";
        String[] selectionArgs = {"1"};

        Cursor cursor = db.query(
                TABLE_SHOP,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

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
            Bitmap asteroid_bit = BitmapFactory.decodeResource
                    (context.getResources(), R.drawable.default_asteroid);
            asteroid_bit = Bitmap.createScaledBitmap(asteroid_bit, (int)dipToPixels(context,100),(int)dipToPixels(context, 30), true);
            asteroids[i].setBitmap(asteroid_bit);

        }

        planet = new Planet(context,screenX,screenY);

        cursor.moveToNext();
        if(cursor.getString(cursor.getColumnIndexOrThrow(SHOP_ITEM)).equals("default")){
            Bitmap planet_bit = BitmapFactory.decodeResource
                    (context.getResources(), R.drawable.default_planet);
            planet_bit = Bitmap.createScaledBitmap(planet_bit, (int) dipToPixels(context,120),(int)dipToPixels (context,120), true);
            planet.setBitmap(planet_bit);

            for(int i=0; i<asteroidCount; i++){
                Bitmap asteroid_bit = BitmapFactory.decodeResource
                        (context.getResources(), R.drawable.default_asteroid);
                asteroid_bit = Bitmap.createScaledBitmap(asteroid_bit, (int)dipToPixels(context,100),(int)dipToPixels(context, 100), true);
                asteroids[i].setBitmap(asteroid_bit);
            }

        }
        else if (cursor.getString(cursor.getColumnIndexOrThrow(SHOP_ITEM)).equals("cookiemonster")){
            Bitmap planet_bit = BitmapFactory.decodeResource
                    (context.getResources(), R.drawable.cookie_planet);
            planet_bit = Bitmap.createScaledBitmap(planet_bit, (int) dipToPixels(context,120),(int)dipToPixels (context,120), true);
            planet.setBitmap(planet_bit);

            for(int i=0; i<asteroidCount; i++){
                Bitmap asteroid_bit = BitmapFactory.decodeResource
                        (context.getResources(), R.drawable.cookie_asteroid);
                asteroid_bit = Bitmap.createScaledBitmap(asteroid_bit, (int)dipToPixels(context,100),(int)dipToPixels(context, 100), true);
                asteroids[i].setBitmap(asteroid_bit);
            }
        }
        else if (cursor.getString(cursor.getColumnIndexOrThrow(SHOP_ITEM)).equals("breakingbad")){
            Bitmap planet_bit = BitmapFactory.decodeResource
                    (context.getResources(), R.drawable.breakingbad_planet);
            planet_bit = Bitmap.createScaledBitmap(planet_bit, (int) dipToPixels(context,120),(int)dipToPixels (context,120), true);
            planet.setBitmap(planet_bit);

            for(int i=0; i<asteroidCount; i++){
                Bitmap asteroid_bit = BitmapFactory.decodeResource
                        (context.getResources(), R.drawable.breakingbad_asteroid);
                asteroid_bit = Bitmap.createScaledBitmap(asteroid_bit, (int)dipToPixels(context,100),(int)dipToPixels(context, 100), true);
                asteroids[i].setBitmap(asteroid_bit);
            }
        }
        else if (cursor.getString(cursor.getColumnIndexOrThrow(SHOP_ITEM)).equals("starwars")){
            Bitmap planet_bit = BitmapFactory.decodeResource
                    (context.getResources(), R.drawable.starwars_planet);
            planet_bit = Bitmap.createScaledBitmap(planet_bit, (int) dipToPixels(context,120),(int)dipToPixels (context,120), true);
            planet.setBitmap(planet_bit);

            for(int i=0; i<asteroidCount; i++){
                Bitmap asteroid_bit = BitmapFactory.decodeResource
                        (context.getResources(), R.drawable.starwars_asteroid);
                asteroid_bit = Bitmap.createScaledBitmap(asteroid_bit, (int)dipToPixels(context,100),(int)dipToPixels(context, 100), true);
                asteroids[i].setBitmap(asteroid_bit);
            }
        }
        else if (cursor.getString(cursor.getColumnIndexOrThrow(SHOP_ITEM)).equals("khalili")){
            Bitmap planet_bit = BitmapFactory.decodeResource
                    (context.getResources(), R.drawable.khalili_planet);
            planet_bit = Bitmap.createScaledBitmap(planet_bit, (int) dipToPixels(context,120),(int)dipToPixels (context,120), true);
            planet.setBitmap(planet_bit);

            for(int i=0; i<asteroidCount; i++){
                Bitmap asteroid_bit = BitmapFactory.decodeResource
                        (context.getResources(), R.drawable.khalili_asteroid);
                asteroid_bit = Bitmap.createScaledBitmap(asteroid_bit, (int)dipToPixels(context,100),(int)dipToPixels(context, 100), true);
                asteroids[i].setBitmap(asteroid_bit);
            }
        }

        scope = new Scope(context,screenX,screenY);

        blast = new Blast(context);

        heart = new Powerup(context,screenX,screenY);

        coin = new Powerup(context,screenX,screenY);
        coin_bitmap = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.coin);
        coin_bitmap = Bitmap.createScaledBitmap(coin_bitmap, (int)dipToPixels(context,60),(int)dipToPixels(context, 60), true);
        coin.setBitmap(coin_bitmap);

        plus5 = new Powerup(context,screenX,screenY);
        plus5_bitmap = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.plus5);
        plus5_bitmap = Bitmap.createScaledBitmap(plus5_bitmap, (int)dipToPixels(context,60), (int)dipToPixels(context, 60), true);
        plus5.setBitmap(plus5_bitmap);

        powerup = new Blast(context);
        powerup_bitmap = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.powerup);
        powerup_bitmap = Bitmap.createScaledBitmap(powerup_bitmap,(int)dipToPixels(context, 100), (int)dipToPixels(context, 100), true);
        powerup.setBitmap(powerup_bitmap);

        pause = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.pause);
        pause = Bitmap.createScaledBitmap(pause, (int)dipToPixels(context, 60),(int)dipToPixels(context,60), true);
        play = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.play);
        play = Bitmap.createScaledBitmap(play, (int)dipToPixels(context, 60), (int)dipToPixels(context, 60), true);
        restart = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.restart);
        restart = Bitmap.createScaledBitmap(restart, (int)dipToPixels(context, 60),(int)dipToPixels(context, 60), true);
        exit = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.exit);
        exit = Bitmap.createScaledBitmap(exit, (int)dipToPixels(context, 60), (int)dipToPixels(context, 60), true);

        scope.setBitmap(Bitmap.createScaledBitmap(scope.getBitmap(),(int)dipToPixels(context, 60), (int)dipToPixels(context, 60), true));

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

    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
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

                if (touchX >= screenX-(int)dipToPixels(context, 60) && touchY >= screenY-(int)dipToPixels(context, 80)){
                    if (playing) {
                        playing = false;
                    }
                    else {
                        playing = true;
                    }
                }
                else if (touchX >= screenX-2*(int)dipToPixels(context, 65) && touchX <= screenX-(int)dipToPixels(context, 65) && touchY >= screenY-(int)dipToPixels(context, 80) && !playing){

                    Intent intent = new Intent(context, GameActivity.class);
                    intent.putExtra("USERNAME",name);
                    context.startActivity(intent);
                    android.os.Process.killProcess(android.os.Process.myPid());

                }
                else if (touchX >= screenX-3*(int)dipToPixels(context, 65) && touchX <= screenX-2*(int)dipToPixels(context, 65) && touchY >= screenY-(int)dipToPixels(context, 80) && !playing){

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


        if (distance_scope_heart < (int)dipToPixels(context, 70)){
            heart.setCaptured(true);
            status.setHealth(status.getHealth() + 1);
            status.update_health();
            powerup.setX(heart.getPosition_x() - 125);
            powerup.setY(heart.getPosition_y() - 125);
        }

        if (distance_scope_coin < (int)dipToPixels(context, 70)){
            coin.setCaptured(true);
            status.update_coins();
            powerup.setX(coin.getPosition_x() - 125);
            powerup.setY(coin.getPosition_y() - 125);
        }

        if (distance_scope_plus5 < (int)dipToPixels(context, 70)){
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

            if(distance_scope_asteroid < (int)dipToPixels(context, 50)){
                blast.setX(asteroids[i].getX());
                blast.setY(asteroids[i].getY());
                status.setScore(status.getScore() + 1);
                status.update_score();

                asteroids[i].place();
            }

            double distance_asteroid_planet = Math.sqrt(Math.pow((double)(
                    asteroids[i].getCollisionX() - planet.getCollisionX()),2) +
                    Math.pow((double)(asteroids[i].getCollisionY() - planet.getCollisionY()),2));

            if(distance_asteroid_planet < (int)dipToPixels(context, 100) ){
                blast.setX(asteroids[i].getX());
                blast.setY(asteroids[i].getY());
                MediaPlayer mp = MediaPlayer.create(context, R.raw.damage);

                mp.start();

                DbHelper mDbHelper = new DbHelper(context);

                SQLiteDatabase db = mDbHelper.getWritableDatabase();

                String[] projection_sound = {
                        SOUND_STATE
                };

                Cursor cursor_sound = db.query(
                        TABLE_SOUND,
                        projection_sound,
                        null,
                        null,
                        null,
                        null,
                        null
                );

                cursor_sound.moveToNext();
                if(cursor_sound.getLong(cursor_sound.getColumnIndexOrThrow(SOUND_STATE)) == 0){
                    mp.setVolume(0,0);
                }
                cursor_sound.close();

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

                        ContentValues values = new ContentValues();
                        values.put(HIGH_SCORE, status.getScore());
                        values.put(HIGH_NAME, name);

                        db.insert(TABLE_HIGH, null, values);

                        String[] projection = {
                                COIN_AMOUNT
                        };

                        Cursor cursor = db.query(
                                TABLE_COIN,
                                projection,
                                null,
                                null,
                                null,
                                null,
                                null
                        );

                        cursor.moveToNext();
                        status.setCoins(status.getCoins() + cursor.getInt(cursor.getColumnIndexOrThrow(COIN_AMOUNT)));

                        ContentValues coin_value = new ContentValues();
                        coin_value.put(COIN_AMOUNT, status.getCoins());
                        db.update(TABLE_COIN,coin_value,null,null);

                        cursor.close();

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
                canvas.drawBitmap(pause,screenX-(int)dipToPixels(context, 60),screenY-(int)dipToPixels(context, 80),paint);
            }
            else{
                canvas.drawBitmap(play,screenX-(int)dipToPixels(context, 60),screenY-(int)dipToPixels(context, 80),paint);
                canvas.drawBitmap(restart,screenX-(int)dipToPixels(context, 125),screenY-(int)dipToPixels(context, 80),paint);
                canvas.drawBitmap(exit,screenX - (int)dipToPixels(context, 190),screenY-(int)dipToPixels(context, 80),paint);
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

