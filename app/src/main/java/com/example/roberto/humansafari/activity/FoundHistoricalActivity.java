package com.example.roberto.humansafari.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.roberto.humansafari.adapter.CustomAdapterHistorical;
import com.example.roberto.humansafari.Model;
import com.example.roberto.humansafari.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoundHistoricalActivity extends AppCompatActivity {

    ListView listViewHistorical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_historical);

        listViewHistorical = (ListView)findViewById(R.id.listViewHistorical);

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

            listViewHistorical.setAdapter(new CustomAdapterHistorical(this, R.layout.raw_hist, arrayList));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
