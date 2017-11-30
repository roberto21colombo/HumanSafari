package com.example.roberto.humansafari;


import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by roberto on 20/11/17.
 */

public class Model
{
    private static Model istanza;

    private ArrayList<Character> alCharacter;
    private ArrayList<User> alUsers;
    private int score = 0;
    private String userName = "";

    private Model()
    {
        alCharacter = new ArrayList<Character>();
        alUsers = new ArrayList<User>();

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
            alCharacter.clear();

            JSONArray jsonArray = new JSONArray(s);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject characterObject = jsonArray.getJSONObject(i);

                int id = characterObject.getInt("id");
                String name = characterObject.getString("name");
                int point = characterObject.getInt("points");
                String img = characterObject.getString("img");
                String lat = characterObject.getString("lat");
                String lng = characterObject.getString("lng");
                long time = characterObject.getLong("time_able");


                if(lat.equals("null") || lng.equals("null")){
                    alCharacter.add(new Character(id, name, img, point, time));
                }else{
                    LatLng lastPosition = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                    alCharacter.add(new Character(id, name, img, point, lastPosition, time));
                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setLastPositionCharacter(String name, LatLng newPosition){
        for (Character i : alCharacter){
            if(i.getName().equals(name)){
                i.setLastPosition(newPosition);
            }
        }
    }

    public ArrayList<Character> getCharacter()
    {
        return alCharacter;
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

    public void setUsers(String s){
        try {
            alUsers.clear();

            JSONArray jsonArray = new JSONArray(s);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject userObject = jsonArray.getJSONObject(i);

                int id = userObject.getInt("id");
                String name = userObject.getString("userid");
                int score = userObject.getInt("score");

                alUsers.add(new User(name, score));
            }

            Collections.sort(alUsers, new CustomComparator());

            int i = 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<User> getUsers(){
        return alUsers;
    }


    public class CustomComparator implements Comparator<User> {
        @Override
        public int compare(User u1, User u2) {
            if(u1.getScore() == u2.getScore()){
                return 0;
            }else{
                if(u1.getScore() > u2.getScore()){
                    return -1;
                }else{
                    return 1;
                }
            }
        }
    }
}


