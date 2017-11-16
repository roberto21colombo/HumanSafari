package com.example.roberto.humansafari;

/**
 * Created by roberto on 05/10/17.
 */

public class Character {
    String name;
    int imgSrc;
    int points;

    public Character(String name, int imgSrc, int points){
        this.name = name;
        this.imgSrc = imgSrc;
        this.points = points;
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
