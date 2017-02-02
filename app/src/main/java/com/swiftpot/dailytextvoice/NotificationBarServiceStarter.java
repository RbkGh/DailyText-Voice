package com.swiftpot.dailytextvoice;/**
 * Created by Ace Programmer Rbk<rodney@swiftpot.com> on 02-Feb-17
 * 7:05 AM
 */

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.swiftpot.dailytextvoice.fragments.HomeActivityFragment;

/**
 * Created by Ace Programmer Rbk<rodney@swiftpot.com> on 02-Feb-17
 * 7:05 AM Use to acknowledge boot completed to set sticky notif.
 */
public class NotificationBarServiceStarter extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == "android.intent.action.BOOT_COMPLETED") {
            HomeActivityFragment.setStickyNotification(context);
        }
        completeWakefulIntent(intent);
    }
}
