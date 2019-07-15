package com.tuplestores.driverapp.model;

/*Created By Ajish Dharman on 16-July-2019
 *
 *
 */


public class TripRequest {
    private String ride_request_id;
    private String product_id;
    private String product_name;
    private String rider_id;
    private String rider_full_name;
    private String pick_up_latitude;
    private String pick_up_longitude;
    private String drop_off_latitude;
    private String drop_off_longitude;
    private String pick_up_location_text;
    private String drop_off_location_text;
    public String getProduct_id() {
        return product_id;
    }
    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
    public String getProduct_name() {
        return product_name;
    }
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
    public String getRider_id() {
        return rider_id;
    }
    public void setRider_id(String rider_id) {
        this.rider_id = rider_id;
    }

    public String getPick_up_latitude() {
        return pick_up_latitude;
    }
    public void setPick_up_latitude(String pick_up_latitude) {
        this.pick_up_latitude = pick_up_latitude;
    }
    public String getPick_up_longitude() {
        return pick_up_longitude;
    }
    public void setPick_up_longitude(String pick_up_longitude) {
        this.pick_up_longitude = pick_up_longitude;
    }
    public String getDrop_off_latitude() {
        return drop_off_latitude;
    }
    public void setDrop_off_latitude(String drop_off_latitude) {
        this.drop_off_latitude = drop_off_latitude;
    }
    public String getDrop_off_longitude() {
        return drop_off_longitude;
    }
    public void setDrop_off_longitude(String drop_off_longitude) {
        this.drop_off_longitude = drop_off_longitude;
    }
    public String getPick_up_location_text() {
        return pick_up_location_text;
    }
    public void setPick_up_location_text(String pick_up_location_text) {
        this.pick_up_location_text = pick_up_location_text;
    }
    public String getDrop_off_location_text() {
        return drop_off_location_text;
    }
    public void setDrop_off_location_text(String drop_off_location_text) {
        this.drop_off_location_text = drop_off_location_text;
    }
    public String getRider_full_name() {
        return rider_full_name;
    }
    public void setRider_full_name(String rider_full_name) {
        this.rider_full_name = rider_full_name;
    }
    public String getRide_request_id() {
        return ride_request_id;
    }
    public void setRide_request_id(String ride_request_id) {
        this.ride_request_id = ride_request_id;
    }



}
