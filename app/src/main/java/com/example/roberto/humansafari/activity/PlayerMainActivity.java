package com.example.roberto.humansafari.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.roberto.humansafari.Model;
import com.example.roberto.humansafari.R;
import com.example.roberto.humansafari.ServerConnections;
import com.example.roberto.humansafari.adapter.SectionsPageAdapterMaster;
import com.example.roberto.humansafari.adapter.SectionsPageAdapterPlayer;
import com.example.roberto.humansafari.fragment.FoundsListFragment;
import com.example.roberto.humansafari.fragment.PlayerCharacterFragment;
import com.example.roberto.humansafari.fragment.PlayerMapFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class PlayerMainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "PlayerMainActivity";

    public SectionsPageAdapterPlayer mSectionsPageAdapterPlayer;

    private ViewPager mViewPager;

    TextView tvGameName, tvPlayerName, tvScore;

    Boolean userPermission = false;
    Boolean googlePlayServiceConnected = false;
    GoogleApiClient mGoogleApiClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_main);

        checkPermission();
        connectGoogleApiClient();

        mSectionsPageAdapterPlayer = new SectionsPageAdapterPlayer(getSupportFragmentManager());
        mViewPager = (ViewPager)findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tvGameName = (TextView)findViewById(R.id.tvGameName);
        tvPlayerName = (TextView)findViewById(R.id.tvPlayerName);
        tvScore = (TextView)findViewById(R.id.tvCodCharacter);
        tvGameName.setText(Model.getInstance().getGameName());
        tvPlayerName.setText(Model.getInstance().getPlayerName());
        tvScore.setText(Model.getInstance().getScore() + " Punti");


    }

    public void setupViewPager(ViewPager viewPager){
        SectionsPageAdapterMaster adapter = new SectionsPageAdapterMaster(getSupportFragmentManager());
        adapter.addFragment(new PlayerCharacterFragment(), "Lista");
        adapter.addFragment(new FoundsListFragment(), "Avvistamenti");
        adapter.addFragment(new PlayerMapFragment(), "Mappa");
        viewPager.setAdapter(adapter);
    }


    //Metodo che viene chiamato quando il qrcode legge qualcosa
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "you cancelled scanning", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();

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


    public void changePlayerScore(int score) {
        Model.getInstance().addScore(score);
        ServerConnections.setScore(
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, Volley.newRequestQueue(this));

    }

    public void changeCharacterPosition(int i) {

        LatLng position = getMyPosition();
        Model.getInstance().getCharacter().get(i).setLastPosition(position);
        ServerConnections.changePosition(
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, Volley.newRequestQueue(this), i);
    }

    public void addFound(String nameCharacter) {
        ServerConnections.addFound(
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, Volley.newRequestQueue(this), nameCharacter);
    }

    public LatLng getMyPosition() {

        if (userPermission && googlePlayServiceConnected) {


            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }

            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                double lat = mLastLocation.getLatitude();
                double lng = mLastLocation.getLongitude();
                return new LatLng(lat, lng);
            }

        }

        return new LatLng(0,0);
    }

    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 21);
        } else {
            userPermission = true;
        }
    }@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 21){
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                userPermission = true;
            }
            return;
        }
    }

    public void connectGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();
    }@Override
    public void onConnected(@Nullable Bundle bundle) {
        googlePlayServiceConnected = true;
    }@Override
    public void onConnectionSuspended(int i) {
        googlePlayServiceConnected = false;
    }@Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        googlePlayServiceConnected = false;
    }
}
