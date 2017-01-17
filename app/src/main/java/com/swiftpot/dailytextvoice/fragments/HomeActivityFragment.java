package com.swiftpot.dailytextvoice.fragments;

import android.content.Intent;
import android.content.IntentFilter;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.swiftpot.dailytextvoice.R;
import com.swiftpot.dailytextvoice.services.HotWordDetectorService;
import com.swiftpot.dailytextvoice.services.TaskWakefulBroadcastReciever;

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
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });

        btnStartService.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HotWordDetectorService.class);
                getActivity().startService(intent);
                Log.i(getClass().getName(), "onClick of start service button : service started");
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
    public void onPause() {
        if(textToSpeech !=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }
}
