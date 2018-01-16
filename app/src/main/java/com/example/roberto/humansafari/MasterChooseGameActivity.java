package com.example.roberto.humansafari;

import android.content.Intent;
    import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MasterChooseGameActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    FloatingActionButton btnAddButton;
    ListView listViewGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game);

        listViewGames = (ListView) findViewById(R.id.lvGames);
        btnAddButton = (FloatingActionButton) findViewById(R.id.btnMaterialAdd);
        btnAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MasterChooseGameActivity.this, CreateGameActivity.class));
            }
        });

        ArrayList<String[]> arrayList = readFromInternalStorage();
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
        startActivity(new Intent(MasterChooseGameActivity.this, MasterMainActivity.class));
    }
}
