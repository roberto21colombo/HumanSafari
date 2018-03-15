package com.example.roberto.humansafari.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.roberto.humansafari.Character;
import com.example.roberto.humansafari.Model;
import com.example.roberto.humansafari.R;
import com.example.roberto.humansafari.ServerConnections;
import com.example.roberto.humansafari.activity.MasterMainActivity;
import com.example.roberto.humansafari.activity.PlayerMainActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by roberto on 16/01/18.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, GoogleMap.OnMapClickListener{
    Boolean googlePlayServiceConnected = false;
    GoogleApiClient mGoogleApiClient = null;

    LocationManager locationManager;

    ArrayList<Character> arrayListCharacter = null;

    private  View mView;
    private MapView mapView;
    private GoogleMap mGoogleMap;
    private Button btnDefineBound;

    private ArrayList<LatLng> boundPoints= null;

    private boolean isPlayerActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_master_map, container, false);

        isPlayerActivity = getActivity().getLocalClassName().equals("activity.PlayerMainActivity");

        boundPoints = new ArrayList<LatLng>();
        downloadMapBound();

        btnDefineBound = mView.findViewById(R.id.btnSetBound);
        if(isPlayerActivity) {
            btnDefineBound.setVisibility(View.INVISIBLE);
        }else {
            btnDefineBound.setVisibility(View.VISIBLE);
            btnDefineBound.setEnabled(false);
            btnDefineBound.setOnClickListener(this);
        }


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

        LatLngBounds bounds = getLatLngBoundLastFound();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.25); // offset from edges of the map 12% of screen

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding));

        if(!isPlayerActivity) {
            mGoogleMap.setOnMapClickListener(this);
        }


        drawPolylineBound();
        locationManager = (LocationManager) getContext().getSystemService(getContext().LOCATION_SERVICE);
        locationManager.requestLocationUpdates("gps", 1000, 1, new android.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LatLng mLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                boolean isContained = contains(mLatLng);
                //TODO Mandare notifica
                if(isContained){
                    Log.d("Fencing", "Dentro");
                    Toast.makeText(getContext(), "Sei Dentro", Toast.LENGTH_SHORT).show();
                }else {
                    Log.d("Fencing", "Fuori");
                    Toast.makeText(getContext(), "Sei Fuori", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        });
    }

    @Override
    public void onMapClick(LatLng latLng) {
        btnDefineBound.setEnabled(true);
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
        btnDefineBound.setEnabled(false);
        Model.getInstance().setBoundPoints(boundPoints);
        boundPoints.clear();

        mGoogleMap.clear();
        drawPolylineBound();

        //UploadOnServerMapBound
        ServerConnections.setMapBound(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, Volley.newRequestQueue(getContext()));
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

    public LatLngBounds getLatLngBoundLastFound(){

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        //aggiungo la mia posizione

        if(isPlayerActivity){
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
        if(boundPoints.size()>0){
            for(int i=0; i<boundPoints.size(); i++){
                polygonOptions.add(boundPoints.get(i));
            }
            mGoogleMap.addPolygon(polygonOptions);
        }
    }

    public void downloadMapBound(){
        ServerConnections.getMapBound(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<LatLng> boundPoints = new ArrayList<LatLng>();

                StringTokenizer stringTokenizer = new StringTokenizer(response, ";");
                while (stringTokenizer.hasMoreElements()){
                    String[] stringLatLng =stringTokenizer.nextElement().toString().split(",");
                    LatLng latLng = new LatLng(Double.parseDouble(stringLatLng[0]), Double.parseDouble(stringLatLng[1]));
                    boundPoints.add(latLng);
                }

                Model.getInstance().setBoundPoints(boundPoints);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, Volley.newRequestQueue(getContext()));
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


    public boolean contains(LatLng myLatLng) {
        int i;
        int j;
        ArrayList<LatLng> boundPoints = Model.getInstance().getBoundPoints();
        boolean result = false;
        for (i = 0, j = boundPoints.size() - 1; i < boundPoints.size(); j = i++) {
            if ((boundPoints.get(i).latitude > myLatLng.latitude) != (boundPoints.get(j).latitude > myLatLng.latitude) &&
                    (myLatLng.longitude < (boundPoints.get(j).longitude - boundPoints.get(i).longitude) * (myLatLng.latitude - boundPoints.get(i).latitude) / (boundPoints.get(j).latitude-boundPoints.get(i).latitude) + boundPoints.get(i).longitude)) {
                result = !result;
            }
        }
        return result;
    }
}
