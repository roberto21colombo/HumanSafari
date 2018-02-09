package com.example.roberto.humansafari.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.roberto.humansafari.Model;
import com.example.roberto.humansafari.ServerConnections;
import com.example.roberto.humansafari.adapter.CustomAdapterGames;
import com.example.roberto.humansafari.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ListGamePlayerActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    FloatingActionButton btnAddButton;
    ListView listViewGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_game);

        listViewGames = (ListView) findViewById(R.id.lvGames);
        listViewGames.setOnItemClickListener(this);
        btnAddButton = (FloatingActionButton) findViewById(R.id.btnNewCharacter);
        btnAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListGamePlayerActivity.this, JoinGameActivity.class));
            }
        });


        //Creo Arraylist dove ogni elemento è una partita espresso come array di stringa [NomePartita,NomeGiocatore,Data]
        ArrayList<String[]> arrayList = new ArrayList<String[]>();
        try {
            File fl = new File(this.getFilesDir(),"myfile");
            FileInputStream fin = new FileInputStream(fl);

            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
            String mLine = null;
            while ((mLine = reader.readLine()) != null) {
                //Per ogni riga del file aggiungo elemento all'arrayList
                arrayList.add(new String[]{mLine.split(";")[0],mLine.split(";")[1],mLine.split(";")[2]});
            }
            reader.close();

            fin.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Assegno l'adapter alla list view
        listViewGames.setAdapter(new CustomAdapterGames(this, R.layout.raw_game_player, arrayList));

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String name = ((TextView)view.findViewById(R.id.rawPlayer)).getText().toString();
        String game = ((TextView)view.findViewById(R.id.rawGameName)).getText().toString();

        Model.getInstance().setPlayerName(name);
        Model.getInstance().setGameName(game);

        ServerConnections.getUserInfo(name, game,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("onResponse", response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            String playerName = jsonArray.getString(0);
                            String gameName = jsonArray.getString(1);
                            int point = jsonArray.getInt(2);

                            Model.getInstance().setPlayerName(playerName);
                            Model.getInstance().setGameName(gameName);
                            Model.getInstance().setScore(point);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("errorResponse", error.toString());
                    }
                },
                Volley.newRequestQueue(this));

        //TODO Scaricare i personaggi relativi alla partita
        ServerConnections.downloadCharacters(
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("onResponse", response);
                        //Salvo le informazioni nel model
                        Model.getInstance().setCharacters(response);
                        Model.getInstance().setDown("downChar", true);
                        //Una volta salvati i dati chiamo l'activity successiva
                        //TODO crasha quando chiama la nuova activity
                        startActivity(new Intent(ListGamePlayerActivity.this, PlayerMainActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("errorResponse", error.toString());
                    }
                },
                Volley.newRequestQueue(this));

    }
}
