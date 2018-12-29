package com.example.q.myapplication;

import android.content.res.Resources;
import android.graphics.Bitmap;

import java.util.Random;

public class ppBall extends CharacterSprite {
    private int xVelocity = 75;
    private int yVelocity = 75;
    private int radius = image.getWidth()/2;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private boolean hadCollideWall;

    public ppBall(Bitmap bmp) {
        super(bmp);
        Random generator = new Random();
        int randX = generator.nextFloat() > 0.5 ? -1 : 1;
        int randY = generator.nextFloat() > 0.5 ? -1 : 1;
        xVelocity *= randX;
        yVelocity *= randY;
    }

    public void update(CharacterSprite plateA) {
        x += xVelocity;
        y += yVelocity;

        x = x > 0 ? x : 0;
        x = x < screenWidth ? x : screenWidth;
        y = y > 0 ? y : 0;
        y = y < screenHeight ? y : screenHeight;

        if ((x >= screenWidth - radius) || (x <= 0)) {
            xVelocity *= -1;
            hadCollideWall = true;
        }
        if ((y >= screenHeight - radius) || (y <= 0)) {
            yVelocity *= -1;
            hadCollideWall = true;
        }

        if (isCollide(plateA) && hadCollideWall) {
            yVelocity *= -1;
            hadCollideWall = false;
        }
    }

    public boolean isCollide(CharacterSprite plate) {
        int leftBound = plate.getX() - plate.getWidth()/2;
        int rightBound = plate.getX() + plate.getWidth()/2;
        int upperBound = plate.getY() - plate.getHeight()/2;
        int lowerBound = plate.getY() + plate.getHeight()/2;

        // Case 1: Circle collide on the corner of the plate.
        if (dist(x, y, leftBound, upperBound) <= radius ||
                dist(x, y, leftBound, lowerBound) <= radius ||
                dist(x, y, rightBound, upperBound) <= radius ||
                dist(x, y, rightBound, lowerBound) <= radius) {
            return true;
        }

        // Case 2: Circle collide through the edge of the plate.
        if ((((upperBound - y) <= radius && (upperBound >= y)) ||
                ((y - lowerBound) <= radius && (lowerBound <= y))) &&
                x >= leftBound && x <= rightBound) {
            return true;
        }

        return false;
    }

    public double dist(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public boolean gameOver1() {
        return y + radius >= screenHeight;
    }

    public int getRadius() { return radius; }

}
