package com.example.roberto.humansafari.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.roberto.humansafari.Model;
import com.example.roberto.humansafari.R;
import com.example.roberto.humansafari.ServerConnections;
import com.example.roberto.humansafari.activity.HomeActivity;
import com.example.roberto.humansafari.activity.ListGamePlayerActivity;
import com.example.roberto.humansafari.activity.PlayerMainActivity;
import com.example.roberto.humansafari.adapter.CustomAdapterHistorical;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoundsListFragment extends Fragment {

    ListView listViewHistorical;
    FloatingActionButton btnRefreshFound;

    CustomAdapterHistorical mCustomAdapterHistorical = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_founds_list, container, false);


        listViewHistorical = view.findViewById(R.id.listViewHistorical);
        btnRefreshFound = view.findViewById(R.id.btnRefreshFound);
        btnRefreshFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServerConnections.getHistorical(
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            mCustomAdapterHistorical.updateObject(Model.getInstance().getHistoricalArray());
                            mCustomAdapterHistorical.notifyDataSetChanged();
                            Toast.makeText(getContext(), "Avvistamenti Aggiornati", Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }, Volley.newRequestQueue(getContext()));
            }
        });

        mCustomAdapterHistorical = new CustomAdapterHistorical(getContext(), R.layout.raw_hist, Model.getInstance().getHistoricalArray());
        listViewHistorical.setAdapter(mCustomAdapterHistorical);



        return view;
    }



}
