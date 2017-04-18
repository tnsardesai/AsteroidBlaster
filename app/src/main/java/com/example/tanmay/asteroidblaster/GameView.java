package com.example.tanmay.asteroidblaster;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable{

    volatile boolean playing;

    private Thread gameThread = null;

    private Scope scope;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;


    public GameView (Context context,int height,int width) {
        super(context);

        scope = new Scope(context,height,width);

        surfaceHolder = getHolder();
        paint = new Paint();
    }

    @Override
    public void run(){
        while (playing){
            update();

            draw();

            control();
        }

    }

    private void update() {
        scope.update();
    }

    private void draw() {
        if(surfaceHolder.getSurface().isValid()){

            canvas = surfaceHolder.lockCanvas();

            canvas.drawColor(Color.BLACK);

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

