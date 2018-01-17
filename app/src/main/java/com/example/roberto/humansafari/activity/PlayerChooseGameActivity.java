package com.example.roberto.humansafari.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.roberto.humansafari.adapter.CustomAdapterGames;
import com.example.roberto.humansafari.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PlayerChooseGameActivity extends AppCompatActivity {

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
                startActivity(new Intent(PlayerChooseGameActivity.this, JoinGameActivity.class));
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
        listViewGames.setAdapter(new CustomAdapterGames(this, R.layout.raw_game, arrayList));

    }
}
