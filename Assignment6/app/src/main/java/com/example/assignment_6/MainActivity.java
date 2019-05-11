package com.example.assignment_6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView text;
    ImageView img;
    Animation fromtop, frombottom, rotate, scale;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (ImageView) findViewById(R.id.img);
        fromtop = AnimationUtils.loadAnimation(this, R.anim.toptranslate);
        rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);

        text = (TextView) findViewById(R.id.textview);
        frombottom = AnimationUtils.loadAnimation(this, R.anim.bottomtranslate);
        scale = AnimationUtils.loadAnimation(this, R.anim.scale);

       final Intent intent = new Intent(this, SecondActivity.class);

        fromtop.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                img.startAnimation(rotate);
                text.startAnimation(scale);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        img.startAnimation(fromtop);
        text.startAnimation(frombottom);







    }
}
