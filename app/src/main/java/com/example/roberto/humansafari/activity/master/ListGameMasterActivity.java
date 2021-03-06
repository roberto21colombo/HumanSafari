package com.example.roberto.humansafari.activity.master;

import android.content.Intent;
    import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.roberto.humansafari.activity.MainActivity;
import com.example.roberto.humansafari.adapter.CustomAdapterGames;
import com.example.roberto.humansafari.Model;
import com.example.roberto.humansafari.R;
import com.example.roberto.humansafari.ServerConnections;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ListGameMasterActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    FloatingActionButton btnAddButton;
    ListView listViewGames;

    Boolean isUserInfo = false, isCharacter = false, isHistorical = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_game);

        findViewById(R.id.progressBarGame).setVisibility(View.INVISIBLE);

        ((TextView)findViewById(R.id.title)).setText("Gestisci una Partita");
        listViewGames = (ListView) findViewById(R.id.lvGames);
        btnAddButton = (FloatingActionButton) findViewById(R.id.btnNewCharacter);
        btnAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListGameMasterActivity.this, CreateGameActivity.class));
            }
        });

        //Leggo dallo storage quali partite l'utente aveva già creato e carico l'array list
        ArrayList<String[]> arrayList = readFromInternalStorage();
        //Mostro la lista di partite con l'adapter
        listViewGames.setAdapter(new CustomAdapterGames(this, R.layout.raw_game_master, arrayList));
        listViewGames.setOnItemClickListener(this);

    }

    private ArrayList<String[]> readFromInternalStorage(){
        //Creo Arraylist dove ogni elemento è una partita espresso come array di stringa [NomePartita,NomeGiocatore,Data]
        ArrayList<String[]> arrayList = new ArrayList<String[]>();
        try {
            File fl = new File(this.getFilesDir(),"master_games_file");
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
        return arrayList;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //Leggo il nome della partita che vuole gestire
        String gameName = readFromInternalStorage().get(i)[0];
        String codChar = readFromInternalStorage().get(i)[1];
        //Salvo il nomepartita nel model
        Model.getInstance().setGameName(gameName);
        Model.getInstance().setCodCharacters(codChar);

        findViewById(R.id.progressBarGame).setVisibility(View.INVISIBLE);
        getUserInfo();
        getCharacters();
    }

    public void getUserInfo(){
        ServerConnections.getUsers
            (new Response.Listener<String>(){
                @Override
                public void onResponse(String response) {
                    Model.getInstance().setUsers(response);
                    isUserInfo = true;
                    if(isCharacter && isUserInfo) {
                        findViewById(R.id.progressBarGame).setVisibility(View.INVISIBLE);
                        startActivity(new Intent(ListGameMasterActivity.this, MasterMainActivity.class));
                    }
                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }, Volley.newRequestQueue(this)
        );
    }

    public void getCharacters(){
        //Scarico le informazioni dei character
        ServerConnections.downloadCharacters(
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("onResponse", response);
                        //Salvo le informazioni nel model
                        Model.getInstance().setCharacters(response);
                        isCharacter = true;
                        if(isCharacter && isUserInfo){
                            findViewById(R.id.progressBarGame).setVisibility(View.INVISIBLE);
                            startActivity(new Intent(ListGameMasterActivity.this, MasterMainActivity.class));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("errorResponse", error.toString());
                    }
                },
                Volley.newRequestQueue(this)
        );
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ListGameMasterActivity.this, MainActivity.class));
    }
}
