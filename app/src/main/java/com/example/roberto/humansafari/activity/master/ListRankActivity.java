package com.example.roberto.humansafari.activity.master;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.roberto.humansafari.adapter.CustomAdapterRank;
import com.example.roberto.humansafari.Model;
import com.example.roberto.humansafari.R;

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
