/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp.utils;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.tuplestores.driverapp.R;
import com.tuplestores.driverapp.api.ApiClient;
import com.tuplestores.driverapp.api.ApiInterface;
import com.tuplestores.driverapp.model.ApiResponse;
import com.tuplestores.driverapp.model.TripsModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*Created By Ajish Dharman on 04-July-2019
 *
 *
 */

public class FusedHelper {

    static final String TAG = "FusedHelper";
    public static Context ctx;

      public static void updateLocation(List<Location> locations,String vid, String tid) {

        try{

            Log.d(TAG, "->updateLocation :" + new Date().toString());
            if(locations!=null && locations.size() > 0) {
                int limit = 25;
                int count = locations.size();
                int lastIndex = count - 1;

                List<Location> locationBatch25 = new ArrayList<Location>();

                if (locationBatch25 != null)
                {
                    for (int i = 0; i < count; i++)
                    {
                        locationBatch25.add(locations.get(i));
                        if (locationBatch25.size() == limit || i == lastIndex)
                        {
                            try
                            {

                               // saveLocations(locationBatch25,tid,vid);

                            }
                            catch (Exception ex)
                            {

                            }
                            locationBatch25 = null;
                            locationBatch25 = new ArrayList<Location>();
                        }
                    }//for
                }//If
            }
            else{
                Log.d(TAG, "->updateLocation : No fix" + new Date().toString());
            }
        }
        catch(Exception ex){


        }
    }//updateLocation


    public static void saveLocations(Location loc,String tid, String vid)
    {

        String locality;

        StringBuilder strbuf = new StringBuilder("");
        //for (int i = 0; i < locations.size(); i++)
       // {

            try {
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                String dt = dateFormat.format(cal.getTime());

                strbuf.append("<tid>" + tid + "</tid>" + "\n"
                        + "<vid>" + vid + "</vid>" + "\n" +
                        "<dt>" + dt + "</dt>" + "\n" +
                        "<lon>" + loc.getLongitude() + "</lon>" + "\n" + "<lat>"
                        + loc.getLatitude() + "</lat>"
                );
                //}

            }

            catch (Exception ex){

                Log.d(TAG, "-->Location Async" + ex.getMessage());
            }

        Log.d(TAG, "-->Location Async" + strbuf.toString());
        //Call the webservice task here as async
        try
        {
            if(strbuf!= null || !strbuf.toString().equals("")) {
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);

                Call<ApiResponse> call = apiService.loadDeviceData(strbuf.toString());

                call.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                        if (response.body() != null) {

                            ApiResponse api = response.body();
                            if (api.getStatus().trim().equals("S")) {

                                Log.d(TAG, "-->Save Location Success");
                            }

                        } else if (response.body() == null) {

                            Log.d(TAG, "-->Save Location Failed");

                        }
                    }//OnResponse

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {

                        //Something went wrong
                        Log.d(TAG, "-->Save Location Failed " + t.getLocalizedMessage());

                    }
                });

            }
            else {

                Toast.makeText(ctx,ctx.getResources().getString(R.string.something_failed),Toast.LENGTH_SHORT).show();
            }



        }
        catch (Exception ex)
        {
            Log.d(TAG, "-->Save Location Failed "+ex.getLocalizedMessage() );
        }
    }//SaveLocations


}
