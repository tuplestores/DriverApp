/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp.api;

import com.tuplestores.driverapp.model.ApiResponse;
import com.tuplestores.driverapp.model.DriverModel;
import com.tuplestores.driverapp.model.TripRequest;
import com.tuplestores.driverapp.model.TripsModel;
import com.tuplestores.driverapp.model.VehicleModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @GET("dispatchAPI/attachvehicle")
    Call<ApiResponse> attachVehile(@Query("vehicle_id") String vehicle_id,
                                          @Query("driver_id") String driver_id,@Query("tenant_id") String tenant_id);


    @GET("dispatchAPI/getTrips")
    Call<List<TripsModel>> getTrips(@Query("i_tenant_id") String i_tenant_id, @Query("i_driver_id") String i_driver_id,
                                    @Query("i_from_date") String i_from_date,@Query("i_to_date") String i_to_date);

    @POST("dispatchAPI/loadDeviceData")
    Call<ApiResponse> loadDeviceData(@Body String device_data);

    @GET("dispatchAPI/getDriverProfile")
    Call<DriverModel> getDriverProfile(@Query("tenant_id") String tenant_id,@Query("driver_id") String driver_id);

    //http://localhost:8080/dispatchAPI/updateDriverProfile?tenant_id=90ed3aed-7c56-11e9-bf06-02306f682212&
    // driver_id=e7bc46d6-7c56-11e9-bf06-02306f682212&email=babu@gmail.com&first_name=Raju&last_name&
    // isd_code=91&mobile=9393030505


    @GET("dispatchAPI/updateDriverProfile")
    Call<ApiResponse> updateDriverProfile(@Query("tenant_id") String tenant_id,@Query("driver_id") String driver_id,
                                   @Query("email") String email,@Query("first_name") String first_name,
                                   @Query("last_name") String last_name,@Query("isd_code") String isd_code,
                                          @Query("mobile") String mobile );

    //@RequestParam String tenant_id,@RequestParam String driver_id

    @GET("dispatchAPI/dettachVehicle")
    Call<ApiResponse> dettachVehicle(@Query("tenant_id") String tenant_id,@Query("driver_id") String driver_id);


     @GET("dispatchAPI/acceptRideRequest")
     Call<ApiResponse> acceptRideRequest(@Query("tenant_id") String tenant_id,@Query("ride_request_id") String ride_request_id,
                                             @Query("vehicle_id") String vehicle_id, @Query("driver_id") String driver_id);

    @GET("dispatchAPI/declineRideRequest")
    Call<ApiResponse> declineRideRequest(@Query("tenant_id") String tenant_id,@Query("ride_request_id") String ride_request_id,
                                         @Query("vehicle_id") String vehicle_id);

    @GET("dispatchAPI/cancelRideRequest")
    Call<ApiResponse> cancelRideRequest(@Query("tenant_id") String tenant_id,@Query("ride_request_id") String ride_request_id,
                                         @Query("vehicle_id") String vehicle_id,   @Query("driver_id") String driver_id);



    @GET("dispatchAPI/getRiderRequest")
    Call<TripRequest> getRiderRequest(@Query("tenant_id") String tenant_id,
                                      @Query("vehicle_id") String vehicle_id);




}
