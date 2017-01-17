package com.swiftpot.dailytextvoice.services;/**
 * Created by Ace Programmer Rbk<rodney@swiftpot.com> on 17-Jan-17
 * 10:21 AM
 */

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by Ace Programmer Rbk<rodney@swiftpot.com> on 17-Jan-17
 * 10:21 AM
 */
public class TaskWakefulBroadcastReciever extends WakefulBroadcastReceiver {
    public static final String ACTION_RESP = "MY_FILTER_NAME";
    public static final String TAG = "WakefulBroadcast";

    public TaskWakefulBroadcastReciever() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Start the service, keeping the device awake while the service is
        // launching. This is the Intent to deliver to the service.
    }
}
