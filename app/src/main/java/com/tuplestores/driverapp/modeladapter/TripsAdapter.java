/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp.modeladapter;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuplestores.driverapp.R;
import com.tuplestores.driverapp.model.TripsModel;

import org.w3c.dom.Text;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/*Created By Ajish Dharman on 02-July-2019
 *
 *
 */
public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.TripsViewHolder> {

    List<TripsModel> lstTrips;

    public TripsAdapter(List<TripsModel> lstTrips) {
        this.lstTrips = lstTrips;
    }

    @NonNull
    @Override
    public TripsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trips_listview_rowitem, parent, false);

        return new TripsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TripsViewHolder holder, int position) {

        TripsModel tm = lstTrips.get(position);
        holder.tv_tripstart.setText(tm.getPick_up_location_text());
        holder.tv_tripend.setText(tm.getDrop_off_location_text());
        holder.tv_trip_total_charge.setText(tm.getTrips_charge());
    }

    @Override
    public int getItemCount() {

        return lstTrips.size();
    }

    public class TripsViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_tripstart;
        private TextView tv_tripend;
        private TextView tv_trip_total_charge;
        private ImageView  img_trips_start;

        public TripsViewHolder( View itemView) {
            super(itemView);
            this.tv_tripstart = (TextView)itemView.findViewById(R.id.tv_tripstart);
            this.tv_tripend = (TextView)itemView.findViewById(R.id.tv_tripend);
            this.tv_trip_total_charge = (TextView)itemView.findViewById(R.id.tv_trip_total_charge);
            this.img_trips_start = (ImageView)itemView.findViewById(R.id.img_trips_start);
        }
    }
}
