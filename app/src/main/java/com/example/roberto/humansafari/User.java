package com.example.roberto.humansafari;

/**
 * Created by roberto on 28/11/17.
 */

public class User {
    String name;
    int score;

    public User(String name, int score){
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
