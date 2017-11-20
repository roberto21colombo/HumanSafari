package com.example.roberto.humansafari;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by roberto on 05/10/17.
 */

public class Character {
    String name;
    int imgSrc;
    int points;
    LatLng lastPosition = null;


    public Character(String name, int imgSrc, int points){
        this.name = name;
        this.imgSrc = imgSrc;
        this.points = points;
    }
    public Character(String name, int imgSrc, int points, LatLng lastPosition){
        this.name = name;
        this.imgSrc = imgSrc;
        this.points = points;
        this.lastPosition = lastPosition;
    }


    public LatLng getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(LatLng lastPosition) {
        this.lastPosition = lastPosition;
    }



    public String getName() {
        return name;
    }

    public int getImgSrc() {
        return imgSrc;
    }

    public int getPoints() {
        return points;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgSrc(int imgSrc) {
        this.imgSrc = imgSrc;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
