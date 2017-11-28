package com.example.roberto.humansafari;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by roberto on 20/11/17.
 */

public class Model
{
    private static Model istanza;

    private ArrayList<Character> al;
    private int score = 0;
    private String userName = "";

    private Model()
    {
        al = new ArrayList<Character>();

        /*
        al.add(new Character("Paolino", R.drawable.piero_pelu,5, new LatLng(45.62724412, 9.2930603)));
        al.add(new Character("Arturo", R.drawable.piero_pelu,5, new LatLng(45.61739941, 9.28894043)));
        al.add(new Character("Francesco", R.drawable.piero_pelu,10, new LatLng(45.61259649, 9.28482056)));
        al.add(new Character("Carlitos", R.drawable.piero_pelu,10, new LatLng(45.60250902, 9.27417755)));
        al.add(new Character("Genoveffo", R.drawable.piero_pelu,10, new LatLng(45.60034718, 9.28688049)));
        al.add(new Character("Antonio", R.drawable.piero_pelu,20, new LatLng(45.60214871, 9.27400589)));
        al.add(new Character("Biennio", R.drawable.piero_pelu,20, new LatLng(45.59890591, 9.28482056)));
        al.add(new Character("Roberto", R.drawable.piero_pelu,30, new LatLng(45.59476204, 9.27666664)));
        al.add(new Character("Dave", R.drawable.piero_pelu,30));
        al.add(new Character("Simone", R.drawable.piero_pelu,30));
        al.add(new Character("Lorenza", R.drawable.piero_pelu,50));
        */
    }

    public static Model getInstance()
    {
        if (istanza == null)
        {
            istanza = new Model();
        }

        return istanza;
    }

    public void setCharacters(String s){
        try {
            al.clear();

            JSONArray jsonArray = new JSONArray(s);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject characterObject = jsonArray.getJSONObject(i);

                int id = characterObject.getInt("id");
                String name = characterObject.getString("name");
                int point = characterObject.getInt("points");
                String lat = characterObject.getString("lat");
                String lng = characterObject.getString("lng");
                long time = characterObject.getLong("time_able");


                if(lat.equals("null") || lng.equals("null")){
                    al.add(new Character(id, name, 0, point, time));
                }else{
                    LatLng lastPosition = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                    al.add(new Character(id, name, 0, point, lastPosition, time));
                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setLastPositionCharacter(String name, LatLng newPosition){
        for (Character i : al){
            if(i.getName().equals(name)){
                i.setLastPosition(newPosition);
            }
        }
    }

    public ArrayList<Character> getCharacter()
    {
        return al;
    }

    public int getScore(){
        return score;
    }
    public void setScore(int i){
        score = i;
    }

    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }


}
