package com.example.roberto.humansafari.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.roberto.humansafari.Model;
import com.example.roberto.humansafari.R;
import com.example.roberto.humansafari.adapter.CustomAdapterHistorical;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoundsListFragment extends Fragment {

    ListView listViewHistorical;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_founds_list, container, false);


        listViewHistorical = view.findViewById(R.id.listViewHistorical);

        try {
            String historical = Model.getInstance().getHistorical();
            JSONArray jArray = new JSONArray(historical);
            final ArrayList<String[]> arrayList = new ArrayList<String[]>();

            for(int i=0; i<jArray.length(); i++){
                JSONObject json_obj = jArray.getJSONObject(i);
                String[] element = new String[3];
                element[0] = json_obj.getString("fk_character");
                element[1] = json_obj.getString("fk_user");
                element[2] = json_obj.getString("date");

                arrayList.add(element);
            }

            listViewHistorical.setAdapter(new CustomAdapterHistorical(getContext(), R.layout.raw_hist, arrayList));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

}
