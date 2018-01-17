package com.example.roberto.humansafari.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.roberto.humansafari.Character;
import com.example.roberto.humansafari.Model;
import com.example.roberto.humansafari.R;
import com.example.roberto.humansafari.ServerConnections;

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
                        //Una volta salvati i dati torno all'activity precedente
                        startActivity(new Intent(NewCharacterActivity.this, MasterMainActivity.class));
                    }
                }, null, Volley.newRequestQueue(NewCharacterActivity.this));
            }
        });
    }
}
