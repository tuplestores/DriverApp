/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 *
 * This activity will show the trip request pop up
 * Same activity as Driver Home. May be only need to show the
 */

package com.tuplestores.driverapp;

import androidx.appcompat.app.AppCompatActivity;

import android.opengl.Visibility;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TripRequestActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    private ProgressBar pgBarCountDown;
    private TextView tv_timer_text;
    private int counter;
    LinearLayout llinlay_triprequest_popup;
    TextView tv_drop_bubble_km;
    TextView tv_drop_bubble_min;
    TextView tv_picup_location;
    Button btnAccept;
    Button btnDecline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_request);
        Initialize();


    }

    private void Initialize(){

        pgBarCountDown = (ProgressBar)findViewById(R.id.pgBarCountDown);
        tv_timer_text = (TextView)findViewById(R.id.tv_timer_text);
        llinlay_triprequest_popup = (LinearLayout) findViewById (R.id.llinlay_triprequest_popup);

        tv_drop_bubble_km = (TextView) findViewById(R.id.tv_drop_bubble_km);
        tv_drop_bubble_min  = (TextView) findViewById(R.id.tv_drop_bubble_min);
        tv_picup_location = (TextView) findViewById(R.id.tv_picup_location);
        btnAccept = (Button)findViewById(R.id.btnAccept);
        btnDecline = (Button)findViewById(R.id.btnDecline);

        counter = 1;


        Animation an = new RotateAnimation(0.0f, 90.0f, 80f, 80f);
        an.setFillAfter(true);
        pgBarCountDown.startAnimation(an);


        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        startTimer();
    }

    private void startTimer() {

        countDownTimer = new CountDownTimer(15000, 1000) {
            // 1000 means, onTick function will be called at every 1 sec

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                long seconds = leftTimeInMilliseconds / 1000;
                if(counter == 15){
                    pgBarCountDown.setProgress(100);
                }
                else {
                    pgBarCountDown.setProgress(counter * 6);
                }

                tv_timer_text.setText(counter+" " +"Sec");
                // format the textview to show the easily readable format
                counter++;

            }
            @Override
            public void onFinish() {

                if(counter > 15){

                    countDownTimer.cancel();
                    llinlay_triprequest_popup.setVisibility(View.GONE);

                }
            }
        }.start();

    }
}
