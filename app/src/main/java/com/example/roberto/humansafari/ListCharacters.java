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

        CustomAdapterCharacters customAdapterCharacters = new CustomAdapterCharacters(this, R.layout.raw_character, Model.getInstance().getCharacter());
        listViewCharacters.setAdapter(customAdapterCharacters);
    }

}
