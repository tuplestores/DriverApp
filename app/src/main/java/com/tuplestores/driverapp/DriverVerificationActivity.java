/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tuplestores.driverapp.api.ApiClient;
import com.tuplestores.driverapp.api.ApiInterface;
import com.tuplestores.driverapp.model.ApiResponse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class DriverVerificationActivity extends AppCompatActivity {

    EditText edtisdCode;
    EditText edtmobileNumber;
    EditText edtinviteCode;
    Button btnSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_verification);

    }

    private void Initialize(){

        edtisdCode = (EditText)findViewById(R.id.edtIsd);
        edtmobileNumber = (EditText)findViewById(R.id.edtMobileNumber);
        edtinviteCode = (EditText)findViewById(R.id.edtInviteCode);
        btnSignin = (Button)findViewById(R.id.btnSignin);

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();// Call signin API
            }
        });
    }

    private void signIn(){

        if(edtmobileNumber.getText()== null || edtmobileNumber.getText().equals("")){

        }
        else if(edtinviteCode.getText()== null || edtinviteCode.getText().equals("")){


        }else{

            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);

            Call<ApiResponse> call = apiService.verifyDriver(edtisdCode.getText().toString(),
                                                            edtmobileNumber.getText().toString(),
                                                            edtinviteCode.getText().toString());

            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                  if(response.body().getStatus().equals("o_verified")){

                      //Driver Verified Successfully.. Go to Next Activity

                  }
                  else  if(response.body().getStatus().equals("ERROR")){

                  }
                }//OnResponse

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {

                }
            });

        }

    }

}
