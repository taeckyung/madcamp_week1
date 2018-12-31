package com.example.q.myapplication;

import android.content.res.Resources;
import android.graphics.Bitmap;

import java.util.Random;

public class ppBall extends CharacterSprite {
    private int xVelocity = 35;
    private int yVelocity = 35;
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

    public void update(CharacterSprite plateA, CharacterSprite plateB) {
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

        if ((isCollide(plateA) || isCollide(plateB)) && hadCollideWall) {
            hadCollideWall = false;
        }
    }

    public boolean isCollide(CharacterSprite plate) {
        int leftBound = plate.getX() - plate.getWidth()/2;
        int rightBound = plate.getX() + plate.getWidth()/2;
        int upperBound = plate.getY() - plate.getHeight()/2;
        int lowerBound = plate.getY() + plate.getHeight()/2;

        if ((upperBound - y <= radius) && (upperBound >= y) && (x + radius >= leftBound) && (x - radius <= rightBound)) {
            y = upperBound - radius;
            yVelocity *= -1;
            return true;
        }
        else if ((y - lowerBound <= radius) && (lowerBound <= y) && (x + radius >= leftBound) && (x - radius <= rightBound)) {
            y = lowerBound + radius;
            yVelocity *= -1;
            return true;
        }
        else if ((leftBound - x <= radius) && (x <= leftBound) && (y + radius >= upperBound) && (y - radius <= lowerBound)) {
            x = leftBound - radius;
            xVelocity *= -1;
            return true;
        }
        else if ((x - rightBound <= radius) && (rightBound <= x) && (y + radius >= upperBound) && (y - radius <= lowerBound)) {
            x = rightBound + radius;
            xVelocity *= -1;
            return true;
        }

        return false;
    }

    public double dist(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public int gameOver() {
        if (y + radius >= screenHeight) return 1;
        else if (y <= radius) return 2;
        else return 0;
    }

    public int getRadius() { return radius; }

}
