package com.example.roberto.humansafari;

import android.content.Context;
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

public class CustomAdapterCharacters extends ArrayAdapter<Character>{

    public CustomAdapterCharacters(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Character> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.raw_character, null);

        TextView name = convertView.findViewById(R.id.editTextNameCharacter);
        TextView points = convertView.findViewById(R.id.editTextPoint);
        ImageView image = convertView.findViewById(R.id.imageView);

        Character c = getItem(position);
        name.setText(c.getName());
        points.setText("" + c.getPoints());


        return convertView;
    }
}
