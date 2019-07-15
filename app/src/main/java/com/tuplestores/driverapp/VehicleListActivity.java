/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.common.util.SharedPreferencesUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.tuplestores.driverapp.api.ApiClient;
import com.tuplestores.driverapp.api.ApiInterface;
import com.tuplestores.driverapp.model.ApiResponse;
import com.tuplestores.driverapp.model.VehicleModel;
import com.tuplestores.driverapp.modeladapter.VehicleListAdapter;
import com.tuplestores.driverapp.utils.UtilityFunctions;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import static com.here.android.mpa.internal.r.h;

public class VehicleListActivity extends AppCompatActivity {

    List<VehicleModel> lstVehicle = null;
   // String driverId;
   // String tenantId;

    ListView lvVehicle;
    VehicleListAdapter vadpater;
     LinearLayout linlayrootPanel;

    ProgressBar pgBar;
    Context ctx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_list);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);


        Initialize();
    }

    private void Initialize(){

       // driverId = this.getIntent().getStringExtra("DRIVER_ID");
       // tenantId = this.getIntent().getStringExtra("TENANT_ID");

        linlayrootPanel = (LinearLayout)findViewById(R.id.linlayrootPanel) ;
        pgBar = (ProgressBar) findViewById(R.id.pgBar);
        pgBar.setVisibility(View.GONE);
        lvVehicle = (ListView) findViewById(R.id.lvVehicles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Select your vehicle");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_black);
        ctx = this;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ctx = this;
        lstVehicle = new ArrayList<VehicleModel>();

        final boolean isdriverTenantSet;

        isdriverTenantSet = UtilityFunctions.getSharedPreferenceOfDriver(this);

        lvVehicle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                VehicleModel vm = (VehicleModel) parent.getAdapter().getItem(position);
                if(vm!=null) {
                    if (isdriverTenantSet) {
                        //Call Attach API
                        doDriverChekIn(vm.getVehicle_id(), vm.getPlate_number(), UtilityFunctions.driver_id, UtilityFunctions.tenant_id);
                    } else {

                        ShowAlert(ctx, "", getResources().getString(R.string.problem_help));
                    }
                }
                else{
                    ShowAlert(ctx, "", getResources().getString(R.string.problem_help));
                }
            }
        });

       if (isdriverTenantSet){
            fillVehicleList();
        }
    }

    private void fillVehicleList(){

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<VehicleModel>> call = apiService.getvehiclelist(UtilityFunctions.tenant_id);

        call.enqueue(new Callback<List<VehicleModel>>() {
            @Override
            public void onResponse(Call<List<VehicleModel>> call, Response<List<VehicleModel>> response) {

                if(response.body()!=null){

                   lstVehicle = response.body();
                   vadpater = new VehicleListAdapter(lstVehicle ,getBaseContext());
                   lvVehicle.setAdapter(vadpater);


                }
                else  if(response.body()==null){

                    lstVehicle = null;
                    //Show Blank template;


                }
            }//OnResponse

            @Override
            public void onFailure(Call<List<VehicleModel>> call, Throwable t) {

                //Something went wrong
                ShowAlert(getBaseContext(),"",getResources().getString(R.string.something_failed));

            }
        });
    }

    private void doDriverChekIn(final String vehicleId, final String vPlate, String driverId, String tenantId){

        //Call the API
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);


        Call<ApiResponse> call = apiService.attachVehile(vehicleId,driverId,tenantId);
        showProgressBar();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                hideProgressBar();
                if(response.body()!=null){

                    ApiResponse api = response.body();
                    if(api.getStatus().trim().equals("S")){

                        UtilityFunctions.setSharedPreferenceVehicle(ctx,vehicleId);

                        ShowAlertAfterCheckin(ctx,"S",vPlate);
                    }
                    else {

                        ShowAlert(ctx,"",getResources().getString(R.string.problem_help_vattach));
                    }

                }
                else  if(response.body()==null){

                    ShowAlertAfterCheckin(ctx,"F",vPlate);

                }
            }//OnResponse

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

                hideProgressBar();
                //Something went wrong
                ShowAlert(getBaseContext(),"",getResources().getString(R.string.something_failed));

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vehicle_list, menu);
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {


                if(lstVehicle!=null){

                    List<VehicleModel> lsttemp = new ArrayList<VehicleModel>();
                    for(VehicleModel v : lstVehicle){

                        if(v.getVehicle_name().toLowerCase().contains(newText.toLowerCase())){
                            lsttemp.add(v);
                        }
                    }

                    VehicleListAdapter adptTemp = new VehicleListAdapter(lsttemp,getBaseContext());
                    lvVehicle.setAdapter(adptTemp);
                    lsttemp = null;
                    adptTemp = null;


                }

                return true;

            }
        });
        return true;
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
        if(dlg!=null){

            dlg.show();
        }
    }//ShowAlert

    private void ShowAlertAfterCheckin(Context ctx, String focus,String vPlate){

        androidx.appcompat.app.AlertDialog.Builder dlg = null;

        if(focus == "S") {
            dlg = new AlertDialog.Builder(ctx, R.style.AlertDialogTheme)
                    .setTitle("")
                    .setMessage(
                            getResources().getString(R.string.check_in_success) +" " +vPlate)

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                           goDriverHome();//Go to Driver Home
                        }
                    });
        }
        else if (focus == "F"){

            dlg = new AlertDialog.Builder(ctx, R.style.AlertDialogTheme)
                    .setTitle("")
                    .setMessage(getResources().getString(R.string.check_in_failed))

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            finish();//Finish it
                        }
                    });
        }
        if(dlg!=null){

            dlg.show();
        }
    }//ShowAlert

    private void goDriverHome(){

        Intent ii = new Intent(this,DriverAppHome.class);
        startActivity(ii);
        finish();

    }

    private void showProgressBar(){

        pgBar.setVisibility(View.VISIBLE);  //To show ProgressBar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }

    private void hideProgressBar(){

        if(pgBar!=null){

            pgBar.setVisibility(View.GONE);     // To Hide ProgressBar
        }
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
    }

}
