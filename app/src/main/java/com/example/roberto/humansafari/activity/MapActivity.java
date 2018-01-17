package com.example.roberto.humansafari.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.roberto.humansafari.Character;
import com.example.roberto.humansafari.Model;
import com.example.roberto.humansafari.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationServices;

import java.lang.*;
import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    Boolean googlePlayServiceConnected = false;
    GoogleApiClient mGoogleApiClient = null;

    ArrayList<Character> arrayListCharacter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        arrayListCharacter = Model.getInstance().getCharacter();

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        }
        mGoogleApiClient.connect();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Log.d("MapActivity","On map ready");
        Toast toast = Toast.makeText(this, "Map Ready", Toast.LENGTH_LONG);
        toast.show();

        markFriends(map);

        LatLngBounds bounds = getLatLngBound();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.25); // offset from edges of the map 12% of screen

        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));



    }

    public void markFriends(GoogleMap map){
        for(Character c : arrayListCharacter){
            if(c.getLastPosition() != null) {
                map.addMarker(new MarkerOptions().position(c.getLastPosition())
                        .title(c.getName()));
            }
        }
    }

    public LatLngBounds getLatLngBound(){

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Character character : arrayListCharacter) {
            LatLng latLng = character.getLastPosition();
            if(latLng != null) {
                builder.include(latLng);
            }
        }
        return builder.build();


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        googlePlayServiceConnected = true;
    }

    @Override
    public void onConnectionSuspended(int i) {
        googlePlayServiceConnected = false;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        googlePlayServiceConnected = false;
    }
}
