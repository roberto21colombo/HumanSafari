package com.example.roberto.humansafari.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import com.example.roberto.humansafari.adapter.CustomAdapterHistorical;

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


        ArrayList<String[]> arrayList = Model.getInstance().getHistoricalArray();

        if(arrayList.size()>0){
            mCustomAdapterHistorical = new CustomAdapterHistorical(getContext(), R.layout.raw_hist, arrayList);
            listViewHistorical.setAdapter(mCustomAdapterHistorical);
        }


        btnRefreshFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServerConnections.getHistorical(
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            ArrayList<String[]> arrayList = Model.getInstance().getHistoricalArray();
                            if(arrayList.size() > 0){
                                mCustomAdapterHistorical.updateObject(arrayList);
                                mCustomAdapterHistorical.notifyDataSetChanged();
                                Toast.makeText(getContext(), "Avvistamenti Aggiornati", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getContext(), "Ancora nessun Avvistamento", Toast.LENGTH_LONG).show();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }, Volley.newRequestQueue(getContext()));
            }
        });





        return view;
    }



}
