/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp.api;

import com.tuplestores.driverapp.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/*Created By Ajish Dharman on 13-June-2019
 *
 * Define End points to call the APIs required by Other
 * Activities / Services
 *
 */
public interface ApiInterface {


    @GET("dispatchAPI/verifydriver")
    Call<ApiResponse> verifyDriver(@Query("isd") String isd,@Query("Mobile") String Mobile,
                                        @Query("invite") String invite);

}
