package com.swiftpot.dailytextvoice.fragments;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.swiftpot.dailytextvoice.NotificationActivityHandler;
import com.swiftpot.dailytextvoice.R;
import com.swiftpot.dailytextvoice.services.HotWordDetectorService;

import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment {

    TextToSpeech textToSpeech;
    EditText edtTextStringToBeRead;
    FloatingActionButton fabReadAloud;
    ImageButton btnStartService;
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
        fabReadAloud =(FloatingActionButton) view.findViewById(R.id.fabReadAloud);
        btnStartService = (ImageButton) view.findViewById(R.id.btnStartService);
        edtTextStringToBeRead = (EditText) view.findViewById(R.id.edtTextStringToBeRead);
        textToSpeech = new TextToSpeech(getActivity().getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });

        btnStartService.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), HotWordDetectorService.class);
//                getActivity().startService(intent);
                setStickyNotification();
                Log.i(getClass().getName(), "Set Sticky notif!");
            }
        });

        fabReadAloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = edtTextStringToBeRead.getText().toString();
                Toast.makeText(getContext(), toSpeak, Toast.LENGTH_SHORT).show();
                textToSpeech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    @Override
    public void onDestroy() {
        if(textToSpeech !=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    public void setOnclickForPlay(View v){
        Toast.makeText(getContext(),"hello Daily Text Voice",Toast.LENGTH_LONG).show();
    }
    /*TODO set on click of notification buttons */
    private void setStickyNotification() {
        int notificationID = 2000;
        // Using RemoteViews to bind custom layouts into Notification
        RemoteViews remoteViews = new RemoteViews(getActivity().getPackageName(),
                R.layout.notification_view);

        Intent notifHandlder=new Intent(getActivity(), NotificationActivityHandler.class);

        notifHandlder.putExtra("PLAY_DAILY_TEXT", "1");
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 1, notifHandlder, 0);

        remoteViews.setOnClickPendingIntent(R.id.btnPlay,pendingIntent);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext());
        mBuilder.setSmallIcon(R.drawable.ic_action_volume_up);
        mBuilder.setContentTitle("DailyTextVoice");
        mBuilder.setContentText("Todays text is at Proverbs 3:5");
        mBuilder.setOngoing(true);
        mBuilder.setContent(remoteViews);

        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(notificationID, mBuilder.build());
    }
}
