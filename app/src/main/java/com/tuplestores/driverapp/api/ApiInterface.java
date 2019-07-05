/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp.api;

import com.tuplestores.driverapp.model.ApiResponse;
import com.tuplestores.driverapp.model.DriverModel;
import com.tuplestores.driverapp.model.TripsModel;
import com.tuplestores.driverapp.model.VehicleModel;

import java.util.List;

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
    Call<DriverModel> verifyDriver(@Query("isd") String isd, @Query("Mobile") String Mobile,
                                   @Query("invite") String invite);



    @GET("dispatchAPI/getvehiclelist")
    Call<List<VehicleModel>> getvehiclelist(@Query("tenant_id") String tenant_id);

    @GET("dispatchAPI/attachVehicle")
    Call<ApiResponse> attachVehile(@Query("vehicle_id") String vehicle_id,
                                          @Query("driver_id") String driver_id,@Query("tenant_id") String tenant_id);

    @GET("dispatchAPI/getTrips")
    Call<List<TripsModel>> getTrips(@Query("driver_id") String driver_id, @Query("tenant_id") String tenant_id);

    @GET("dispatchAPI/getTrips")
    Call<ApiResponse> transformDeviceData(@Query("i_device_data") String i_device_data);

    @GET("dispatchAPI/getDriver")
    Call<DriverModel> getDriver(@Query("tenant_id") String tenant_id,@Query("driver_id") String driver_id);

    @GET("dispatchAPI/updteDriver")
    Call<ApiResponse> updateDriver(@Query("tenant_id") String tenant_id,@Query("driver_id") String driver_id,
                                   @Query("name") String name,@Query("isd") String isd,
                                   @Query("mobile") String mobile,@Query("email") String email);


}
