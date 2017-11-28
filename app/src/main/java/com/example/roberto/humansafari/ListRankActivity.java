package com.example.roberto.humansafari;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ListRankActivity extends AppCompatActivity {

    ListView listViewRank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_rank);


        listViewRank = (ListView)findViewById(R.id.listViewRank);
        listViewRank.setAdapter(new CustomAdapterRank(this, R.layout.raw_rank, Model.getInstance().getUsers()));
    }
}
