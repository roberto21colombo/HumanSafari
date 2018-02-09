package com.example.roberto.humansafari.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.roberto.humansafari.Character;
import com.example.roberto.humansafari.Model;
import com.example.roberto.humansafari.R;
import com.example.roberto.humansafari.ServerConnections;
import com.example.roberto.humansafari.adapter.CustomAdapterMasterCharacters;
import com.example.roberto.humansafari.adapter.CustomAdapterPlayerCharacters;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

/**
 * Created by roberto on 16/01/18.
 */

public class PlayerCharacterFragment extends Fragment implements AdapterView.OnItemClickListener  {
    private static final String TAG = "Lista";

    ListView mListView;
    FloatingActionButton btnFindCharacter;




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
        ArrayList<Character> mArrayList = Model.getInstance().getCharacter();

        mListView.setAdapter(new CustomAdapterPlayerCharacters(getActivity(), R.layout.raw_player_character, mArrayList));
        mListView.setOnItemClickListener(this);


        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //TODO onclick sul personaggio, aprire mappa
    }

    /*
    //Metodo che viene chiamato quando il qrcode legge qualcosa
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(getContext(), "you cancelled scanning", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), result.getContents(), Toast.LENGTH_LONG).show();

                String nameCharacter = result.getContents().split(";")[0];
                String pointCharacter = result.getContents().split(";")[1];

                int indexCharacterList = Model.getInstance().getCharaterPositionWithName(nameCharacter);

                //myGoogleApi = new MyGoogleApi(HomeActivity.this);
                changePlayerScore(Integer.parseInt(pointCharacter));
                changeCharacterPosition(indexCharacterList);
                addFound(nameCharacter);

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
*/



}
