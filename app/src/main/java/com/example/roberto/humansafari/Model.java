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
    private String historical = "";

    private boolean downChar, downUsr, downHist;

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

            Collections.sort(alCharacter, new CustomComparatorCharacters());
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

    //Data la Stringa Json degli Users li inserisce nell'ArrayList e li ordina per punteggio
    public void setUsers(String s){
        try {
            alUsers.clear();

            JSONArray jsonArray = new JSONArray(s);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject userObject = jsonArray.getJSONObject(i);

                String name = userObject.getString("userid");
                int score = userObject.getInt("score");

                alUsers.add(new User(name, score));
            }

            Collections.sort(alUsers, new CustomComparatorUsers());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getUsers(){
        return alUsers;
    }

    public void setHistorical(String s){
        historical = s;
    }

    public String getHistorical() {
        return historical;
    }

    public Character getCharacterWithId(int id){
        for(Character c : alCharacter){
            if(c.getId() == id){
                return c;
            }
        }
        return null;
    }

    public void setDown(String who, boolean what){
        if(who.equals("downChar")){ downChar=what; }
        if(who.equals("downUsr")){ downUsr=what; }
        if(who.equals("downHist")){ downHist=what; }
    }

    public boolean getDown(String who){
        if(who.equals("downChar")){ return downChar; }
        else if(who.equals("downUsr")){ return downUsr; }
        else{ return downHist;}
    }

    public class CustomComparatorUsers implements Comparator<User> {
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

    public class CustomComparatorCharacters implements Comparator<Character> {
        @Override
        public int compare(Character u1, Character u2) {
            if(u1.getPoints() == u2.getPoints()){
                return 0;
            }else{
                if(u1.getPoints() > u2.getPoints()){
                    return 1;
                }else{
                    return -1;
                }
            }
        }
    }
}


