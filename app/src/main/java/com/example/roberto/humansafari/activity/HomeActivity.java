package com.example.roberto.humansafari.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roberto.humansafari.Model;
import com.example.roberto.humansafari.R;
import com.example.roberto.humansafari.ServerConnections;

public class HomeActivity extends AppCompatActivity {

    ImageView imageButtonSafari;
    ImageView imageViewMap;
    ImageView imageViewInstructions;
    ImageView imageViewRank;

    TextView score;
    TextView username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.d("oooo","scrivi qualcosa");

        score = (TextView)findViewById(R.id.score);
        username = (TextView)findViewById(R.id.username);

        imageButtonSafari = (ImageView) findViewById(R.id.imgButtonFound);
        imageButtonSafari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Model.getInstance().getDown("downChar")) {
                    startActivity(new Intent(HomeActivity.this, ListCharacterActivity.class));
                }else{
                    Toast.makeText(HomeActivity.this, "Download in corso, attendere...", Toast.LENGTH_LONG).show();
                }
            }
        });

        imageViewMap = (ImageView) findViewById(R.id.imageViewMap);
        imageViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Model.getInstance().getDown("downChar")) {
                    startActivity(new Intent(HomeActivity.this, MapActivity.class));
                }else{
                    Toast.makeText(HomeActivity.this, "Download in corso, attendere...", Toast.LENGTH_LONG).show();
                }
            }
        });

        imageViewInstructions = (ImageView) findViewById(R.id.imageViewInstructions);
        imageViewInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Model.getInstance().getDown("downHist")) {
                    startActivity(new Intent(HomeActivity.this, FoundHistoricalActivity.class));
                }else{
                    Toast.makeText(HomeActivity.this, "Download in corso, attendere...", Toast.LENGTH_LONG).show();
                }
            }
        });

        imageViewRank = (ImageView) findViewById(R.id.imageViewRank);
        imageViewRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Model.getInstance().getDown("downUsr")) {
                    startActivity(new Intent(HomeActivity.this, ListRankActivity.class));
                }else{
                    Toast.makeText(HomeActivity.this, "Download in corso, attendere...", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        score.setText("Score: " + Model.getInstance().getScore());
        username.setText("" + Model.getInstance().getUserName());


        Model.getInstance().setDown("downChar", false);
        Model.getInstance().setDown("downUsr", false);
        Model.getInstance().setDown("downHist", false);
        //ServerConnections.downloadCharacters(HomeActivity.this, Model.getInstance().getGameName());
        ServerConnections.getUsers(HomeActivity.this);
        ServerConnections.getHistorical(HomeActivity.this);
    }
}
