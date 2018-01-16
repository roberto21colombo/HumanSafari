package com.example.roberto.humansafari;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by roberto on 16/01/18.
 */

public class MasterPlayersFragment extends Fragment {
    private static final String TAG = "Giocatori ";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_master_players, container, false);

        return view;
    }
}
