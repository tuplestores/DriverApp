/*
 * Copyright (c) 2019. TUPLE STORES .All Rights Reserved
 */

package com.tuplestores.driverapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/*Created By Ajish Dharman on 10-July-2019
 *
 *
 */public class UtilityFunctions {

     public static String driver_id;
     public static String tenant_id;
     public static String v_id;
     public static final String SHARED_P = "private_shared_peref";

     public static boolean getSharedPreferenceOfDriver(Context ctx){


        SharedPreferences sharedPreferences =  ctx.getApplicationContext().getSharedPreferences(SHARED_P,0);
         //v_id = sharedPreferences.getString("v_id","");
         driver_id = sharedPreferences.getString("driver_id","");
         tenant_id = sharedPreferences.getString("tenant_id","");

         if(driver_id!=null && !driver_id.equals("")
                 && tenant_id!=null && !tenant_id.equals("")){

             return true;
         }
        else{
             return  false;
         }

     }

    public static boolean getSharedPreferenceOfVehicle(Context ctx){


        SharedPreferences sharedPreferences =  ctx.getApplicationContext().getSharedPreferences(SHARED_P,0);
        String v_id = sharedPreferences.getString("v_id","");
        if(v_id!=null && !v_id.equals("")){

            return true;
        }
        else{
            return  false;
        }

    }




    public static boolean getAllSharedPrefValues(Context ctx){

         SharedPreferences sharedPreferences =  ctx.getApplicationContext().getSharedPreferences(SHARED_P,0);
         v_id = sharedPreferences.getString("v_id","");
         driver_id = sharedPreferences.getString("driver_id","");
         tenant_id = sharedPreferences.getString("tenant_id","");

         if(driver_id!=null && !driver_id.equals("")
                 && tenant_id!=null && !tenant_id.equals("") && v_id!=null && !v_id.equals("") ){

             UtilityFunctions.tenant_id = tenant_id;
             UtilityFunctions.driver_id = driver_id;
             UtilityFunctions.v_id = v_id;
             return true;
         }
         else{
             return  false;
         }

     }


    public static boolean setSharedPreferenceDriver(Context ctx,String driver_id,String tenant_id){

         try {
             ctx.getApplicationContext().getSharedPreferences(SHARED_P, 0);

             SharedPreferences sharedPreferences = ctx.getApplicationContext().getSharedPreferences(SHARED_P, 0);
             SharedPreferences.Editor editor = sharedPreferences.edit();
             editor.putString("driver_id", driver_id);
             editor.putString("tenant_id", tenant_id);
             editor.commit();
             return true;
         }
         catch (Exception ex){
             return  false;
         }
    }

    public static boolean setSharedPreferenceVehicle(Context ctx,String v_id){

        try {
            ctx.getApplicationContext().getSharedPreferences(SHARED_P, 0);

            SharedPreferences sharedPreferences = ctx.getApplicationContext().getSharedPreferences(SHARED_P, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("v_id", v_id);
            editor.commit();
            return true;
        }
        catch (Exception ex){
            return  false;
        }
    }


    public static boolean clearAllPreferenceValues(Context ctx){

        try {
            ctx.getApplicationContext().getSharedPreferences(SHARED_P, 0);

            SharedPreferences sharedPreferences = ctx.getApplicationContext().getSharedPreferences(SHARED_P, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("driver_id", "");
            editor.putString("tenant_id", "");
            editor.putString("v_id", "");
            editor.commit();
            return true;
        }
        catch (Exception ex){
            return  false;
        }
    }

}
