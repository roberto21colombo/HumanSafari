package com.example.roberto.humansafari;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.wearable.Asset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

public class JoinGameActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edGameName = null, edPlayerName = null;
    Button btnJoin = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);

        edGameName = (EditText)findViewById(R.id.gameName);
        edPlayerName = (EditText)findViewById(R.id.playerName);

        btnJoin = (Button)findViewById(R.id.btnJoinGame);
        btnJoin.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        String gameName = edGameName.getText().toString();
        String playerName = edPlayerName.getText().toString();
        String data = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "-" + (Calendar.getInstance().get(Calendar.MONTH)+1) + "-" + Calendar.getInstance().get(Calendar.YEAR);

        File file = new File(this.getFilesDir(), "myfile");

        String string = gameName+";"+playerName+";"+data+"\n";
        FileOutputStream outputStream;


        try {
            outputStream = openFileOutput(file.getName(), Context.MODE_APPEND);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        startActivity(new Intent(JoinGameActivity.this, ChooseGameActivity.class));

    }

}


