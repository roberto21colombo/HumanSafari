package com.example.roberto.humansafari.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.roberto.humansafari.Character;
import com.example.roberto.humansafari.Model;
import com.example.roberto.humansafari.R;
import com.example.roberto.humansafari.activity.PlayerMainActivity;
import com.example.roberto.humansafari.adapter.CustomAdapterPlayerCharacters;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

/**
 * Created by roberto on 16/01/18.
 */

public class PlayerCharacterFragment extends Fragment implements AdapterView.OnItemClickListener  {
    private static final String TAG = "ListaCharacterPlayer";

    public ListView mListView;
    FloatingActionButton btnFindCharacter;
    public CustomAdapterPlayerCharacters mCustomAdapterPlayerCharacters = null;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_characters, container, false);



        mListView = view.findViewById(R.id.listViewCharacters);
        btnFindCharacter = view.findViewById(R.id.btnFindCharacter);
        btnFindCharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Inquadra il qrcode del personaggio");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.setOrientationLocked(false);
                integrator.initiateScan();
            }
        });
        ArrayList<Character> mArrayList = Model.getInstance().getCharacters();

        mCustomAdapterPlayerCharacters = new CustomAdapterPlayerCharacters(getActivity(), R.layout.raw_player_character, mArrayList);
        mListView.setAdapter(mCustomAdapterPlayerCharacters);
        mListView.setOnItemClickListener(this);


        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //TODO onclick sul personaggio, aprire mappa
        if(Model.getInstance().getCharacters().get(i).getLastPosition() != null) {
            Model.getInstance().setCharacterSelectedMap(i);
            ((PlayerMainActivity) getActivity()).tabLayout.getTabAt(2).select();
        }else{
            Toast.makeText(getContext(), "Personaggio non ancora trovato", Toast.LENGTH_LONG).show();
        }
    }



    public void notifyCheckboxChanged(String name) {
        mCustomAdapterPlayerCharacters.setFoundedTrue(name);
        mCustomAdapterPlayerCharacters.notifyDataSetChanged();
    }
}
