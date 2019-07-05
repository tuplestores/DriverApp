/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp.model;

/*Created By Ajish Dharman on 01-July-2019
 *
 *
 */
public class DriverModel {

     private String tenant_id;
     private String driver_id;
     private String email;
     private String first_name;
     private String last_name;
     private String isd_code;
     private String mobile;
     private String driver_online;
     private String checked_in_vehicle_id;
     private String verified;
     private String invite_code;
     private String status;

    public String getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getIsd_code() {
        return isd_code;
    }

    public void setIsd_code(String isd_code) {
        this.isd_code = isd_code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDriver_online() {
        return driver_online;
    }

    public void setDriver_online(String driver_online) {
        this.driver_online = driver_online;
    }

    public String getChecked_in_vehicle_id() {
        return checked_in_vehicle_id;
    }

    public void setChecked_in_vehicle_id(String checked_in_vehicle_id) {
        this.checked_in_vehicle_id = checked_in_vehicle_id;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
