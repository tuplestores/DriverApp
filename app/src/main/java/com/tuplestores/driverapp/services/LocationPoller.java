/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * BroadCast Receiver for AlarmManager..Simply passes over the location polling to
 * FusedLocationPollerService-/LocationPollerService
 */
public class LocationPoller extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
