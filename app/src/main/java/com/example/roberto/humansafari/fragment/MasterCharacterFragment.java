package com.example.roberto.humansafari.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.roberto.humansafari.Character;
import com.example.roberto.humansafari.Model;
import com.example.roberto.humansafari.R;
import com.example.roberto.humansafari.activity.master.NewCharacterActivity;
import com.example.roberto.humansafari.adapter.CustomAdapterMasterCharacters;

import java.util.ArrayList;

/**
 * Created by roberto on 16/01/18.
 */

public class MasterCharacterFragment extends Fragment implements AdapterView.OnItemClickListener  {
    private static final String TAG = "Personaggi ";

    ListView mListView;
    FloatingActionButton btnAddCharacter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_master_characters, container, false);

        mListView = view.findViewById(R.id.listViewCharacters);
        btnAddCharacter = view.findViewById(R.id.btnNewCharacter);
        btnAddCharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), NewCharacterActivity.class));
            }
        });
        ArrayList<Character> mArrayList = Model.getInstance().getCharacters();

        mListView.setAdapter(new CustomAdapterMasterCharacters(getActivity(), R.layout.raw_master_character, mArrayList));
        mListView.setOnItemClickListener(this);


        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //TODO onclick sul personaggio, aprire pagina di modifica
        Intent intent = new Intent(getActivity(), NewCharacterActivity.class);
        intent.putExtra("character", i);
        getActivity().startActivity(intent);
    }
}
