package com.example.roberto.humansafari;

import java.util.Comparator;

/**
 * Created by roberto on 17/01/18.
 */
public class CustomComparatorCharacter implements Comparator<Character> {
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