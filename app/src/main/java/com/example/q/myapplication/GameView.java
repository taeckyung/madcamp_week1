package com.example.q.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.MainThread;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import static com.example.q.myapplication.mainThread.canvas;
import static java.lang.Thread.sleep;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private mainThread thread;
    private ppBall ball;
    private CharacterSprite plateA;
    private CharacterSprite loseA;
    private Paint paint;
    private boolean onPlateA;
    private boolean gameStarted;
    private int playerLose;
    private int screenWidth;
    private int screenHeight;
    private int averageFPS;
    private long startTime;
    private long timeElapsed;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);

        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        thread = new mainThread(getHolder(), this);

        ball = new ppBall(BitmapFactory.decodeResource(getResources(),R.drawable.ball));
        plateA = new CharacterSprite(BitmapFactory.decodeResource(getResources(),R.drawable.plate));

        loseA = new CharacterSprite(BitmapFactory.decodeResource(getResources(),R.drawable.player_one_lose));
        loseA.setXY(screenWidth/2, screenHeight/2);

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(60);

        setFocusable(true);
    }

    public void init_game() {
        ball.setXY(screenWidth/2, screenHeight/2);
        plateA.setXY(screenWidth/2, screenHeight - 200);
        onPlateA = false;
        gameStarted = false;
        averageFPS = 0;
        playerLose = 0;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        init_game();
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update(double _averageFPS) {
        if (gameStarted) {
            averageFPS = (int)_averageFPS;
            ball.update(plateA);
            int temp;
            if ((temp = ball.gameOver()) != 0) {
                init_game();
                playerLose = temp;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            if (playerLose == 0) {
                ball.draw(canvas);
                plateA.draw(canvas);
                int fps = gameStarted ? averageFPS : 0;
                timeElapsed = gameStarted ? (System.nanoTime() - startTime) / 1000000000 : 0;
                canvas.drawText("FPS: "+Integer.toString(fps),200, screenHeight/2, paint);
                canvas.drawText("Time: " + Long.toString(timeElapsed), screenWidth - 400, screenHeight / 2, paint);
            }
            else if (playerLose == 1) {
                //canvas.drawText("PLAYER\nTWO\nWIN!\n\n"+Long.toString(timeElapsed)+" sec", screenWidth/2, screenHeight/2,paint);
                loseA.draw(canvas);
            }
        }
    }

    // Return false for handling the touch input only in this function.
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (playerLose != 0) {
            playerLose = 0;
            return false;
        }
        else if (!gameStarted) {
            gameStarted = true;
            startTime = System.nanoTime();
            return false;
        }

        int keyAction = event.getAction();
        int eventX = (int)event.getX();
        int eventY = (int)event.getY();

        switch (keyAction) {
            case MotionEvent.ACTION_DOWN:
                if (plateA.isInside(eventX, eventY)) {
                    onPlateA = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (onPlateA) {
                    plateA.setXY(eventX, eventY);
                }
                break;
            case MotionEvent.ACTION_UP:
                onPlateA = false;
                break;
        }
        return true;
    }

}
