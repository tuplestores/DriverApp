/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp.model;

/*Created By Ajish Dharman on 02-July-2019
 *
 *
 */public class TripsModel {

     private String tripDate;
     private String pick_up_location_text;
     private String drop_off_location_text;
     private String trips_charge;

     public String getTripDate() {
          return tripDate;
     }

     public void setTripDate(String tripDate) {
          this.tripDate = tripDate;
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

     public String getTrips_charge() {
          return trips_charge;
     }

     public void setTrips_charge(String trips_charge) {
          this.trips_charge = trips_charge;
     }
}
