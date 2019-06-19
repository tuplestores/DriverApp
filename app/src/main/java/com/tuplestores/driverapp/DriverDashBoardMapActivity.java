/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;


import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;


import java.util.Arrays;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DriverDashBoardMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    // map embedded in the map fragment
    private  GoogleMap mMap = null;
    // map fragment embedded in this activity
    private SupportMapFragment mapFragment = null;

    Button btnOk;
    EditText edtPlaceQuery;

    int AUTOCOMPLETE_REQUEST_CODE = 1;

    // Set the fields to specify which types of place data to
// return after the user has made a selection.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Initialize();

    }

    private void Initialize(){

        setContentView(R.layout.activity_driver_dash_board_map);

        String locale = this.getApplicationContext().getResources().getConfiguration().locale.getCountry();

        btnOk = (Button)(findViewById(R.id.btnOK)) ;

       Places.initialize(getApplicationContext(),getResources().getString(R.string.google_maps_key));

        // Search for the map fragment to finish setup by calling init().
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



       btnOk.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //GeoCoding click
               // Instantiate a GeoCoordinate object
              /* GeoCoordinate vancouver = new GeoCoordinate( 11.258753,75.780411);

              // Example code for creating a OneBox Request
               ResultListener<List<GeocodeResult>> listener = null;
               listener = (ResultListener)new GeocodeListener();
               GeocodeRequest request = new GeocodeRequest("Palayam").setSearchArea(vancouver, 5000);

               if (request.execute(listener) != ErrorCode.NONE) {
                   // Handle request error

               }*/


               // Create a request to search for restaurants in Seattle
               /*try {
                   GeoCoordinate seattle
                           = new GeoCoordinate( 11.258753,75.780411);

                   DiscoveryRequest request =
                           new SearchRequest("restaurant").setSearchCenter(seattle);

                   // limit number of items in each result page to 10
                   request.setCollectionSize(10);

                   ErrorCode error = request.execute(new SearchRequestListener());
                   if( error != ErrorCode.NONE ) {
                       // Handle request error

                   }
               } catch (IllegalArgumentException ex) {
                   // Handle invalid create search request parameters
               }*/
/*
               try {
                   GeoCoordinate seattle
                           = new GeoCoordinate( 11.258753,75.780411);

                   TextAutoSuggestionRequest request =
                           new TextAutoSuggestionRequest("bus").setSearchCenter(seattle);

                   // limit number of items in each result page to 10
                   request.setCollectionSize(10);

                   ErrorCode error = request.execute(new AutoSuggestionQueryListener());
                   if( error != ErrorCode.NONE ) {
                       // Handle request error

                   }
               } catch (IllegalArgumentException ex) {
                   // Handle invalid create search request parameters
               }*/



           }
       });



        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

// Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

// Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                //Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                double d = place.getLatLng().latitude;
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(),16.0f));

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
               // Log.i(TAG, "An error occurred: " + status);
            }
        });



    }




    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,16.0f));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
