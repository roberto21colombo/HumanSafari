package com.example.roberto.humansafari.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.roberto.humansafari.R;

import java.util.ArrayList;

/**
 * Created by roberto on 12/01/18.
 */

public class CustomAdapterGames extends ArrayAdapter<String[]> {
    int resource;
    public CustomAdapterGames(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<String[]> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Capisco se è la listView delle partite dell'organizzatore o del giocatore
        if(resource == R.layout.raw_game_player){
            convertView = inflater.inflate(R.layout.raw_game_player, null);
            TextView tvGame = convertView.findViewById(R.id.rawGameName);
            TextView tvPlayer = convertView.findViewById(R.id.rawPlayer);
            TextView tvDate = convertView.findViewById(R.id.rawDate);

            String[] s = getItem(position);
            tvGame.setText(s[0]);
            tvPlayer.setText(s[1]);
            tvDate.setText(s[2]);
        }else{
            convertView = inflater.inflate(R.layout.raw_game_master, null);
            TextView tvGame = convertView.findViewById(R.id.rawGameName);
            TextView tvDate = convertView.findViewById(R.id.rawDate);

            String[] s = getItem(position);
            tvGame.setText(s[0]);
            tvDate.setText(s[2]);
        }

        if(position%2==0){
            convertView.setBackgroundColor(Color.parseColor("#BBDEFB"));
        }else{
            convertView.setBackgroundColor(Color.parseColor("#E3F2FD"));
        }


        return convertView;
    }
}
