package com.example.edufutureapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.edufutureapp.R;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class EduRoomActivity extends AppCompatActivity {

    EditText codeBox;
    Button joinBtn, shareBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edu_room);

        codeBox = findViewById(R.id.codeBox);
        joinBtn = findViewById(R.id.joinBtn);
        shareBtn = findViewById(R.id.shareBtn);

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (codeBox.getText().toString().isEmpty())
                {
                    Toast.makeText(EduRoomActivity.this, "Please enter room ID", Toast.LENGTH_SHORT).show();
                }

                else
                {
                JitsiMeetConferenceOptions options
                        = new JitsiMeetConferenceOptions.Builder()
                        .setRoom(codeBox.getText().toString()).setFeatureFlag("welcomepage.enabled", false)
                        .build();
                JitsiMeetActivity.launch(EduRoomActivity.this, options);
            }}
        });

        URL serverURL = null;
        try {
            serverURL = new URL("");
            JitsiMeetConferenceOptions defaultOptions
                    = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(serverURL)
                    .setFeatureFlag("welcomepage.enabled", false)
                    .build();

            JitsiMeet.setDefaultConferenceOptions(defaultOptions);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }




        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = codeBox.getText().toString();
                Intent intent = new Intent();
                intent.setAction(intent.ACTION_SEND);
                intent.putExtra(intent.EXTRA_TEXT, string);
                intent.setType("text/plain");
                startActivity(intent);
            }});

            }

}