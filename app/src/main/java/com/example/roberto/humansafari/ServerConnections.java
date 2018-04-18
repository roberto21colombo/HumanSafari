package com.example.roberto.humansafari;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.nearby.connection.Payload;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by roberto on 21/11/17.
 */

public class ServerConnections {

    public static void downloadCharacters(Response.Listener<String> responsStringListener, Response.ErrorListener errorListener, RequestQueue requestQueue) {
        String url = "http://www.aclitriuggio.it/wp-pinguino/humansafari" +
                "/getcharacters.php?gamename="+ Model.getInstance().getGameName();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responsStringListener, errorListener);

        requestQueue.add(stringRequest);
    }



    public static void changePosition(Response.Listener<String> responsStringListener, Response.ErrorListener errorListener, RequestQueue requestQueue, int i){
        Character c = Model.getInstance().getCharacters().get(i);
        String name = c.getName();
        //final String name = c.getName();
        double lat = c.getLastPosition().latitude;
        double lng = c.getLastPosition().longitude;

        String url = "http://www.aclitriuggio.it/wp-pinguino/humansafari" +
                "/updatelastposition.php?name=" + name + "&game=" + Model.getInstance().getGameName() + "&lat=" + lat + "&lng=" + lng;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responsStringListener, errorListener);

        requestQueue.add(stringRequest);
    }

    public static void getUserInfo(Response.Listener<String> responseStringLisener, Response.ErrorListener responseErrorListener, RequestQueue requestQueue){
        String url = "http://www.aclitriuggio.it/wp-pinguino/humansafari" +
                "/getuserinfo.php?playername=" + Model.getInstance().getPlayerName() + "&gamename=" + Model.getInstance().getGameName();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseStringLisener, responseErrorListener);
        requestQueue.add(stringRequest);
    }

    public static void addPlayer(String name, String game, Response.Listener responseStringListener, Response.ErrorListener responseErrorListener, RequestQueue requestQueue){
        String url = "http://www.aclitriuggio.it/wp-pinguino/humansafari" +
                "/addplayer.php?name=" + name + "&game=" + game;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseStringListener, responseErrorListener);
        requestQueue.add(stringRequest);
    }

    public static void addCharacter(final String name, final String point, final String image, Response.Listener responseStringListener, Response.ErrorListener responseErrorListener, RequestQueue requestQueue){
        String url = "http://www.aclitriuggio.it/wp-pinguino/humansafari/addcharacter.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, responseStringListener, responseErrorListener)

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("point", point);
                params.put("game", Model.getInstance().getGameName());
                params.put("image", image);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public static void modCharacter(final String oldName, final String name, final String point, final String image, Response.Listener responseStringListener, Response.ErrorListener responseErrorListener, RequestQueue requestQueue){
        String url = "http://www.aclitriuggio.it/wp-pinguino/humansafari/modcharacter.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, responseStringListener, responseErrorListener)
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("oldname", oldName);
                params.put("name", name);
                params.put("point", point);
                params.put("image", image);
                params.put("game", Model.getInstance().getGameName());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public static void setScore(Response.Listener<String> responsStringListener, Response.ErrorListener errorListener, RequestQueue requestQueue){
        String url = "http://www.aclitriuggio.it/wp-pinguino/humansafari" +
                "/setscore.php?username=" + Model.getInstance().getPlayerName() + "&score=" + Model.getInstance().getScore() + "&game=" + Model.getInstance().getGameName();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responsStringListener, errorListener);
        requestQueue.add(stringRequest);
    }

    public static void changeTime(int i, Context context){
        String url = "http://www.aclitriuggio.it/wp-pinguino/humansafari" +
                "/changetime.php?id=" + Model.getInstance().getCharacters().get(i).getId() + "&time=" + Model.getInstance().getCharacters().get(i).getTime();
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

    public static void getUsers(Response.Listener<String> responseStringListener, Response.ErrorListener responseErrorListener, RequestQueue requestQueue){
        String url = "http://www.aclitriuggio.it/wp-pinguino/humansafari" +
                "/getallusers.php?game="+Model.getInstance().getGameName();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseStringListener, responseErrorListener);
        requestQueue.add(stringRequest);
    }

    public static void addFound(Response.Listener<String> responseStringListener, Response.ErrorListener responseErrorListener, RequestQueue requestQueue, String nameCharacter){
        String url = "http://www.aclitriuggio.it/wp-pinguino/humansafari" +
                "/addfound.php?player=" + Model.getInstance().getPlayerName() + "&character=" + nameCharacter + "&game=" + Model.getInstance().getGameName() ;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseStringListener, responseErrorListener);
        requestQueue.add(stringRequest);
    }

    public static void getHistorical(Response.Listener<String> responseStringListener, Response.ErrorListener responseErrorListener, RequestQueue requestQueue ){
        String url = "http://www.aclitriuggio.it/wp-pinguino/humansafari" +
                "/gethistorical.php?game=" + Model.getInstance().getGameName();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseStringListener, responseErrorListener);
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

    public static void getCharacterInfo(Response.Listener<String> responseStringListener, Response.ErrorListener responseErrorListener, RequestQueue requestQueue, String gameName, String codCharacter, String nameCharacter){
        String url = "http://www.aclitriuggio.it/wp-pinguino/humansafari" +
                "/getcharacterinfo.php?game="+gameName+"&codcharacter="+codCharacter+"&namecharacter="+nameCharacter;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseStringListener, responseErrorListener);
        requestQueue.add(stringRequest);
    }

    public static void isGamePlayerExist(String playerName, String game, Response.Listener<String> responseStringListener, RequestQueue requestQueue){
        String url = "http://www.aclitriuggio.it/wp-pinguino/humansafari" +
                "/isgameplayerexist.php?playername="+playerName+ "&game="+game;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseStringListener, null);
        requestQueue.add(stringRequest);
    }

    public static void setMapBound(Response.Listener<String> responseStringListener, Response.ErrorListener responseErrorListener, RequestQueue requestQueue){
        String url = "http://www.aclitriuggio.it/wp-pinguino/humansafari" +
                "/setmapbound.php?game=" + Model.getInstance().getGameName() + "&bound=" +Model.getInstance().getBoundPointsSerialized();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseStringListener, responseErrorListener);
        requestQueue.add(stringRequest);
    }

    public static void getMapBound(Response.Listener<String> responseStringListener, Response.ErrorListener responseErrorListener, RequestQueue requestQueue){
        String url = "http://www.aclitriuggio.it/wp-pinguino/humansafari" +
                "/getmapbound.php?game=" + Model.getInstance().getGameName();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseStringListener, responseErrorListener);
        requestQueue.add(stringRequest);
    }

    public static void sendNotification(Response.Listener<String> responseStringListener, Response.ErrorListener responseErrorListener, RequestQueue requestQueue){
        String url = "http://www.aclitriuggio.it/wp-pinguino/humansafari/sendnotification.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseStringListener, responseErrorListener);
        requestQueue.add(stringRequest);
    }

}