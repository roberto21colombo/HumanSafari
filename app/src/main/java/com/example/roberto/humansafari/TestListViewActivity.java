package com.example.roberto.humansafari;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class TestListViewActivity extends AppCompatActivity {

    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list_view);

        mListView = (ListView)findViewById(R.id.mListView);


        //mListView.setAdapter(new ArrayAdapter<String> ( this, R.layout.mio_lay, R.id.miotextView1, new String[]{"ciao", "mamma","come","stai","io","molto","bene","grazie","a","tutti"} ));
        CustomAdapterCharacters customAdapterCharacters = new CustomAdapterCharacters(this, R.layout.mio_lay, Model.getInstance().getCharacter());
        mListView.setAdapter(customAdapterCharacters);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(TestListViewActivity.this, HomeActivity.class));
            }
        });

    }


}
