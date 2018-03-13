package com.example.roberto.humansafari.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.roberto.humansafari.Character;
import com.example.roberto.humansafari.Model;
import com.example.roberto.humansafari.R;
import com.example.roberto.humansafari.activity.MasterMainActivity;
import com.example.roberto.humansafari.activity.PlayerMainActivity;
import com.example.roberto.humansafari.adapter.CustomAdapterPlayerCharacters;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

/**
 * Created by roberto on 16/01/18.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, GoogleMap.OnMapClickListener{
    Boolean googlePlayServiceConnected = false;
    GoogleApiClient mGoogleApiClient = null;

    ArrayList<Character> arrayListCharacter = null;

    private  View mView;
    private MapView mapView;
    private GoogleMap mGoogleMap;
    private Button btnDefineBound;

    private ArrayList<LatLng> boundPoints= null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_master_map, container, false);

        boundPoints = new ArrayList<LatLng>();

        btnDefineBound = mView.findViewById(R.id.btnSetBound);
        btnDefineBound.setOnClickListener(this);

        arrayListCharacter = Model.getInstance().getCharacters();

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getContext()).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        }
        mGoogleApiClient.connect();

        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mapView = (MapView) mView.findViewById(R.id.map);
        mapView.onCreate(null);
        mapView.onResume();
        mapView.getMapAsync(this);//when you already implement OnMapReadyCallback in your fragment
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(final GoogleMap map) {
        mGoogleMap = map;
        Log.d("MapActivity","On map ready");
        Toast toast = Toast.makeText(getActivity(), "Map Ready", Toast.LENGTH_LONG);
        toast.show();

        MapsInitializer.initialize(getContext());
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.setMyLocationEnabled(true);
        markFriends(mGoogleMap);

        LatLngBounds bounds = getLatLngBound();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.25); // offset from edges of the map 12% of screen

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));
        mGoogleMap.setOnMapClickListener(this);

        drawPolylineBound();

    }

    @Override
    public void onMapClick(LatLng latLng) {
        mGoogleMap.clear();
        mGoogleMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.cast_ic_stop_circle_filled_grey600)).anchor(0.5f, 0.5f));
        boundPoints.add(latLng);
        if(boundPoints.size()>=2){
            //disegna Polyline
            final PolylineOptions polylineOptions = new PolylineOptions();
            int i;
            for(i=0; i<boundPoints.size(); i++){
                polylineOptions.add(boundPoints.get(i)).color(Color.BLACK).width(5);
            }
            mGoogleMap.addPolyline(polylineOptions);

            //disegna ultimo polyline
            mGoogleMap.addPolyline(new PolylineOptions().add(boundPoints.get(0)).add(boundPoints.get(boundPoints.size()-1)).color(Color.GRAY));
        }
    }

    @Override
    public void onClick(View view) {
        //TODO Attivare/Disattivare pulsante definisci confini se non ci sono selezionati punti sulla mappa
        Model.getInstance().setBoundPoints(boundPoints);
        mGoogleMap.clear();
        drawPolylineBound();
        boundPoints.clear();
    }

    public void markFriends(GoogleMap map){
        Character characterSelected = Model.getInstance().getCharacterSelectedMap();
        for(Character c : arrayListCharacter){
            if(c.getLastPosition() != null) {
                if(!c.equals(characterSelected)){
                    map.addMarker(new MarkerOptions().position(c.getLastPosition())
                            .title(c.getName()));
                }else {
                    map.addMarker(new MarkerOptions().position(characterSelected.getLastPosition())
                            .title(characterSelected.getName())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                }
            }
        }
        Model.getInstance().setCharacterSelectedMap(-1);
    }

    public LatLngBounds getLatLngBound(){

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        //aggiungo la mia posizione

        if(getActivity().getLocalClassName().equals("activity.PlayerMainActivity")){
            builder.include(((PlayerMainActivity)getActivity()).getMyPosition());
        }else{
            builder.include(((MasterMainActivity)getActivity()).getMyPosition());
        }

        //aggiungo la posizione dei character trovati
        for (Character character : arrayListCharacter) {
            LatLng latLng = character.getLastPosition();
            if(latLng != null) {
                builder.include(latLng);
            }
        }
        return builder.build();
    }

    private void drawPolylineBound(){
        ArrayList<LatLng> boundPoints = Model.getInstance().getBoundPoints();
        PolygonOptions polygonOptions = new PolygonOptions();
        if(boundPoints!=null){
            for(int i=0; i<boundPoints.size(); i++){
                polygonOptions.add(boundPoints.get(i));
            }
            mGoogleMap.addPolygon(polygonOptions);
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
