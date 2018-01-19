package com.example.roberto.humansafari.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.roberto.humansafari.Model;
import com.example.roberto.humansafari.R;
import com.example.roberto.humansafari.adapter.CustomAdapterRank;

/**
 * Created by roberto on 16/01/18.
 */

public class MasterPlayersFragment extends Fragment {
    private static final String TAG = "Giocatori ";

    ListView listViewRank;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_master_players, container, false);

        listViewRank = view.findViewById(R.id.listViewRank);
        listViewRank.setAdapter(new CustomAdapterRank(getContext(), R.layout.raw_rank, Model.getInstance().getUsers()));

        return view;
    }
}
