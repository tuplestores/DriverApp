package com.tuplestores.driverapp.services;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tuplestores.driverapp.utils.Constants;
import com.tuplestores.driverapp.utils.NotificationUtil;
import com.tuplestores.driverapp.utils.UtilityFunctions;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;


public class FireBaseService extends FirebaseMessagingService {

    private static final String TAG = "FireBaseService";



    LocalBroadcastManager broadcaster;

    // [START_EXCLUDE]
    // There are two types of messages data messages and notification messages. Data messages
    // are handled
    // here in onMessageReceived whether the app is in the foreground or background. Data
    // messages are the type
    // traditionally used with GCM. Notification messages are only received here in
    // onMessageReceived when the app
    // is in the foreground. When the app is in the background an automatically generated
    // notification is displayed.
    // When the user taps on the notification they are returned to the app. Messages
    // containing both notification
    // and data payloads are treated as notification messages. The Firebase console always
    // sends notification
    // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
    // [END_EXCLUDE]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getData().size() > 0){
            //handle the data message here
        }

        broadcaster = LocalBroadcastManager.getInstance(this);

        //getting the title and the body
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

       // NotificationUtil.getInstance(this).displayNotification(title,body);

        if(body!=null){
            if(UtilityFunctions.currentActivity!=null) {

                if (UtilityFunctions.currentActivity.equals("HOME")) {
                    Intent intent = new Intent(Constants.ACTION_TAXI_DISPATCH_FIREBASE_TRIP_R);
                    intent.putExtra(Constants.EXTRA_TAXI_DISPATCH_FIREBASE_TRIP_M, body);
                    broadcaster.sendBroadcast(intent);
                }
            }
        }

    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d(TAG,"Registration Tocken for the device :"+s);

        Log.d(TAG,"Registration Tocken for the device :"+s);


    }
}
