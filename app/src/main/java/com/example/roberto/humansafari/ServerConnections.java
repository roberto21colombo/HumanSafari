package com.example.roberto.humansafari;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import static android.R.attr.required;

/**
 * Created by roberto on 21/11/17.
 */

public class ServerConnections {

    public static void downloadCharacters(Context context) {
        String url = "http://www.aclitriuggio.it/wp-pinguino/humansafari" +
                "/getcharacters.php";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("onResponse", response);
                Model.getInstance().setCharacters(response);
                Model.getInstance().setDown("downChar", true);
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

        String url = "http://www.aclitriuggio.it/wp-pinguino/humansafari" +
                "/updatelastposition.php?id=" + id + "&lat=" + lat + "&lng=" + lng;
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

    public static void getUserInfo(String userId, Response.Listener<String> responseStringLisener, Response.ErrorListener responseErrorListener, RequestQueue requestQueue){
        String url = "http://www.aclitriuggio.it/wp-pinguino/humansafari" +
                "/getuserinfo.php?userid=" + userId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseStringLisener, responseErrorListener);
        requestQueue.add(stringRequest);
    }

    public static void addUser(String name, String password, String confirmPassword, Response.Listener responseStringListener, Response.ErrorListener responseErrorListener, RequestQueue requestQueue){
        String url = "http://www.aclitriuggio.it/wp-pinguino/humansafari" +
                "/insertifnotexist.php?userid=" + name + "&pass=" + password + "&conpass=" + confirmPassword;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseStringListener, responseErrorListener);
        requestQueue.add(stringRequest);
    }

    public static void setScore(Context context){
        String url = "http://www.aclitriuggio.it/wp-pinguino/humansafari" +
                "/setscore.php?username=" + Model.getInstance().getUserName() + "&score=" + Model.getInstance().getScore();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public static void changeTime(int i, Context context){
        String url = "http://www.aclitriuggio.it/wp-pinguino/humansafari" +
                "/changetime.php?id=" + Model.getInstance().getCharacter().get(i).getId() + "&time=" + Model.getInstance().getCharacter().get(i).getTime();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public static void getUsers(Context context){
        String url = "http://www.aclitriuggio.it/wp-pinguino/humansafari" +
                "/getallusers.php";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("onResponse", response);
                Model.getInstance().setUsers(response);
                Model.getInstance().setDown("downUsr", true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public static void addFound(Context context, int indexCharacter){
        String url = "http://www.aclitriuggio.it/wp-pinguino/humansafari" +
                "/addfound.php?user=" + Model.getInstance().getUserName() + "&character=" + Model.getInstance().getCharacter().get(indexCharacter).getId();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public static void getHistorical(Context context){
        String url = "http://www.aclitriuggio.it/wp-pinguino/humansafari" +
                "/gethistorical.php";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("onResponse", response);
                Model.getInstance().setHistorical(response);
                Model.getInstance().setDown("downHist", true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    public static void addGame(Context context, String name, String codCharacter, String date){
        String url = "http://www.aclitriuggio.it/wp-pinguino/humansafari" +
                "/addgame.php?name="+name+"&codcharacter="+codCharacter+"&date="+date;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("onResponse", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

}
