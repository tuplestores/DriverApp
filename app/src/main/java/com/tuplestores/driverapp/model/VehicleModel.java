/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp.model;

import com.google.gson.annotations.SerializedName;

/*Created By Ajish Dharman on 01-July-2019
 *
 *
 */public class VehicleModel {

    @SerializedName("tenant_id")
    private String tenant_id;
    @SerializedName("vehicle_id")
    private  String vehicle_id;
    @SerializedName("vehicle_name")
    private String vehicle_name;
    @SerializedName("plate_number")
    private String plate_number;

    public String getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getVehicle_name() {
        return vehicle_name;
    }

    public void setVehicle_name(String vehicle_name) {
        this.vehicle_name = vehicle_name;
    }

    public String getPlate_number() {
        return plate_number;
    }

    public void setPlate_number(String plate_number) {
        this.plate_number = plate_number;
    }
}
