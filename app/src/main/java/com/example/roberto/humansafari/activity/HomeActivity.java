package com.example.roberto.humansafari.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.roberto.humansafari.Model;
import com.example.roberto.humansafari.R;
import com.example.roberto.humansafari.ServerConnections;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    ImageView imageButtonSafari;
    ImageView imageViewMap;
    ImageView imageViewListCharacters;
    ImageView imageViewRank;

    TextView score;
    TextView username;

    GoogleApiClient mGoogleApiClient = null;

    Boolean userPermission = false;
    Boolean googlePlayServiceConnected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.d("oooo","scrivi qualcosa");
        checkPermission();
        connectGoogleApiClient();

        score = (TextView)findViewById(R.id.score);
        username = (TextView)findViewById(R.id.username);

        imageButtonSafari = (ImageView) findViewById(R.id.imgButtonFound);
        imageButtonSafari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(HomeActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Inquadra il qrcode del personaggio");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.setOrientationLocked(false);
                integrator.initiateScan();
            }
        });
        /*
        imageButtonSafari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Model.getInstance().getDown("downChar")) {
                    startActivity(new Intent(HomeActivity.this, ListCharacterActivity.class));
                }else{
                    Toast.makeText(HomeActivity.this, "Download in corso, attendere...", Toast.LENGTH_LONG).show();
                }
            }
        });
        */

        imageViewMap = (ImageView) findViewById(R.id.imageViewMap);
        imageViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Model.getInstance().getDown("downChar")) {
                    startActivity(new Intent(HomeActivity.this, MapActivity.class));
                }else{
                    Toast.makeText(HomeActivity.this, "Download in corso, attendere...", Toast.LENGTH_LONG).show();
                }
            }
        });

        imageViewListCharacters = (ImageView) findViewById(R.id.imageViewListCharacters);
        imageViewListCharacters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Model.getInstance().getDown("downHist")) {
                    startActivity(new Intent(HomeActivity.this, ListCharacterActivity.class));
                }else{
                    Toast.makeText(HomeActivity.this, "Download in corso, attendere...", Toast.LENGTH_LONG).show();
                }
            }
        });

        imageViewRank = (ImageView) findViewById(R.id.imageViewRank);
        imageViewRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Model.getInstance().getDown("downUsr")) {
                    startActivity(new Intent(HomeActivity.this, ListRankActivity.class));
                }else{
                    Toast.makeText(HomeActivity.this, "Download in corso, attendere...", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        score.setText("Score: " + Model.getInstance().getScore());
        username.setText("" + Model.getInstance().getPlayerName());


        Model.getInstance().setDown("downChar", false);
        Model.getInstance().setDown("downUsr", false);
        Model.getInstance().setDown("downHist", false);
        //ServerConnections.downloadCharacters(HomeActivity.this, Model.getInstance().getGameName());
        //ServerConnections.getUsers(HomeActivity.this);
        ServerConnections.getHistorical(HomeActivity.this);
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
            }, Volley.newRequestQueue(HomeActivity.this));

    }

    public void changeCharacterPosition(int i) {

        LatLng position = getPosition();
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
            }, Volley.newRequestQueue(HomeActivity.this), i);
    }

    public void addFound(int i) {
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
            }, Volley.newRequestQueue(HomeActivity.this), i);
    }


    public LatLng getPosition() {

        if (userPermission && googlePlayServiceConnected) {


            if (ActivityCompat.checkSelfPermission(HomeActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HomeActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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



    //Metodo che viene chiamato quando il qrcode legge qualcosa
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(HomeActivity.this, "you cancelled scanning", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(HomeActivity.this, result.getContents(), Toast.LENGTH_LONG).show();

                String nameCharacter = result.getContents().split(";")[0];
                String pointCharacter = result.getContents().split(";")[1];

                int indexCharacterList = Model.getInstance().getCharaterPositionWithName(nameCharacter);

                //myGoogleApi = new MyGoogleApi(HomeActivity.this);
                changePlayerScore(Integer.parseInt(pointCharacter));
                changeCharacterPosition(indexCharacterList);
                addFound(indexCharacterList);

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }





    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(HomeActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
            mGoogleApiClient = new GoogleApiClient.Builder(HomeActivity.this)
                    .addConnectionCallbacks(HomeActivity.this)
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
