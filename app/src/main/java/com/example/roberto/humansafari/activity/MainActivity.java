package com.example.roberto.humansafari.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.roberto.humansafari.R;
import com.example.roberto.humansafari.activity.character.CharacterLogin;
import com.example.roberto.humansafari.activity.master.ListGameMasterActivity;
import com.example.roberto.humansafari.activity.player.ListGamePlayerActivity;
import com.onesignal.OneSignal;

public class MainActivity extends AppCompatActivity {

    Button btnPlay, btnManage, btnHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = (Button)findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ListGamePlayerActivity.class));
            }
        });

        btnManage = (Button)findViewById(R.id.btnManage);
        btnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ListGameMasterActivity.class));
            }
        });

        btnHide = (Button)findViewById(R.id.btnNasconditi);
        btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CharacterLogin.class));
            }
        });

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }

    @Override
    public void onBackPressed() {
        // stayHere.
    }
}
