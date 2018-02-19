package com.example.roberto.humansafari.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.roberto.humansafari.Model;
import com.example.roberto.humansafari.fragment.MapFragment;
import com.example.roberto.humansafari.fragment.MasterCharacterFragment;
import com.example.roberto.humansafari.fragment.MasterMapsFragment;
import com.example.roberto.humansafari.fragment.MasterPlayersFragment;
import com.example.roberto.humansafari.R;
import com.example.roberto.humansafari.adapter.SectionsPageAdapterMaster;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

public class MasterMainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = "MasterMainActivity";

    public SectionsPageAdapterMaster mSectionsPageAdapterMaster;

    private ViewPager mViewPager;

    Boolean userPermission = false;
    Boolean googlePlayServiceConnected = false;
    GoogleApiClient mGoogleApiClient = null;

    TextView tvGameName, tvCodCharacter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_main);

        checkPermission();
        connectGoogleApiClient();

        mSectionsPageAdapterMaster = new SectionsPageAdapterMaster(getSupportFragmentManager());
        mViewPager = (ViewPager)findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tvGameName = (TextView)findViewById(R.id.tvGameName);
        tvCodCharacter = (TextView)findViewById(R.id.tvCodCharacter);
        tvGameName.setText(Model.getInstance().getGameName());
        tvCodCharacter.setText(Model.getInstance().getCodCharacters());
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MasterMainActivity.this, ListGameMasterActivity.class));
    }
    public void setupViewPager(ViewPager viewPager){
        SectionsPageAdapterMaster adapter = new SectionsPageAdapterMaster(getSupportFragmentManager());
        adapter.addFragment(new MasterCharacterFragment(), "Personaggi");
        adapter.addFragment(new MasterPlayersFragment(), "Giocatori");
        adapter.addFragment(new MapFragment(), "Mappa");
        viewPager.setAdapter(adapter);
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
    }

    @Override
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
