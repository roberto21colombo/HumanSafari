package com.example.roberto.humansafari;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class ListCharacter extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, AdapterView.OnItemClickListener {

    boolean userPermission = false;
    Boolean googlePlayServiceConnected = false;

    GoogleApiClient mGoogleApiClient = null;

    ListView mListView;
    LatLng lastPosition = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_character);

        checkPermission();
        connectGoogleApiClient();


        mListView = (ListView) findViewById(R.id.listViewCharacters);
        mListView.setAdapter(new CustomAdapterCharacters(this, R.layout.raw_character, Model.getInstance().getCharacter()));
        mListView.setOnItemClickListener(this);



    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(Model.getInstance().getCharacter().get(i).isCatchable()) {
            changePlayerScore(i);
            changeLastCharacterPosition(i);
            changeTimeAbleCharacter(i);

            startActivity(new Intent(ListCharacter.this, HomeActivity.class));
        }else{
            long deltaTime = Model.getInstance().getCharacter().get(i).getDeltaTime();
            long seconds = TimeUnit.MILLISECONDS.toSeconds((int)deltaTime);
            Toast.makeText(this, "Devi attendere " +(-seconds)+ " secondi.", Toast.LENGTH_LONG).show();
        }
    }

    public void changePlayerScore(int i){
        int characterPoint = Model.getInstance().getCharacter().get(i).getPoints();
        int playerPoint = Model.getInstance().getScore();
        Model.getInstance().setScore(characterPoint + playerPoint);
        ServerConnections.setScore(ListCharacter.this);
    }

    public void changeLastCharacterPosition(int i) {
        if (userPermission && googlePlayServiceConnected) {

            // TODO: Consider calling
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                double lat = mLastLocation.getLatitude();
                double lng = mLastLocation.getLongitude();
                lastPosition = new LatLng(lat, lng);
            }

        }
        Model.getInstance().getCharacter().get(i).setLastPosition(lastPosition);
        ServerConnections.changePosition(i, ListCharacter.this);
    }

    public void changeTimeAbleCharacter(int i) {
        long time = Calendar.getInstance().getTime().getTime() + 30000;
        Model.getInstance().getCharacter().get(i).setTime(time);
        ServerConnections.changeTime(i, ListCharacter.this);
    }





    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 21);
        } else {
            userPermission = true;
        }
    }

    private void connectGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        }mGoogleApiClient.connect();
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 21){
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                userPermission = true;
            }
            return;
        }
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
