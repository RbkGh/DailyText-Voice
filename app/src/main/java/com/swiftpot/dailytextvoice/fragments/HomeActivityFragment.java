package com.swiftpot.dailytextvoice.fragments;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RemoteViews;

import com.swiftpot.dailytextvoice.NotificationActivityHandler;
import com.swiftpot.dailytextvoice.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment {

    public HomeActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setStickyNotification(getContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /*TODO set on click of notification buttons */
    public static void setStickyNotification(Context context) {
        int notificationID = 2000;
        // Using RemoteViews to bind custom layouts into Notification
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.notification_view);

        Intent notifHandlder=new Intent(context, NotificationActivityHandler.class);
        notifHandlder.putExtra("PLAY_DAILY_TEXT", "1");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, notifHandlder, 0);
        remoteViews.setOnClickPendingIntent(R.id.btnPlay,pendingIntent);

        Intent notifStopHandlder=new Intent(context, NotificationActivityHandler.class);
        notifStopHandlder.putExtra("PLAY_DAILY_TEXT", "2");
        PendingIntent pendingCloseIntent = PendingIntent.getBroadcast(context, 2, notifStopHandlder, 0);
        remoteViews.setOnClickPendingIntent(R.id.btnStop,pendingCloseIntent);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ic_action_volume_up);
        mBuilder.setContentTitle("DailyTextVoice");
        mBuilder.setContentText("Todays text is at Proverbs 3:5");
        mBuilder.setOngoing(true);
        mBuilder.setContent(remoteViews);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(notificationID, mBuilder.build());
    }
}
