package com.swiftpot.dailytextvoice;/**
 * Created by Ace Programmer Rbk<rodney@swiftpot.com> on 20-Jan-17
 * 11:30 PM
 */

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by Ace Programmer Rbk<rodney@swiftpot.com> on 20-Jan-17
 * 11:30 PM
 */
public class NotificationActivityHandler extends AppCompatActivity {
    TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String action = (String) getIntent().getExtras().get("PLAY_DAILY_TEXT");
        Log.i("LOG", "lauching action: " + action);
        assert action != null;
        if (action.equals("1")) {
            String acknowledgedMessage = "Hello Rod,a moment please,I'm getting today's text online..";
            Toast.makeText(getApplicationContext(), acknowledgedMessage, Toast.LENGTH_SHORT).show();
            talk(acknowledgedMessage);
            Log.d("Speaking", "Speaking");

        }
    }


    void talk(final String speech) {
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    textToSpeech.speak(speech,TextToSpeech.QUEUE_FLUSH,null);
                }
            }
        });
    }


    @Override
    public void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
