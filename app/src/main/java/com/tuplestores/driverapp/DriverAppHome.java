/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.tuplestores.driverapp.services.LocationUpdatesBroadcastReceiver;

/*Created By Ajish Dharman on 04-July-2019
 *
 *
 * "Q" supports three user choices for location:
 * <ul>
 *     <li>Allow all the time</li>
 *     <li>Allow while app is in use, i.e., while app is in foreground</li>
 *     <li>Not allow location</li>
 * </ul>
 *
 * Because this app requires location updates while the app isn't in use to work, i.e., not in the
 * foreground, the app requires the users to approve "all the time" for location access.
 *
 * However, best practice is to handle "all the time" and "while in use" permissions via a
 * foreground service + Notification. This use case is shown in the
 * LocationUpdatesForegroundService sample in this same repo.
 *
 * We still wanted to show an example where the app needs location access "all the time" for its
 * location features to be enabled (this sample).
 *
 * Location updates requested through this activity continue even when the activity is not in the
 * foreground. Note: apps running on "O" devices (regardless of targetSdkVersion) may receive
 * updates less frequently than the interval specified in the {@link LocationRequest} when the app
 * is no longer in the foreground.
 */
public class DriverAppHome extends AppCompatActivity {


    Button btnHome;
    Button btnTrips;
    Button btnProfile;

    private CountDownTimer countDownTimer;
    private ProgressBar pgBarCountDown;
    private TextView tv_timer_text;
    private int counter;
    LinearLayout llinlay_triprequest_popup;
    LinearLayout linlay_bottom_panel;
    TextView tv_drop_bubble_km;
    TextView tv_drop_bubble_min;
    TextView tv_picup_location;
    Button btnAccept;
    Button btnDecline;
    Activity thisActivity;
    SwitchCompat btnswith;

    /////////////////////////////////Related to Location ////////////////////////////////

    private static final String TAG ="FUSED_HELPER";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private static final long UPDATE_INTERVAL = 10000; // Every 60 seconds.

    /**
     * The fastest rate for active location updates. Updates will never be more frequent
     * than this value, but they may be less frequent.
     */
    private static final long FASTEST_UPDATE_INTERVAL = 10000; // Every 30 seconds

    /**
     * The max time before batched results are delivered by location services. Results may be
     * delivered sooner than this interval.
     */
    private static final long MAX_WAIT_TIME = UPDATE_INTERVAL * 5; // Every 5 minutes.

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    private LocationRequest mLocationRequest;

    /**
     * Provides access to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;

    boolean permissionGranted = false;

    /**
     * Callback for Location events.
     */
    private LocationCallback mLocationCallback;

    /**
     * Represents a geographical location.
     */
    private Location mCurrentLocation;

    boolean mLocatonRequested=false;



    ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_driver_app_home_drw_main);
        Initialize();

        // Check if the user revoked runtime permissions.
        if (!checkPermissions()) {
            requestPermissions();
        }

        // Kick off the process of building the LocationCallback, LocationRequest objects
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        createLocationRequest();
        createLocationCallback();

    }

    private void Initialize(){

        btnHome = findViewById(R.id.btnHome);
        btnTrips = findViewById(R.id.btnTrips);
        btnProfile = findViewById(R.id.btnProfile);

        pgBarCountDown = (ProgressBar)findViewById(R.id.pgBarCountDown);
        tv_timer_text = (TextView)findViewById(R.id.tv_timer_text);
        llinlay_triprequest_popup = (LinearLayout) findViewById (R.id.llinlay_triprequest_popup);
        linlay_bottom_panel = (LinearLayout) findViewById (R.id.linlay_bottom_panel);

        tv_drop_bubble_km = (TextView) findViewById(R.id.tv_drop_bubble_km);
        tv_drop_bubble_min  = (TextView) findViewById(R.id.tv_drop_bubble_min);
        tv_picup_location = (TextView) findViewById(R.id.tv_picup_location);
        btnAccept = (Button)findViewById(R.id.btnAccept);
        btnDecline = (Button)findViewById(R.id.btnDecline);
        btnswith = (SwitchCompat) (findViewById(R.id.btnswith));

        counter = 1;
        thisActivity = this;


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

        btnswith.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked && permissionGranted){

                    requestLocationUpdates();
                }
                else{
                    stopLocationUpdates();
                }
            }
        });

        startTimer();

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
                    Intent ii = new Intent(thisActivity,TripsListActivity.class);
                    startActivity(ii);
                    thisActivity.finish();
                } else if (v.getId() == R.id.btnProfile){

                    toggleButtonState("PROFILE");
                    Intent ii = new Intent(thisActivity,DriverProfileActivity.class);
                    startActivity(ii);
                    thisActivity.finish();

                }
            }
        });

    }

    /**
     * This method for startTimer for the progress bar (circular ) which shows the time to accept
     * the trip request.. after 15 sec the trip request will be cancelled
     */
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




    ///////////////////////LBS///////////////////////////////////////////////////

    /*
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int fineLocationPermissionState = ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION);

        int coarseLocationPermissionState = ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION);

        //  int backgroundLocationPermissionState = ActivityCompat.checkSelfPermission(
        //  ctx, Manifest.permission.ACCESS_BACKGROUND_LOCATION);

        if (fineLocationPermissionState == PackageManager.PERMISSION_GRANTED &&
                coarseLocationPermissionState == PackageManager.PERMISSION_GRANTED){
                    permissionGranted = true;
        }
        else{
            permissionGranted = false;
        }
        return permissionGranted;
    }

    private void requestPermissions() {

        boolean permissionAccessFineLocationApproved =
                ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;

        boolean backgroundLocationPermissionApproved =
                ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;

        boolean shouldProvideRationale =
                permissionAccessFineLocationApproved && backgroundLocationPermissionApproved;

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            Snackbar.make(
                   findViewById(R.id.frame_main),
                    R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(DriverAppHome.this,
                                    new String[] {
                                            Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.ACCESS_COARSE_LOCATION },
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    })
                    .show();
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(DriverAppHome.this,
                    new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION },
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }


    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");

            } else if ((grantResults[0] == PackageManager.PERMISSION_GRANTED) &&
                    (grantResults[1] == PackageManager.PERMISSION_GRANTED)) {

                       // Permission was granted.
                       permissionGranted = true;

               } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                permissionGranted = false;
                Snackbar.make(
                        findViewById(R.id.frame_main),
                        R.string.permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        }
    }


    /**
     * Sets up the location request. Android has two location request settings:
     * {@code ACCESS_COARSE_LOCATION} and {@code ACCESS_FINE_LOCATION}. These settings control
     * the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in
     * the AndroidManifest.xml.
     * <p/>
     * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update
     * interval (5 seconds), the Fused Location Provider API returns location updates that are
     * accurate to within a few feet.
     * <p/>
     * These settings are appropriate for mapping applications that show real-time location
     * updates.
     */
    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        // Note: apps running on "O" devices (regardless of targetSdkVersion) may receive updates
        // less frequently than this interval when the app is no longer in the foreground.
        mLocationRequest.setInterval(UPDATE_INTERVAL);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Sets the maximum time when batched location updates are delivered. Updates may be
        // delivered sooner than this interval.
        mLocationRequest.setMaxWaitTime(MAX_WAIT_TIME);
    }

    private PendingIntent getPendingIntent() {
        // Note: for apps targeting API level 25 ("Nougat") or lower, either
        // PendingIntent.getService() or PendingIntent.getBroadcast() may be used when requesting
        // location updates. For apps targeting API level O, only
        // PendingIntent.getBroadcast() should be used. This is due to the limits placed on services
        // started in the background in "O".

        // TODO(developer): uncomment to use PendingIntent.getService().
        // Intent intent = new Intent(this, LocationUpdatesIntentService.class);
       //intent.setAction(LocationUpdatesIntentService.ACTION_PROCESS_UPDATES);
       // return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

       Intent intent = new Intent(this, LocationUpdatesBroadcastReceiver.class);
       intent.setAction(LocationUpdatesBroadcastReceiver.ACTION_PROCESS_UPDATES);
      return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    /**
     * Handles the Request Updates button and requests start of location updates.
     */
    public void requestLocationUpdates() {
        try {
            Log.i(TAG, "Starting location updates");
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,getPendingIntent());

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
    private void stopLocationUpdates() {


        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        mLocatonRequested = false;
                    }
                });
    }

    /**
     * Creates a callback for receiving location events.
     */
    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                mCurrentLocation = locationResult.getLastLocation();

            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    ///////////////////////////////////////////////////////////////////////////


    @Override
    protected void onResume() {

        if(permissionGranted==false){
            requestPermissions();
        }
        super.onResume();
    }
}//Class
