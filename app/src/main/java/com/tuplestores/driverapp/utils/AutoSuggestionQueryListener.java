/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp.utils;

import com.here.android.mpa.search.AutoSuggest;
import com.here.android.mpa.search.AutoSuggestPlace;
import com.here.android.mpa.search.ErrorCode;
import com.here.android.mpa.search.ResultListener;
import com.here.android.mpa.search.TextAutoSuggestionRequest;

import java.util.List;

// Example request listener
public class AutoSuggestionQueryListener implements ResultListener<List<AutoSuggest>> {

    @Override
    public void onCompleted(List<AutoSuggest> data, ErrorCode error) {

        AutoSuggest autoSuggest = data.get(0);

        // set title
        String title = autoSuggest.getTitle();
       if(autoSuggest instanceof AutoSuggestPlace){

           AutoSuggestPlace ap = (AutoSuggestPlace)autoSuggest;
           double lat =  ap.getPosition().getAltitude();
           double lon = ap.getPosition().getLongitude();

       }
        for (AutoSuggest r : data) {
            try {


            } catch (IllegalArgumentException ex) {
                //Handle invalid create search request parameters
            }
        }
    }
}