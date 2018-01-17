package com.example.roberto.humansafari.adapter;

import android.content.Context;
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
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by roberto on 12/01/18.
 */

public class CustomAdapterGames extends ArrayAdapter<String[]> {
    public CustomAdapterGames(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<String[]> objects) {
        super(context, resource, objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.raw_game, null);

        TextView tvGame = (TextView)convertView.findViewById(R.id.rawGameName);
        TextView tvPlayer = (TextView)convertView.findViewById(R.id.rawPlayerName);
        TextView tvDate = (TextView)convertView.findViewById(R.id.rawDate);

        String[] s = getItem(position);
        tvGame.setText(s[0]);
        tvPlayer.setText(s[1]);
        tvDate.setText(s[2]);



        return convertView;
    }
}
