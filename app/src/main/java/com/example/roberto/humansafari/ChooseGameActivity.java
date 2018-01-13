package com.example.roberto.humansafari;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ChooseGameActivity extends AppCompatActivity {

    FloatingActionButton btnAddButton;
    ListView listViewGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game);

        btnAddButton = (FloatingActionButton) findViewById(R.id.btnMaterialAdd);
        btnAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseGameActivity.this, JoinGameActivity.class));
            }
        });



        ArrayList<String[]> arrayList = new ArrayList<String[]>();
        try {
            File fl = new File(this.getFilesDir(),"myfile");
            FileInputStream fin = new FileInputStream(fl);

            BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
            String mLine = null;
            while ((mLine = reader.readLine()) != null) {
                arrayList.add(new String[]{mLine.split(";")[0],mLine.split(";")[1],mLine.split(";")[2]});
            }
            reader.close();
            //Make sure you close all streams.
            fin.close();


            listViewGames = (ListView) findViewById(R.id.lvGames);
            listViewGames.setAdapter(new CustomAdapterGames(this, R.layout.raw_game, arrayList));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
