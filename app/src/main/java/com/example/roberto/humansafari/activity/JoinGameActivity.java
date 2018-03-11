package com.example.roberto.humansafari.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.roberto.humansafari.Model;
import com.example.roberto.humansafari.R;
import com.example.roberto.humansafari.ServerConnections;

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


    String gameName;
    String playerName;
    String data;
    @Override
    public void onClick(View view) {
        gameName= edGameName.getText().toString();
        playerName = edPlayerName.getText().toString();
        data = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "-" + (Calendar.getInstance().get(Calendar.MONTH)+1) + "-" + Calendar.getInstance().get(Calendar.YEAR);



        ServerConnections.isGamePlayerExist(playerName, gameName, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                if(response.equals("game")){
                    Toast.makeText(JoinGameActivity.this, "Partita non esitente", Toast.LENGTH_LONG).show();
                }else if(response.equals("name")){
                    Toast.makeText(JoinGameActivity.this, "Giocatore già esistente", Toast.LENGTH_LONG).show();
                }else{
                    aggiungiPartita();
                }
            }
        }, Volley.newRequestQueue(this));
    }


    private void aggiungiPartita(){
        ServerConnections.addPlayer(playerName, gameName, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("onResponse", response);

                        salvaInLocale();

                        Model.getInstance().setPlayerName(playerName);
                        Model.getInstance().setGameName(gameName);
                        Intent intent = new Intent(JoinGameActivity.this, ListGamePlayerActivity.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("errorResponse", error.toString());
                    }
                },
                Volley.newRequestQueue(JoinGameActivity.this));
    }

    private void salvaInLocale(){
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
    }
}


