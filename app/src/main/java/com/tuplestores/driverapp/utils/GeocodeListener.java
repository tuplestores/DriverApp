/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp.utils;

import com.here.android.mpa.search.ErrorCode;
import com.here.android.mpa.search.GeocodeRequest;
import com.here.android.mpa.search.GeocodeResult;
import com.here.android.mpa.search.ResultListener;

import java.util.List;

public class GeocodeListener implements ResultListener<List<GeocodeResult>> {

    @Override
    public void onCompleted(List<GeocodeResult> geocodeResults, ErrorCode errorCode) {

        if (errorCode != ErrorCode.NONE) {

        } else {

            for (GeocodeResult geo : geocodeResults) {
                System.out.println(geo.getLocation().getAddress());
            }
        }
    }
}

