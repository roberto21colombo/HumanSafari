package com.example.roberto.humansafari;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ListCharacter extends AppCompatActivity {

    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_character);

        mListView = (ListView)findViewById(R.id.mListView);


        //mListView.setAdapter(new ArrayAdapter<String> ( this, R.layout.raw_character, R.id.miotextView1, new String[]{"ciao", "mamma","come","stai","io","molto","bene","grazie","a","tutti"} ));
        CustomAdapterCharacters customAdapterCharacters = new CustomAdapterCharacters(this, R.layout.raw_character, Model.getInstance().getCharacter());
        mListView.setAdapter(customAdapterCharacters);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tvp = (TextView) findViewById(R.id.editTextPoint);
                String s = tvp.getText().toString();
                int characterPoint = Integer.parseInt(s);
                int playerPoint = Model.getInstance().getScore();

                Model.getInstance().setScore(characterPoint+playerPoint);

                startActivity(new Intent(ListCharacter.this, HomeActivity.class));
            }
        });

    }


}
