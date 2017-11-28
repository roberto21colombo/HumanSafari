package com.example.roberto.humansafari;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by roberto on 05/10/17.
 */

public class CustomAdapterRank extends ArrayAdapter<User>{

    public CustomAdapterRank(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<User> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.raw_rank, null);


        TextView name = convertView.findViewById(R.id.editTextNameUser);
        TextView score = convertView.findViewById(R.id.editTextScore);
        ImageView image = convertView.findViewById(R.id.imageView);


        User u = getItem(position);
        name.setText(u.getName());
        score.setText("" + u.getScore());

        return convertView;
    }
}
