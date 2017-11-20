package com.example.roberto.humansafari;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {

    ImageView imageButtonSafari;
    ImageView imageViewMap;
    ImageView imageViewInstructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imageButtonSafari = (ImageView) findViewById(R.id.imgButtonFound);
        imageButtonSafari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ListCharacters.class));
            }
        });

        imageViewMap = (ImageView) findViewById(R.id.imageViewMap);
        imageViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, MapActivity.class));
            }
        });

        imageViewInstructions = (ImageView) findViewById(R.id.imageViewInstructions);
        imageViewInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, InstructionsActivity.class));
            }
        });
    }


}
