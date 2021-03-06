package com.swiftpot.dailytextvoice;/**
 * Created by Ace Programmer Rbk<rodney@swiftpot.com> on 20-Jan-17
 * 11:30 PM
 */

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.WakefulBroadcastReceiver;
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
public class NotificationActivityHandler extends WakefulBroadcastReceiver {
    TextToSpeech textToSpeech = null;
    private static String DEFAULT_DATE_PATTERN_EXPECTED = "yyyy/MM/dd";

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.i("LOG", "Here now,yet to process number");
        String action = (String) intent.getExtras().get("PLAY_DAILY_TEXT");
        Log.i("LOG", "launching action: " + action);
        assert action != null;
        if (action.equals("1")) {
            String acknowledgedMessage = "Hello ,a moment please,I'm getting today's text online..";
            Toast.makeText(context, acknowledgedMessage, Toast.LENGTH_SHORT).show();

            //talk(acknowledgedMessage, context);

            //use asynctask inline,not suitable for production
            new AsyncTask<Void, Void, DailyTextEntity>() {
                DailyTextEntity dailyTextEntity;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected void onPostExecute(DailyTextEntity dailyTextEntity) {
                    super.onPostExecute(dailyTextEntity);
                    if (dailyTextEntity == null) {
                        talk("Please ensure you have internet and try again.", context);
                    } else {
                        String startingSpeech = "Today's text : ";
                        String theme = dailyTextEntity.getDailyTextTheme();
                        String body = dailyTextEntity.getDailyTextMsgBody();
                        talk(startingSpeech + theme + body, context);
                    }
                }

                @Override
                protected DailyTextEntity doInBackground(Void... voids) {
                    DailyTextCrawler dailyTextCrawler = new DailyTextCrawler();
                    Date date = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN_EXPECTED);
                    String dateInExpectedFormat = simpleDateFormat.format(date);
                    Log.d("LOG", "dateInExpectedFormat: " + dateInExpectedFormat);
                    try {
                        dailyTextEntity = dailyTextCrawler.crawlForDailyText(dateInExpectedFormat);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return dailyTextEntity;
                }
            }.execute();


        } else if (action.equals("2")) {
            stopTalking(context);
        } else {
            //start athe main intent if required
        }
        completeWakefulIntent(intent);
    }


    void talk(final String speech, Context context) {
        if (getTextToSpeech() != null) {
            speak(speech);
        } else {
            textToSpeech = new TextToSpeech(context.getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    textToSpeech.setSpeechRate((float) 0.8);
                    textToSpeech.setLanguage(Locale.US);
                    if (status != TextToSpeech.ERROR) {
                        speak(speech);
                    }

                }
            });
            setTextToSpeech(textToSpeech);
        }

    }

    void stopTalking(Context context) {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        } else {
            textToSpeech.shutdown();
        }
    }

    /**
     * ensure deprecation in lollipop still runs
     *
     * @param text
     */
    private void speak(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public TextToSpeech getTextToSpeech() {
        return textToSpeech;
    }

    public void setTextToSpeech(TextToSpeech textToSpeech) {
        this.textToSpeech = textToSpeech;
    }
}
