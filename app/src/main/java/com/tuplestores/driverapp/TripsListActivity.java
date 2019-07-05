/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.tuplestores.driverapp.api.ApiClient;
import com.tuplestores.driverapp.api.ApiInterface;
import com.tuplestores.driverapp.model.TripsModel;
import com.tuplestores.driverapp.model.VehicleModel;
import com.tuplestores.driverapp.modeladapter.TripsAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TripsListActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView rcvListTrips;
    private List<TripsModel> lstTrips = new ArrayList<>();
    private RecyclerView recyclerView;
    private TripsAdapter mAdapter;
    String driver_id, tenant_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips_home_drw_main);
        Initialize();
    }

    private void Initialize(){

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAdapter = new TripsAdapter(lstTrips);
        rcvListTrips = (RecyclerView)findViewById(R.id.rclstTrips);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rcvListTrips.setLayoutManager(mLayoutManager);
        rcvListTrips.setAdapter(mAdapter);
        fillTrips();

    }

    private void fillTrips(){

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<List<TripsModel>> call = apiService.getTrips(driver_id,tenant_id);

        call.enqueue(new Callback<List<TripsModel>>() {
            @Override
            public void onResponse(Call<List<TripsModel>> call, Response<List<TripsModel>> response) {

                if(response.body()!=null){

                    lstTrips = response.body();
                    mAdapter.notifyDataSetChanged();

                }
                else  if(response.body()==null){

                    lstTrips = null;
                    //Show Blank template;


                }
            }//OnResponse

            @Override
            public void onFailure(Call<List<TripsModel>> call, Throwable t) {

                //Something went wrong
                ShowAlert(getBaseContext(),"",getResources().getString(R.string.something_failed));

            }
        });
    }//fillTrips

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
}
