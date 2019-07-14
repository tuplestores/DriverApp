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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);


    }

    private void initialize(){



        TextView tv_mob= (TextView) (findViewById(R.id.tv_mob));
        TextView tv_email= (TextView) (findViewById(R.id.tv_email));
        TextView tv_name =  (TextView) (findViewById(R.id.tv_name));


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

                if(response.body()!=null){

                    DriverModel dm = response.body();
                    tv_email.setText(dm.getEmail());
                    String mob = dm.getIsd_code();
                    mob= mob + "" + dm.getMobile();
                    tv_mob.setText(mob);
                    tv_name.setText(dm.getFirst_name());


                }
                else  if(response.body()==null){

                    //Show Blank template;


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
        String isd = tv_mob.getText().toString().substring(0,1);
        String mobile = tv_mob.getText().toString();
        String email = tv_email.getText().toString();
        Intent ii = new Intent(this,DriverProfileEditActivity.class);
        ii.putExtra("NAME",name);
        ii.putExtra("ISD",isd);
        ii.putExtra("MOBILE",mobile);
        ii.putExtra("EMAIL",email);

        ii.putExtra("DRIVER_ID",driverId);

        ii.putExtra("TENANT_ID",tenantId);


        this.startActivity(ii);
    }

}
