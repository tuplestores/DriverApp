/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*Created By Ajish Dharman on 13-June-2019
 *
 * Class that creates a Retrofit Object
 *
 */
public class ApiClient {

    public static final String BASE_URL = "http://localhost:8080/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {

        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
