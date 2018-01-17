package com.example.roberto.humansafari.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.roberto.humansafari.Character;
import com.example.roberto.humansafari.CustomComparatorCharacter;
import com.example.roberto.humansafari.Model;
import com.example.roberto.humansafari.R;
import com.example.roberto.humansafari.ServerConnections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewCharacterActivity extends AppCompatActivity {

    EditText edNameCharacter, edPoint;
    Button btnAddCharacter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_character);

        edNameCharacter = (EditText)findViewById(R.id.etNameCharacter);
        edPoint = (EditText)findViewById(R.id.etPointCharacter);
        btnAddCharacter = (Button)findViewById(R.id.btnAddCharacter);

        final int charater = getIntent().getIntExtra("character", -1);

        if( charater == -1){
            btnAddCharacter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String name = edNameCharacter.getText().toString();
                    final String point = edPoint.getText().toString();

                    ServerConnections.addCharacter(name, point, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("onResponse", response);
                            //Salvo le informazioni nel model
                            Model.getInstance().getCharacter().add(new Character(name, Integer.parseInt(point)));
                            Collections.sort(Model.getInstance().getCharacter(), new CustomComparatorCharacter());
                            //Una volta salvati i dati torno all'activity precedente
                            startActivity(new Intent(NewCharacterActivity.this, MasterMainActivity.class));
                        }
                    }, null, Volley.newRequestQueue(NewCharacterActivity.this));
                }
            });
        }else{
            Character c = Model.getInstance().getCharacter().get(charater);
            final String oldName = c.getName();

            ((TextView)findViewById(R.id.tvTitle)).setText("Modifica Personaggio");
            edNameCharacter.setText(c.getName());
            edPoint.setText(""+c.getPoints());
            btnAddCharacter.setText("Modifica");

            btnAddCharacter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String name = edNameCharacter.getText().toString();
                    final String point = edPoint.getText().toString();

                    ServerConnections.modCharacter(oldName, name, point, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("onResponse", response);
                            //Salvo le informazioni nel model
                            Model.getInstance().getCharacter().remove(charater);
                            Model.getInstance().getCharacter().add(new Character(name, Integer.parseInt(point)));
                            Collections.sort(Model.getInstance().getCharacter(), new CustomComparatorCharacter());
                            //Una volta salvati i dati torno all'activity precedente
                            startActivity(new Intent(NewCharacterActivity.this, MasterMainActivity.class));
                        }
                    }, null, Volley.newRequestQueue(NewCharacterActivity.this));
                }
            });
        }


    }
}
