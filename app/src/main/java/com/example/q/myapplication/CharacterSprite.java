package com.example.q.myapplication;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.RelativeLayout;

import java.util.Random;

public class CharacterSprite {
    private Bitmap image;
    private int x, y;
    private int xVelocity = 75;
    private int yVelocity = 75;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public CharacterSprite(Bitmap bmp) {
        image = bmp;
        x = screenWidth / 2;
        y = screenHeight / 2;

        Random generator = new Random();
        int randX = generator.nextFloat() > 0.5 ? -1 : 1;
        int randY = generator.nextFloat() > 0.5 ? -1 : 1;
        xVelocity *= randX;
        yVelocity *= randY;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    public void update() {
        x += xVelocity;
        y += yVelocity;
        if ((x > screenWidth - image.getWidth()) || (x < 0)) {
            xVelocity *= -1;
        }
        if ((y > screenHeight - image.getHeight()) || (y < 0)) {
            yVelocity *= -1;
        }
    }
}
