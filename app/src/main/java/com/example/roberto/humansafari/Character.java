package com.example.roberto.humansafari;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.vision.barcode.Barcode;

import java.security.Timestamp;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by roberto on 05/10/17.
 */

public class Character {
    int id;
    String name;
    String img;
    int points;
    LatLng lastPosition = null;
    long time;

    boolean catchable = true;
    long deltaTime;


    public Character(int id, String name, String img, int points, long timestamp){
        this.id = id;
        this.name = name;
        this.img = img;
        this.points = points;
        this.time = timestamp;
    }
    public Character(int id, String name, String img, int points, LatLng lastPosition, long timestamp){
        this.id = id;
        this.name = name;
        this.img = img;
        this.points = points;
        this.lastPosition = lastPosition;
        this.time = timestamp;
    }


    public LatLng getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(LatLng lastPosition) {
        this.lastPosition = lastPosition;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgSrc() {
        return img;
    }

    public int getPoints() {
        return points;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgSrc(int imgSrc) {
        this.img = img;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setTime(long time){
        this.time = time;
    }

    public long getTime(){
        return time;
    }

    public boolean isCatchable(){
        if(getDeltaTime() >= 0){
            catchable = true;
        }else{
            catchable = false;
        }
        return catchable;
    }

    public long getDeltaTime(){
        return Calendar.getInstance().getTime().getTime() - time;
    }

    public void flipCatchable(){
        catchable = !catchable;
    }
}
