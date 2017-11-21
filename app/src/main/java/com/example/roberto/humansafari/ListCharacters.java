package com.example.roberto.humansafari;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;

import android.widget.AdapterView.OnItemClickListener;

import java.lang.*;


public class ListCharacters extends AppCompatActivity {

    ListView listViewCharacters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_characters);
        Log.d("ListCharacter", "OnCreate");

        listViewCharacters = (ListView) findViewById(R.id.listViewCharacter);

        CustomAdapterCharacters customAdapterCharacters = new CustomAdapterCharacters(this, R.layout.raw_character, Model.getInstance().getCharacter());
        listViewCharacters.setAdapter(customAdapterCharacters);

        listViewCharacters.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("oooo","funziona");
            }
        });

    }

}
