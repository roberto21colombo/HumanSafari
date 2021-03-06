package com.example.roberto.humansafari;


import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
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
    private String playerName = "";
    private String historical = "";
    private String gameName = "";
    private String codCharacters = "";

    private boolean downChar, downUsr, downHist;

    private String userType;

    private Character characterSelectedMap = null;

    private ArrayList<LatLng> boundPoints;

    private boolean isIn = true;

    private Model()
    {
        alCharacter = new ArrayList<Character>();
        alUsers = new ArrayList<User>();
        boundPoints = new ArrayList<LatLng>();

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
                int fk_game = characterObject.getInt("fk_game");
                long time = characterObject.getLong("time_able");


                if(lat.equals("null") || lng.equals("null")){
                    alCharacter.add(new Character(id, name, img, point, time));
                }else{
                    LatLng lastPosition = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                    alCharacter.add(new Character(id, name, img, point, lastPosition, time));
                }


            }

            Collections.sort(alCharacter, new CustomComparatorCharacter());
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

    public ArrayList<Character> getCharacters()
    {
        return alCharacter;
    }

    public Character getCharacterWithName(String name){
        int i;
        for(i = 0; i<alCharacter.size(); i++){
            String nameNextChar = alCharacter.get(i).getName();
            if(name.equals(nameNextChar)){
                break;
            }
        }
        return alCharacter.get(i);
    }


    public int getScore(){
        return score;
    }
    public void setScore(int i){
        score = i;
    }
    public void addScore(int i){ score = score + i; }

    public String getPlayerName(){
        return playerName;
    }
    public void setPlayerName(String userName){
        this.playerName = userName;
    }

    //Data la Stringa Json degli Users li inserisce nell'ArrayList e li ordina per punteggio
    public void setUsers(String s){
        try {
            alUsers.clear();

            JSONArray jsonArray = new JSONArray(s);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject userObject = jsonArray.getJSONObject(i);

                String name = userObject.getString("playername");
                int score = userObject.getInt("points");

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

    public ArrayList<String[]> getHistoricalArray(){
        String historical = getHistorical();
        JSONArray jArray = null;
        ArrayList<String[]> arrayList = new ArrayList<String[]>();
        try {
            jArray = new JSONArray(historical);
            for(int i=0; i<jArray.length(); i++) {
                JSONObject json_obj = jArray.getJSONObject(i);
                String[] element = new String[3];
                element[0] = json_obj.getString("playername");
                element[1] = json_obj.getString("charactername");
                element[2] = json_obj.getString("date");

                arrayList.add(element);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arrayList;
    }

    //Dato l'id di un personaggio ritorna l'oggetto character
    public Character getCharacterWithId(int id){
        for(Character c : alCharacter){
            if(c.getId() == id){
                return c;
            }
        }
        return null;
    }
    //Dato il nome di un personaggio presente nella partita corrente, ritorna la sua posizione all'interno dell'arraylist
    public int getCharaterPositionWithName(String s){
        int i;
        for(i = 0; i<alCharacter.size(); i++){
            String nameNextChar = alCharacter.get(i).getName();
            if(s.equals(nameNextChar)){
                break;
            }
        }
        return i;
    }

    public String getGameName() {
        return gameName;
    }
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getCodCharacters() {
        return codCharacters;
    }

    public void setCodCharacters(String codCharacters) {
        this.codCharacters = codCharacters;
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

    public void saveInternalStorage(String dir, String fileName, String text){
        String data = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "-" + (Calendar.getInstance().get(Calendar.MONTH)+1) + "-" + Calendar.getInstance().get(Calendar.YEAR);

        //Creo il file corrispondente
        // /data/user/0/com.example.roberto.humansafari/files/myfile
        File file = new File(dir, fileName);
    }

    public void setCheckedMyFound(){
        for(String[] s: getHistoricalArray()){
            String player = s[0];
            if(player.equals(getPlayerName()))
            {
                String characterNameFounded = s[1];
                int i = getCharaterPositionWithName(characterNameFounded);
                Log.d("Personaggio", characterNameFounded);
                Log.d("PosizionePersonaggio", "+i");
                alCharacter.get(i).setFounded(true);
            }

        }
    }

    public Character getCharacterSelectedMap() {
        return characterSelectedMap;
    }

    public void setCharacterSelectedMap(int i) {
        if(i == -1){
            characterSelectedMap = null;
        }else {
            characterSelectedMap = alCharacter.get(i);
        }
    }

    public ArrayList<LatLng> getBoundPoints() {
        return boundPoints;
    }

    public String getBoundPointsSerialized(){
        String s = new SerializeObject().boundPointsToString();
        return s;
    }

    public void setBoundPoints(ArrayList<LatLng> boundPoints) {
        this.boundPoints = new ArrayList<LatLng>();
        for(int i=0; i< boundPoints.size(); i++){
            this.boundPoints.add(boundPoints.get(i));
        }
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public boolean isIn() {
        return isIn;
    }

    public void setIn(boolean in) {
        isIn = in;
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

    private class SerializeObject{

        public String boundPointsToString(){
            StringBuilder stringBuilder = new StringBuilder();
            for(int i = 0; i<boundPoints.size(); i++){
                LatLng latLng = boundPoints.get(i);
                stringBuilder.append(latLng.latitude);
                stringBuilder.append(",");
                stringBuilder.append(latLng.longitude);
                stringBuilder.append(";");
            }
            return (stringBuilder.toString());
        }
    }

    public class UserType {
        public static final String PLAYER = "player";
        public static final String MASTER = "master";
        public static final String CHARACTER = "character";
    }

}



