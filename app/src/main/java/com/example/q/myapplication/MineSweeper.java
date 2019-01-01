package com.example.q.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;


public class MineSweeper extends Activity {

    static TextView bomb;
    static TextView win;
    static TextView lose;
    static LottieAnimationView animationView;
    static LottieAnimationView loseanimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("game","intent는 받아왔니");
        getIntent();
        Log.e("game","intent는 받아왔니2");
        setContentView(R.layout.activity_mine_sweeper);
        bomb = findViewById(R.id.bomb);
        win = findViewById(R.id.win);
        lose = findViewById(R.id.lose);
        animationView = findViewById(R.id.animation_view);
        loseanimation = findViewById(R.id.lose_animation);
        win.bringToFront() ;
        lose.bringToFront();
        animationView.bringToFront();
        loseanimation.bringToFront();
        GameEngine.getInstance().createGrid(MineSweeper.this);
        bomb.setText(GameEngine.BOMB_NUMBER + "");
        animationView.setAnimation("confetti_blast.json");
        loseanimation.setAnimation("uh_oh.json");
    }

    public TextView getBomb(){
        return bomb;
    }

    public void onResetClicked(View v){
        GameEngine.getInstance().createGrid(MineSweeper.this);
        bomb.setText(GameEngine.BOMB_NUMBER+"");
        MineSweeper.win.setVisibility(View.INVISIBLE);
        MineSweeper.lose.setVisibility(View.INVISIBLE);
        MineSweeper.animationView.setVisibility(View.INVISIBLE);
        MineSweeper.loseanimation.setVisibility(View.INVISIBLE);
    }

}
