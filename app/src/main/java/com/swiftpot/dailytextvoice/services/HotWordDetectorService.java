package com.swiftpot.dailytextvoice.services;/**
 * Created by Ace Programmer Rbk<rodney@swiftpot.com> on 17-Jan-17
 * 10:35 AM
 */

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.swiftpot.dailytextvoice.R;

/**
 * Created by Ace Programmer Rbk<rodney@swiftpot.com> on 17-Jan-17
 * 10:35 AM
 */
public class HotWordDetectorService extends Service {

    private long TIME_PERIOD = 10000;
    private static String TAG="HotWordService";


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onHandleIntent: inside on handle intent now!! ");
        final Handler handler = new Handler();
        Runnable runnableCode = new Runnable() {
            @Override
            public void run() {
                // Do something here on the main thread
                new Runnable(){
                    @Override
                    public void run() {
                        Log.d("Handlers", "Called on main thread");
                        Log.v("FragmentSound", "Initializing sounds...");
                        final MediaPlayer mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.open_app_acknowledgement_sound);
                        mediaPlayer.start();
                        Toast.makeText(getBaseContext(),"Hello,I am obeying the commands every "+String.valueOf(TIME_PERIOD)+"seconds",Toast.LENGTH_LONG).show();
                    }
                }.run();



                handler.postDelayed(this, TIME_PERIOD);
            }
        };
        handler.post(runnableCode);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("Handlers", "Called on main thread");
        Log.v("FragmentSound", "Initializing sounds...");
        final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.service_stopped);
        Toast.makeText(getApplicationContext(),"HotWord DetectorServiceStopped!!",Toast.LENGTH_LONG).show();
        mediaPlayer.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
