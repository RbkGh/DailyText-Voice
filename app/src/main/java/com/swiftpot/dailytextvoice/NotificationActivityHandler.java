package com.swiftpot.dailytextvoice;/**
 * Created by Ace Programmer Rbk<rodney@swiftpot.com> on 20-Jan-17
 * 11:30 PM
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.swiftpot.dailytext.crawler.DailyTextCrawler;
import com.swiftpot.dailytext.crawler.models.DailyTextEntity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ace Programmer Rbk<rodney@swiftpot.com> on 20-Jan-17
 * 11:30 PM
 */
public class NotificationActivityHandler extends AppCompatActivity {
    TextToSpeech textToSpeech;
    private static String DEFAULT_DATE_PATTERN_EXPECTED = "yyyy/MM/dd";

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
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //use asynctask inline,not suitable for production
                    new AsyncTask<Void, Void, DailyTextEntity>(){
                        DailyTextEntity dailyTextEntity;
                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                        }

                        @Override
                        protected void onPostExecute(DailyTextEntity dailyTextEntity) {
                            super.onPostExecute(dailyTextEntity);
                            String startingSpeech = "Today's text : ";
                            String theme = dailyTextEntity.getDailyTextTheme();
                            String body = dailyTextEntity.getDailyTextMsgBody();
                            talk(startingSpeech);
                            talk(theme);
                            talk(body);
                        }

                        @Override
                        protected DailyTextEntity doInBackground(Void... voids) {
                            DailyTextCrawler dailyTextCrawler = new DailyTextCrawler();
                            Date date = new Date();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN_EXPECTED);
                            String dateInExpectedFormat = simpleDateFormat.format(date);

                            try {
                                dailyTextEntity = dailyTextCrawler.crawlForDailyText(dateInExpectedFormat);

                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                    }.execute();

                }
            }).run();

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
