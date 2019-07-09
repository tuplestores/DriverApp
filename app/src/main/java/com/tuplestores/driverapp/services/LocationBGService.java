/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp.services;

//This is the Service intented to run in background for sending the location
//Using Fused Location API

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class LocationBGService extends Service {





    public LocationBGService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
}
