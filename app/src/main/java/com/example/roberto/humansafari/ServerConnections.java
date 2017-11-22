package com.example.roberto.humansafari;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.R.attr.name;

/**
 * Created by roberto on 21/11/17.
 */

public class ServerConnections {

    public static void downloadCharacters(Context context) {
        String url = "http://www.aclitriuggio.it/wp-pinguino/getcharacters.php";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("onResponse", response);
                Model.getInstance().setCharacters(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
            requestQueue.add(stringRequest);
    }

    public static void changePosition(int i, final Context context){
        Character c = Model.getInstance().getCharacter().get(i);
        int id = c.getId();
        final String name = c.getName();
        double lat = c.getLastPosition().latitude;
        double lng = c.getLastPosition().longitude;

        String url = "http://www.aclitriuggio.it/wp-pinguino/updatelastposition.php?id=" + id + "&lat=" + lat + "&lng=" + lng;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("onResponse", response);
                Toast.makeText(context, "Aggiornata Posizione di " + name, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public static void getUserPassword(String userId, Response.Listener<String> responseStringLisener, Response.ErrorListener responseErrorListener, RequestQueue requestQueue){
        String url = "http://www.aclitriuggio.it/wp-pinguino/getuserpassword.php?userid=" + userId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseStringLisener, responseErrorListener);
        requestQueue.add(stringRequest);
    }
}
