package com.example.assignment_6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    TextView description,end;
    ImageView apple, android;
    Animation icon_bound, scale_icon, scale_icon2, alpha_text, alpha_text2, lefttranslate, righttranslate, text_bound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        apple = (ImageView) findViewById(R.id.apple);
        android= (ImageView) findViewById(R.id.android);

        description = (TextView) findViewById(R.id.description);
        end = (TextView) findViewById(R.id.end);

        icon_bound = AnimationUtils.loadAnimation(this, R.anim.icon_bound);
        icon_bound.setFillAfter(true);
        icon_bound.setInterpolator(new BounceInterpolator());

        text_bound = AnimationUtils.loadAnimation(this, R.anim.icon_bound);
        text_bound.setFillAfter(true);
        text_bound.setInterpolator(new BounceInterpolator());

        scale_icon = AnimationUtils.loadAnimation(this, R.anim.scale_icon);
        scale_icon2 = AnimationUtils.loadAnimation(this, R.anim.scale_icon);
        alpha_text = AnimationUtils.loadAnimation(this, R.anim.alpha_text);
        alpha_text2 = AnimationUtils.loadAnimation(this, R.anim.alpha_text);

        righttranslate = AnimationUtils.loadAnimation(this, R.anim.righttranslate);
        lefttranslate = AnimationUtils.loadAnimation(this, R.anim.lefttranslate);

        apple.startAnimation(icon_bound);
        android.startAnimation(icon_bound);

        icon_bound.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                android.startAnimation(scale_icon);
                description.startAnimation(alpha_text);

                alpha_text.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        description.setText("Android");
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        description.setText("");
                        apple.startAnimation(scale_icon2);
                        description.startAnimation(alpha_text2);

                        alpha_text2.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                description.setText("IOS");
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                description.setText("");
                                righttranslate.setFillAfter(true);
                                lefttranslate.setFillAfter(true);
                                android.startAnimation(righttranslate);
                                apple.startAnimation(lefttranslate);
                                lefttranslate.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        end.setText("That is all!");
                                        end.startAnimation(text_bound);
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
