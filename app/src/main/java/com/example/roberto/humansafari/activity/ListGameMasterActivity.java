package com.example.roberto.humansafari.activity;

import android.content.Intent;
    import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_game_master);

        listViewGames = (ListView) findViewById(R.id.lvGames);
        btnAddButton = (FloatingActionButton) findViewById(R.id.btnMaterialAdd);
        btnAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListGameMasterActivity.this, CreateGameActivity.class));
            }
        });

        //Leggo dallo storage quali partite l'utente aveva già creato e carico l'array list
        ArrayList<String[]> arrayList = readFromInternalStorage();
        //Mostro la lista di partite con l'adapter
        listViewGames.setAdapter(new CustomAdapterGames(this, R.layout.raw_game, arrayList));
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
        String gameName = ((TextView)view.findViewById(R.id.rawGameName)).getText().toString();
        //Salvo il nomepartita nel model
        Model.getInstance().setGameName(gameName);


        //Scarico le informazioni dei character
        ServerConnections.downloadCharacters(
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("onResponse", response);
                        //Salvo le informazioni nel model
                        Model.getInstance().setCharacters(response);
                        Model.getInstance().setDown("downChar", true);
                        //Una volta salvati i dati chiamo l'activity successiva
                        startActivity(new Intent(ListGameMasterActivity.this, MasterMainActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("errorResponse", error.toString());
                    }
                },
                Volley.newRequestQueue(this));
        //TODO scarico le informazioni della partita: Giocatori
        //TODO memorizzo Giocatori nel Model

    }
}
