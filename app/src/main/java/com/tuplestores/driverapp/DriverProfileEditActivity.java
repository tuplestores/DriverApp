/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tuplestores.driverapp.api.ApiClient;
import com.tuplestores.driverapp.api.ApiInterface;
import com.tuplestores.driverapp.model.ApiResponse;
import com.tuplestores.driverapp.model.DriverModel;

public class DriverProfileEditActivity extends AppCompatActivity  {

    EditText edt_name;
    EditText edt_mob;
    EditText edt_isd;
    EditText edt_email;
    Button btnUpdate;
    Context ctx;
    ProgressBar pgBar;

    String driverId,tenantId,email,isd,mobile,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile_edit);
        initialize();
    }

    private void initialize(){

         ctx = this;

        driverId = this.getIntent().getStringExtra("DRIVER_ID");
        tenantId = this.getIntent().getStringExtra("TENANT_ID");

        email =  this.getIntent().getStringExtra("EMAIL");
        name =  this.getIntent().getStringExtra("NAME");
        mobile =  this.getIntent().getStringExtra("MOBILE");
        isd =  this.getIntent().getStringExtra("ISD");

        edt_name = (EditText)findViewById(R.id.edt_name);
        edt_isd = (EditText)findViewById(R.id.edt_isd);
        edt_mob= (EditText)findViewById(R.id.edt_mobile_number);
        edt_email= (EditText)findViewById(R.id.edt_email);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        pgBar = (ProgressBar) findViewById(R.id.pgBar);

        edt_email.setText(email);
        edt_isd.setText(isd);
        edt_mob.setText(mobile);
        edt_name.setText(name);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editDetails();
            }
        });


    }//Initialize()

    //Call<ApiResponse> updateDriver(@Query("tenant_id") String tenant_id, @Query("driver_id") String driver_id,
                               //   @Query("name") String name, @Query("isd") String isd,
                                 //  @Query("mobile") String mobile, @Query("email") String email);

    private void editDetails(){

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ApiResponse> call = apiService.updateDriverProfile(tenantId,driverId,edt_name.getText().toString(),
                                                            edt_isd.getText().toString(),
                                                         edt_mob.getText().toString(),edt_email.getText().toString());

        showProgressBar();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                if(response.body()!=null){

                    ApiResponse api = response.body();
                    hideProgressBar();

                    if(api.getStatus().equals("S")){

                        ShowAlert(ctx,getResources().getString(R.string.profile_udpate_success));
                    }
                    else if(api.getStatus().equals("E")){
                        ShowAlert(ctx,getResources().getString(R.string.something_failed));

                    }




                }
                else  if(response.body()==null){
                    showProgressBar();

                    //Show Blank template;
                    ShowAlert(ctx,getResources().getString(R.string.something_failed));

                }
            }//OnResponse

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

               hideProgressBar();
                //Something went wrong
                ShowAlert(ctx,getResources().getString(R.string.something_failed));

            }
        });
    }//editDetails

    private void ShowAlert(final Context ctx, String msg){

        androidx.appcompat.app.AlertDialog.Builder dlg = null;
        dlg =   new AlertDialog.Builder(ctx,R.style.AlertDialogTheme)
                .setTitle("")
                .setMessage(msg)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Intent ii = new Intent(ctx,DriverProfileActivity.class);
                        ii.putExtra("DRIVER_ID",driverId);
                        ii.putExtra("TENANT_ID",tenantId);
                        ctx.startActivity(ii);
                        finish();
                    }
                });
        if(dlg!=null){

            dlg.show();
        }
    }//ShowAlert


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

    ///Menu//////////





}
