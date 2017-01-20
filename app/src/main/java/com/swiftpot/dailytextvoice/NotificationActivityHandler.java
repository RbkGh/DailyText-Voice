package com.swiftpot.dailytextvoice;/**
 * Created by Ace Programmer Rbk<rodney@swiftpot.com> on 20-Jan-17
 * 11:30 PM
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Ace Programmer Rbk<rodney@swiftpot.com> on 20-Jan-17
 * 11:30 PM
 */
public class NotificationActivityHandler extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String action= (String)getIntent().getExtras().get("PLAY_DAILY_TEXT");
        Log.i("LOG", "lauching action: " + action);
        assert action != null;
        if(action.equals("1")){
            Toast.makeText(getBaseContext(),"Play Button Clicked!!",Toast.LENGTH_LONG).show();
        }
    }
}
