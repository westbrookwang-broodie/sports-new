package com.example.sportsnews.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.sportsnews.R;

public class AnimationActivity extends AppCompatActivity {

    private static int SPLASH_TIME = 5000;

    //Hooks
    View first;
    TextView title, slogan;


    //Animations
    Animation topAnimantion, bottomAnimation, middleAnimation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_animation);


        topAnimantion = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_anmantion);
        middleAnimation = AnimationUtils.loadAnimation(this,R.anim.middle_animation);



        //Hooks

        //first = findViewById(R.id.first_line);

        title = findViewById(R.id.tenfei);
        slogan = findViewById(R.id.tagLine);

        //first.setAlpha(topAnimantion);

        title.setAnimation(middleAnimation);
        slogan.setAnimation(bottomAnimation);

        //Splash Screen

        new android.os.Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intent = new Intent(AnimationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}