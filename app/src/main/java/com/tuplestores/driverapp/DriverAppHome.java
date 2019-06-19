/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DriverAppHome extends AppCompatActivity {


    Button btnHome;
    Button btnTrips;
    Button btnProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void Initialize(){

        btnHome = findViewById(R.id.btnHome);
        btnTrips = findViewById(R.id.btnTrips);
        btnProfile = findViewById(R.id.btnProfile);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(v.getId() == R.id.btnHome){

                    toggleButtonState("HOME");
                    //Do the stuff for home
                }
                else if(v.getId() == R.id.btnTrips){
                    toggleButtonState("TRIPS");
                    //Do the stuff for Trips
                } else if (v.getId() == R.id.btnProfile){

                    toggleButtonState("PROFILE");
                    //Do the stuff for Profile

                }
            }
        });

    }

    private void toggleButtonState(String btn){

        if(btn.equals("HOME")){

            Drawable top = getResources().getDrawable(R.drawable.ic_home_act_blue_24);
            btnHome.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
            btnHome.setTextColor(getResources().getColor(R.color.colorbtnActive));

            top = getResources().getDrawable(R.drawable.ic_trips_grey_24);
            btnTrips.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
            btnTrips.setTextColor(getResources().getColor(R.color.colorbtnNotActive));

            top = getResources().getDrawable(R.drawable.ic_profile_grey_24);
            btnProfile.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
            btnProfile.setTextColor(getResources().getColor(R.color.colorbtnNotActive));


        }else if(btn.equals("TRIPS")){

            Drawable top = getResources().getDrawable(R.drawable.ic_trips_act_blue_24);
            btnTrips.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
            btnTrips.setTextColor(getResources().getColor(R.color.colorbtnActive));

            top = getResources().getDrawable(R.drawable.ic_home_grey_24);
            btnHome.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
            btnHome.setTextColor(getResources().getColor(R.color.colorbtnNotActive));

            top = getResources().getDrawable(R.drawable.ic_profile_grey_24);
            btnProfile.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
            btnProfile.setTextColor(getResources().getColor(R.color.colorbtnNotActive));



        }else if(btn.equals("PROFILE")){

            Drawable top = getResources().getDrawable(R.drawable.ic_profile_act_blue_24);
            btnProfile.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
            btnProfile.setTextColor(getResources().getColor(R.color.colorbtnActive));

            top = getResources().getDrawable(R.drawable.ic_trips_grey_24);
            btnTrips.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
            btnTrips.setTextColor(getResources().getColor(R.color.colorbtnNotActive));

            top = getResources().getDrawable(R.drawable.ic_home_grey_24);
            btnHome.setCompoundDrawablesWithIntrinsicBounds(null, top , null, null);
            btnHome.setTextColor(getResources().getColor(R.color.colorbtnNotActive));

        }

    }//ToggleButtonSTate




}//Class
