package com.example.roberto.humansafari;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.lang.*;
import java.util.ArrayList;

public class ListCharacters extends AppCompatActivity {

    ListView listViewCharacters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_characters);

        listViewCharacters = (ListView) findViewById(R.id.listviewCharacters);

        CustomAdapterCharacters customAdapterCharacters = new CustomAdapterCharacters(this, R.layout.raw_character, getArrayList());
        listViewCharacters.setAdapter(customAdapterCharacters);
    }


    public ArrayList<Character> getArrayList(){
        ArrayList<Character> al = new ArrayList<Character>();

        al.add(new Character("Paolino", R.drawable.piero_pelu,30));
        al.add(new Character("Arturo", R.drawable.piero_pelu,30));
        al.add(new Character("Francesco", R.drawable.piero_pelu,30));
        al.add(new Character("Carlitos", R.drawable.piero_pelu,30));
        al.add(new Character("Genoveffo", R.drawable.piero_pelu,30));
        al.add(new Character("Antonio", R.drawable.piero_pelu,30));
        al.add(new Character("Biennio", R.drawable.piero_pelu,30));
        al.add(new Character("Roberto", R.drawable.piero_pelu,30));
        al.add(new Character("Dave", R.drawable.piero_pelu,30));
        al.add(new Character("Simone", R.drawable.piero_pelu,30));
        al.add(new Character("Lorenza", R.drawable.piero_pelu,30));

        return al;
    }
}
