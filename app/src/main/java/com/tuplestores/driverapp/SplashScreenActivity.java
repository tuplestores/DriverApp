/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.tuplestores.driverapp.utils.UtilityFunctions;

public class SplashScreenActivity extends AppCompatActivity {

    final int SPLASH_TIME = 5000;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPrefereneEditor;
    String v_id,driver_id,tenant_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

       /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                initialize();

            }
        },SPLASH_TIME);*/

       initialize();
    }


    private void initialize(){

        //First check if there is mobile and password in preference, if so alreay logged in
        // Make an autologin and go to dashboard
        //Else go to login screen

        if(UtilityFunctions.getSharedPreferenceOfDriver(this)){
            Intent ii = new Intent(this, DriverAppHome.class);
            startActivity(ii);
            finish();
        }
        else {

            //NO driver
            //Move to Login Activity
            Intent ii = new Intent(this,DriverVerificationActivity.class);
            startActivity(ii);
            finish();

        }
    }
}


