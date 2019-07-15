/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.UiAutomation;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.tuplestores.driverapp.api.ApiClient;
import com.tuplestores.driverapp.api.ApiInterface;
import com.tuplestores.driverapp.model.ApiResponse;
import com.tuplestores.driverapp.model.DriverModel;
import com.tuplestores.driverapp.model.TripRequest;
import com.tuplestores.driverapp.services.LocationFGService;
import com.tuplestores.driverapp.services.LocationUpdatesBroadcastReceiver;
import com.tuplestores.driverapp.utils.Constants;
import com.tuplestores.driverapp.utils.FusedHelper;
import com.tuplestores.driverapp.utils.UtilityFunctions;

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
public class DriverAppHome extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback {


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
    TextView txtRidername;
    Button btnAccept;
    Button btnDecline;
    Activity thisActivity;
    SwitchCompat btnswith;
    ImageButton img_btn_nav_draw;
    ProgressBar pgBr;
    boolean driverAction = false;


    private GoogleMap mMap;

    String driver_id,tenant_id,v_id;

    /////////////////////////////////Related to Location ////////////////////////////////

    private static final String TAG ="FUSED_HELPER";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    boolean permissionGranted = false;

    BroadcastReceiver receiver;


    ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_driver_app_home_drw_main);
        Initialize();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Check if the user revoked runtime permissions.
        if (!checkPermissions()) {
            requestPermissions();
        }

        getFirebaseTocken();

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
        txtRidername = (TextView) findViewById(R.id.txtRidername);
        btnAccept = (Button)findViewById(R.id.btnAccept);
        btnDecline = (Button)findViewById(R.id.btnDecline);
        btnswith = (SwitchCompat) (findViewById(R.id.btnswith));
        img_btn_nav_draw = (ImageButton) findViewById(R.id.img_btn_nav_draw);

        pgBr = (ProgressBar) findViewById(R.id.pgBar);
        pgBr.setVisibility(View.GONE);

        counter = 1;
        thisActivity = this;

        if(!UtilityFunctions.getSharedPreferenceOfDriver(this)){

            ShowAlert(this,"",getResources().getString(R.string.something_failed));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_home);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);








        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Show a progressBar
                acceptRideRq(UtilityFunctions.ride_req_id);

            }
        });

        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                declineRideRq(UtilityFunctions.ride_req_id);

            }
        });

        btnswith.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked && permissionGranted){

                    startLocationService();

                }
            }
        });



        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(v.getId() == R.id.btnHome){

                    toggleButtonState("HOME");
                    //Do the stuff for home
                }

            }
        });

        btnTrips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if(v.getId() == R.id.btnTrips){
                    toggleButtonState("TRIPS");
                    //Do the stuff for Trips
                    Intent ii = new Intent(thisActivity,TripsListActivity.class);
                    startActivity(ii);
                    //thisActivity.finish();
                } else if (v.getId() == R.id.btnProfile){

                    toggleButtonState("PROFILE");
                    Intent ii = new Intent(thisActivity,DriverProfileActivity.class);
                    startActivity(ii);
                   // thisActivity.finish();

                }
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btnProfile){

                    toggleButtonState("PROFILE");
                    Intent ii = new Intent(thisActivity,DriverProfileActivity.class);
                    startActivity(ii);


                }
            }
        });

        img_btn_nav_draw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_home);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else  {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        ///LBS Receiver

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                    if(intent.getAction().equals(Constants.ACTION_TAXI_DISPATCH_FIREBASE_TRIP_R)){

                        String s = intent.getStringExtra(Constants.EXTRA_TAXI_DISPATCH_FIREBASE_TRIP_M);
                        if(s!=null && !s.equals("")){
                            //Show Ride request popup

                            startCountDownPopUp();
                        }

                    }
                    else if(intent.getAction().equals(Constants.ACTION_TAXI_DISPATCH_LBS)){

                        Location loc = intent.getExtras().getParcelable(Constants.EXTRA_TAXI_DISPATCH_LBS_MSG);
                        //Zoom to that location in map
                        if (loc != null) {
                            updateMapCamerabyZoom(loc);
                        }

                    }

                }
        };//Receiver

    }

    private void startCountDownPopUp(){

        pgBr.setVisibility(View.VISIBLE);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<TripRequest> call = apiService.getRiderRequest(UtilityFunctions.tenant_id,UtilityFunctions.v_id);

        call.enqueue(new Callback<TripRequest>() {
            @Override
            public void onResponse(Call<TripRequest> call, Response<TripRequest> response) {

                pgBr.setVisibility(View.GONE);

                if(response.body()!=null){

                    fillTripRequestPopUp(response.body());

                }
                else  if(response.body()==null){

                    ShowAlert(thisActivity,"",getResources().getString(R.string.something_failed));


                }
            }//OnResponse

            @Override
            public void onFailure(Call<TripRequest> call, Throwable t) {

                //Something went wrong
                ShowAlert(thisActivity,"",getResources().getString(R.string.something_failed));

            }
        });




    }

    private void fillTripRequestPopUp(TripRequest tr){

        txtRidername.setText(tr.getRider_full_name());
        tv_picup_location.setText(tr.getPick_up_location_text());
        UtilityFunctions.ride_req_id = tr.getRide_request_id();
        counter = 1;
        llinlay_triprequest_popup.setVisibility(View.VISIBLE);
        startTimer();
        Animation an = new RotateAnimation(0.0f, 90.0f, 80f, 80f);
        an.setFillAfter(true);
        pgBarCountDown.startAnimation(an);
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
                    if(driverAction==false){

                        cancelRideRq(UtilityFunctions.ride_req_id);
                    }

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

        UtilityFunctions.currentActivity="HOME";
        super.onResume();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_attach_vehicle) {
            // Handle the camera action
        } else if (id == R.id.nav_detach_vehicle) {

            detachVehicle(false);

        } else if (id == R.id.nav_sign_out) {
            detachVehicle(true);


        } else if (id == R.id.nav_profile) {

            Intent ii = new Intent(this,DriverProfileActivity.class);
            this.startActivity(ii);

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_home);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void singOut(){

        //Clear Preferene values for driver/tentant and vehicle attached
        //Call api to driver un verified
        //Call API to detach
        try {
            UtilityFunctions.clearAllPreferenceValues(this);
            Intent ii = new Intent(this, DriverVerificationActivity.class);
            startActivity(ii);
        }
        catch(Exception ex){


        }
    }

    private void detachVehicle(final boolean signout){

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ApiResponse> call = apiService.dettachVehicle(UtilityFunctions.tenant_id,UtilityFunctions.driver_id);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                if(response.body()!=null){

                    if(response.body().getStatus().trim().equals("S")){

                        if(signout){
                            singOut();
                        }
                        else {
                            ShowAlert(thisActivity, "", getResources().getString(R.string.dettach_success));
                        }
                    }


                }
                else  if(response.body()==null){

                    ShowAlert(thisActivity,"",getResources().getString(R.string.something_failed));


                }
            }//OnResponse

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

                //Something went wrong
                ShowAlert(thisActivity,"",getResources().getString(R.string.something_failed));

            }
        });

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_driver_home);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        IntentFilter inf = new IntentFilter();
        inf.addAction(Constants.ACTION_TAXI_DISPATCH_LBS);
        inf.addAction(Constants.ACTION_TAXI_DISPATCH_FIREBASE_TRIP_R);
        /*LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(Constants.ACTION_TAXI_DISPATCH_LBS)
        );*/

        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                inf
        );

        super.onStart();
    }

    @Override
    protected void onStop() {

         LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
         super.onStop();

        }

        private  void startLocationService(){

           Intent ii = new Intent(this,LocationFGService.class);
           ContextCompat.startForegroundService(this,ii);

        }

     private  void stopLocationService(){

        Intent ii = new Intent(this,LocationFGService.class);
        stopService(ii);
    }

    private void updateMapCamerabyZoom(Location loc){

        CameraUpdate center=
                CameraUpdateFactory.newLatLng(new LatLng( loc.getLatitude(),
                        loc.getLongitude()));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

        if(null!=mMap) {

            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(loc.getLatitude(),loc.getLongitude()))
                    .title("Marker in Sydney"))
                    .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker2));
            mMap.moveCamera(center);
            mMap.animateCamera(zoom);



               if(UtilityFunctions.getAllSharedPrefValues(this)){

                   FusedHelper.ctx = this;//Activity

                   FusedHelper.saveLocations(loc,UtilityFunctions.tenant_id,UtilityFunctions.v_id);

               }
               else {
                   Toast.makeText(this,
                           getResources().getString(R.string.something_failed),
                           Toast.LENGTH_SHORT).show();
                   finish();

               }

        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

    }

     private void ShowAlert(Context ctx, String focus, String msg){

        androidx.appcompat.app.AlertDialog.Builder dlg = null;

            dlg =   new AlertDialog.Builder(ctx,R.style.AlertDialogTheme)
                    .setTitle("")
                    .setMessage(msg)

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                           finish();
                        }
                    });
        }


        //Firebase//////////

    private void getFirebaseTocken(){

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast

                        Log.d(TAG, "Tocken==="+token);

                    }
                });

    }

    private void acceptRideRq(String ridereq){

        driverAction = true;
        countDownTimer.cancel();
        counter =1;
        llinlay_triprequest_popup.setVisibility(View.GONE);


        if(ridereq==null || ridereq.equals("")){
            return;
        }
        pgBr.setVisibility(View.VISIBLE);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ApiResponse> call = apiService.acceptRideRequest(UtilityFunctions.tenant_id,
                ridereq,UtilityFunctions.v_id,UtilityFunctions.driver_id);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                pgBr.setVisibility(View.GONE);
                if(response.body()!=null){

                    if(response.body().getStatus().trim().equals("S")){

                        ShowAlert(thisActivity, "", getResources().getString(R.string.ride_requeted_accepted));
                    }


                }
                else  if(response.body()==null){

                    ShowAlert(thisActivity,"",getResources().getString(R.string.something_failed));


                }
            }//OnResponse

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

                //Something went wrong
                ShowAlert(thisActivity,"",getResources().getString(R.string.something_failed));

            }
        });


    }

    private  void declineRideRq(String ridereq){

        driverAction = true;
        countDownTimer.cancel();
        counter =1;
        llinlay_triprequest_popup.setVisibility(View.GONE);
        if(ridereq==null || ridereq.equals("")){
            return;
        }
        pgBr.setVisibility(View.VISIBLE);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ApiResponse> call = apiService.declineRideRequest(UtilityFunctions.tenant_id,
                ridereq,UtilityFunctions.v_id);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                pgBr.setVisibility(View.GONE);

                if(response.body()!=null){

                    if(response.body().getStatus().trim().equals("S")){

                        ShowAlert(thisActivity, "", getResources().getString(R.string.ride_requeted_rejected));
                    }


                }
                else  if(response.body()==null){

                    ShowAlert(thisActivity,"",getResources().getString(R.string.something_failed));


                }
            }//OnResponse

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

                //Something went wrong
                ShowAlert(thisActivity,"",getResources().getString(R.string.something_failed));

            }
        });


    }

    private void cancelRideRq(String ridereq){

        if(ridereq==null || ridereq.equals("")){
            return;
        }

        pgBr.setVisibility(View.VISIBLE);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ApiResponse> call = apiService.cancelRideRequest(UtilityFunctions.tenant_id,
                ridereq,UtilityFunctions.v_id,UtilityFunctions.driver_id);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                pgBr.setVisibility(View.GONE);
                if(response.body()!=null){

                    if(response.body().getStatus().trim().equals("S")){

                        ShowAlert(thisActivity, "", getResources().getString(R.string.ride_requeted_cancelled));
                    }


                }
                else  if(response.body()==null){

                    ShowAlert(thisActivity,"",getResources().getString(R.string.something_failed));


                }
            }//OnResponse

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

                //Something went wrong
                ShowAlert(thisActivity,"",getResources().getString(R.string.something_failed));

            }
        });


    }


}//Class
