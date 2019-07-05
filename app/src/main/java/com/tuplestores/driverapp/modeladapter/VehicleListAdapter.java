/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp.modeladapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuplestores.driverapp.R;
import com.tuplestores.driverapp.model.VehicleModel;

import java.util.List;

/*Created By Ajish Dharman on 01-July-2019
 *
 *
 */public class VehicleListAdapter extends BaseAdapter {

     List<VehicleModel> lstVehicle;
     Context context;
    LayoutInflater inflater;
     public VehicleListAdapter(List<VehicleModel> lstVehicle, Context ctx){

        this.lstVehicle = lstVehicle;
        this.context = ctx;
        inflater = LayoutInflater.from(this.context);
     }


    @Override
    public int getCount() {
        return  lstVehicle.size();
    }

    @Override
    public VehicleModel getItem(int position) {
        return this.lstVehicle.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.vehicle_list_row_item, parent, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        VehicleModel currentListData = getItem(position);

        mViewHolder.tvVehicleName.setText(currentListData.getVehicle_name());
        mViewHolder.tvPlateNumber.setText(currentListData.getPlate_number());

        return convertView;
    }

    private class MyViewHolder {
        TextView tvVehicleName, tvPlateNumber;

        public MyViewHolder(View item) {
            tvVehicleName = (TextView) item.findViewById(R.id.txtScannedVehlistName);
            tvPlateNumber = (TextView) item.findViewById(R.id.txtScannedVehlistPlate);
        }
    }
}
