/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.tuplestores.driverapp.api.ApiClient;
import com.tuplestores.driverapp.api.ApiInterface;
import com.tuplestores.driverapp.model.DriverModel;

public class LauncherActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    String tenant_id,driver_id,v_id;
    Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
    }

    //  ii.putExtra("EXTRA_DRIVER_ID",response.body().getDriver_id());
                         //  ii.putExtra("EXTRA_TENANT_ID",response.body().getTenant_id());
                          // ii.putExtra("EXTRA_VID",response.body().getTenant_id());

    private void initialize(){

        driver_id =  this.getIntent().getStringExtra("EXTRA_DRIVER_ID");
        tenant_id =  this.getIntent().getStringExtra("EXTRA_TENANT_ID");
        v_id =  this.getIntent().getStringExtra("EXTRA_VID");
        ctx = this;
    }


    private void fillDriverDetails(String driverId,String tenantId){

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<DriverModel> call = apiService.getDriverProfile(tenantId,driverId);

        call.enqueue(new Callback<DriverModel>() {
            @Override
            public void onResponse(Call<DriverModel> call, Response<DriverModel> response) {

                if(response.body()!=null){

                    DriverModel dm = response.body();

                    Intent ii = new Intent(ctx,DriverAppHome.class);
                    startActivity(ii);
                    finish();


                }
                else  if(response.body()==null){

                    //Show Blank template;
                    ShowAlert(ctx,getResources().getString(R.string.something_failed));


                }
            }//OnResponse

            @Override
            public void onFailure(Call<DriverModel> call, Throwable t) {

                //Something went wrong
                ShowAlert(ctx,getResources().getString(R.string.something_failed));

            }
        });
    }//fillDriverDetails

    private void ShowAlert(Context ctx, String msg){

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
