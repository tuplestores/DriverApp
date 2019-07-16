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

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tuplestores.driverapp.api.ApiClient;
import com.tuplestores.driverapp.api.ApiInterface;
import com.tuplestores.driverapp.model.DriverModel;
import com.tuplestores.driverapp.model.VehicleModel;
import com.tuplestores.driverapp.modeladapter.VehicleListAdapter;
import com.tuplestores.driverapp.utils.UtilityFunctions;



import org.w3c.dom.Text;

import java.util.List;

public class DriverProfileActivity extends AppCompatActivity {

    String driverId;
    String tenantId;

   TextView tv_mob;
   TextView tv_email;
   TextView tv_name;
   Context ctx;
   String mobile, isd,mobileonly;
   ProgressBar pg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);
        initialize();


    }

    private void initialize(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_black);
        toolbar.setTitle("My Profile");

        setSupportActionBar(toolbar);

         tv_mob= (TextView) (findViewById(R.id.tv_mob));
         tv_email= (TextView) (findViewById(R.id.tv_email));
         tv_name =  (TextView) (findViewById(R.id.tv_name));
        pg = (ProgressBar)findViewById(R.id.pgBar) ;


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });



        ctx = this;

        if(UtilityFunctions.getAllSharedPrefValues(ctx)){
           fillDriverDetails();
        }
        else{

            ShowAlert(ctx,getResources().getString(R.string.something_failed));

        }



    }//initialize()


    private void fillDriverDetails(){

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<DriverModel> call = apiService.getDriverProfile(UtilityFunctions.tenant_id,UtilityFunctions.driver_id);

        call.enqueue(new Callback<DriverModel>() {
            @Override
            public void onResponse(Call<DriverModel> call, Response<DriverModel> response) {


                pg.setVisibility(View.INVISIBLE);
                if(response.body()!=null){

                    DriverModel dm = response.body();
                    if(dm.getDriver_email()==null){
                        tv_email.setText("");
                    }
                    else {
                        tv_email.setText(dm.getDriver_email().toString());
                    }
                    if(dm.getIsd_code()==null){

                        isd="";
                    }
                    else{
                        isd = dm.getIsd_code();
                    }
                    if(dm.getDriver_mobile()==null){

                        mobile = "";
                    }
                    else{
                        mobile = dm.getDriver_mobile().toString();
                        mobileonly=mobile;
                    }
                    mobile= isd + " "+mobile;
                    tv_mob.setText(mobile);

                    if(dm.getDriver_name()==null) {
                        tv_name.setText("");
                    }
                    else{
                        tv_name.setText(dm.getDriver_name());
                    }




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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {


            startProfileEdit();
        }

        return super.onOptionsItemSelected(item);
    }

    private void startProfileEdit(){

        String name = tv_name.getText().toString();
        String email = tv_email.getText().toString();
        Intent ii = new Intent(this,DriverProfileEditActivity.class);
        ii.putExtra("NAME",name);
        ii.putExtra("ISD",isd);
        ii.putExtra("MOBILE",mobileonly);
        ii.putExtra("EMAIL",email);

        ii.putExtra("DRIVER_ID",UtilityFunctions.driver_id);

        ii.putExtra("TENANT_ID",UtilityFunctions.tenant_id);


        this.startActivity(ii);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
