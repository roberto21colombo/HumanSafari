package com.example.roberto.humansafari;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {

    ImageView imageButtonSafari;
    ImageView imageViewMap;
    ImageView imageViewInstructions;

    TextView score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.d("oooo","scrivi qualcosa");

        score = (TextView)findViewById(R.id.score);

        imageButtonSafari = (ImageView) findViewById(R.id.imgButtonFound);
        imageButtonSafari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ListCharacter.class));
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

    @Override
    protected void onStart() {
        super.onStart();
        score.setText("Score: " + Model.getInstance().getScore());

        String url = "http://www.aclitriuggio.it/wp-pinguino/hello_world.php";
        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("onResponse", response);
                Model.getInstance().setCharacters(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}
