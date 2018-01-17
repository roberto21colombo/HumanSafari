package com.example.roberto.humansafari.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.roberto.humansafari.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

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

        //Creo il file corrispondente
        // /data/user/0/com.example.roberto.humansafari/files/myfile
        File file = new File(this.getFilesDir(), "myfile");

        String string = gameName+";"+playerName+";"+data+"\n"; //riga da salvare

        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(file.getName(), Context.MODE_APPEND); //in questo modo non cancella i dati vecchi
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //TODO aggiungere al DB il nuovo giocatore

        startActivity(new Intent(JoinGameActivity.this, PlayerChooseGameActivity.class));

    }

}


