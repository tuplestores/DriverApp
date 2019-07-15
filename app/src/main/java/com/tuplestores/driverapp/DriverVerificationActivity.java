/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.tuplestores.driverapp.api.ApiClient;
import com.tuplestores.driverapp.api.ApiInterface;
import com.tuplestores.driverapp.model.ApiResponse;
import com.tuplestores.driverapp.model.DriverModel;
import com.tuplestores.driverapp.utils.ISDUtils;
import com.tuplestores.driverapp.utils.UtilityFunctions;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.HashMap;
import java.util.Map;


public class DriverVerificationActivity extends AppCompatActivity {

    EditText edtisdCode;
    EditText edtmobileNumber;
    EditText edtinviteCode;
    Button btnSignin;
    Map<String,String> isdMap;
    String tenant_id;
    Activity thisActivity;
    ProgressBar pgBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_verification);
        Initialize();

    }

    private void Initialize(){
        try {


            edtisdCode = (EditText) findViewById(R.id.edtIsd);
            edtmobileNumber = (EditText) findViewById(R.id.edtMobileNumber);
            edtinviteCode = (EditText) findViewById(R.id.edtInviteCode);
            btnSignin = (Button) findViewById(R.id.btnSignin);

            pgBar = (ProgressBar) findViewById(R.id.pgBar);
            showHideProgress(false);
            edtisdCode.setText("");

            btnSignin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    signIn();// Call signin API
                }
            });

            TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            String countryCodeValue = tm.getNetworkCountryIso();

           // edtisdCode.setText(ISDUtils.getISDCode(countryCodeValue));

            //Test values
            edtisdCode.setText("91");
            edtmobileNumber.setText("9876534567");
            edtinviteCode.setText("2346");
            thisActivity = this;
        }
        catch(Exception ex){

            Log.e("DRTAXI_VERIFY",ex.getMessage());
        }
    }

    private void showHideProgress(boolean show){

        if(show){
            pgBar.setVisibility(View.VISIBLE);
        }
        else{
            pgBar.setVisibility(View.GONE);
        }
    }

    private void signIn(){

        showHideProgress(true);

        if(edtmobileNumber.getText()== null || edtmobileNumber.getText().toString().equals("")){

            ShowAlert(this,"MOB" ,getResources().getString(R.string.pls_input_mobile));

        }
        else if(edtinviteCode.getText()== null || edtinviteCode.getText().toString().equals("")){

               ShowAlert(this,"INV",getResources().getString(R.string.pls_input_invite_code));
        }else{

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<DriverModel> call = apiService.verifyDriver(edtisdCode.getText().toString(),
                                                            edtmobileNumber.getText().toString(),
                                                            edtinviteCode.getText().toString());

            call.enqueue(new Callback<DriverModel>() {
                @Override
                public void onResponse(Call<DriverModel> call, Response<DriverModel> response) {

                    showHideProgress(false);
                    if(response.body()!=null) {

                        if(response.body().getStatus().equals("Y")) {

                            UtilityFunctions.setSharedPreferenceDriver(thisActivity,
                                    response.body().getDriver_id(),
                                    response.body().getTenant_id());

                            //Invode vehicle  List for driver check in
                            Intent ii = new Intent(thisActivity, VehicleListActivity.class);
                            ii.putExtra("EXTRA_DRIVER_ID",response.body().getDriver_id());
                            ii.putExtra("EXTRA_TENANT_ID",response.body().getTenant_id());
                            ii.putExtra("EXTRA_VID",response.body().getTenant_id());
                             startActivity(ii);
                            finish();
                        }else if(response.body().getStatus().equals("N")) {
                            //Alert Dialog;
                            ShowAlert(thisActivity,"DV",
                                    getResources().getString(R.string.verify_failed));
                        }
                    }
                    else {

                        ShowAlert(thisActivity,"DV",
                                getResources().getString(R.string.something_failed));

                    }


                }//OnResponse*/

                @Override
                public void onFailure(Call<DriverModel> call, Throwable t) {
                    ShowAlert(thisActivity,"DV",
                            getResources().getString(R.string.verify_failed));

                }
            });
        }

    }

    private void ShowAlert(Context ctx, String focus, String msg){

     androidx.appcompat.app.AlertDialog.Builder dlg = null;
        if(focus=="MOB"){
        dlg =   new AlertDialog.Builder(ctx,R.style.AlertDialogTheme)
                .setTitle("")
                .setMessage(msg)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        edtmobileNumber.requestFocus();
                    }
                });
         }
         else if(focus == "INV"){


            dlg =   new AlertDialog.Builder(ctx,R.style.AlertDialogTheme)
                    .setTitle("")
                    .setMessage(msg)

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            edtinviteCode.requestFocus();
                        }
                    });

            } else if(focus == "DV"){


            dlg =   new AlertDialog.Builder(ctx,R.style.AlertDialogTheme)
                    .setTitle("")
                    .setMessage(msg)

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            finish();
                        }
                    });
        }

            if(dlg!=null){

              dlg.show();
            }
        }//ShowAlert



}
