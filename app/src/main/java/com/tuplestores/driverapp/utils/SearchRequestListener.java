/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp.utils;

import com.here.android.mpa.search.DiscoveryLink;
import com.here.android.mpa.search.DiscoveryResult;
import com.here.android.mpa.search.DiscoveryResultPage;
import com.here.android.mpa.search.ErrorCode;
import com.here.android.mpa.search.PlaceLink;
import com.here.android.mpa.search.ResultListener;

import java.util.List;

public class SearchRequestListener implements ResultListener<DiscoveryResultPage> {

    DiscoveryResultPage mResultPage = null;

    @Override
    public void onCompleted(DiscoveryResultPage data, ErrorCode error) {

        if (error != ErrorCode.NONE) {

            // Handle error

        } else {

            // Store the last DiscoveryResultPage for later processing
            mResultPage = data;
            //The results is a DiscoveryResultPage which represents a
            // paginated collection of items.
            List<DiscoveryResult> items = data.getItems();

            // Iterate through the found place items.
            // A Item can either be a PlaceLink (meta information
            // about a Place) or a DiscoveryLink (which is a reference
            // to another refined search that is related to the
            // original search; for example, a search for
            // "Leisure & Outdoor").
            for (DiscoveryResult item : items)
                if (item.getResultType() != DiscoveryResult.ResultType.PLACE) {
                    if (item.getResultType() == DiscoveryResult.ResultType.DISCOVERY) {
                        DiscoveryLink discoveryLink = (DiscoveryLink) item;

                        // DiscoveryLink can also be presented to the user.
                        // When a DiscoveryLink is selected, another search request should be
                        // performed to retrieve results for a specific category.

                    } else {

                     PlaceLink placeLink = (PlaceLink) item;

                    // PlaceLink should be presented to the user, so the link can be
                    // selected in order to retrieve additional details about a place
                    // of interest.

                }
        } else {
            // Handle search request error.
        }

        }

        //onCompleted
    }
}//Class
