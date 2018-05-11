package com.example.roberto.humansafari.activity.character;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.roberto.humansafari.R;
import com.example.roberto.humansafari.ServerConnections;

public class CharacterLogin extends AppCompatActivity {
    EditText etCharacterCod, etEtCharacterName, etGameName;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_login);

        etGameName = (EditText)findViewById(R.id.gameName);
        etCharacterCod = (EditText)findViewById(R.id.codCharacter);
        etEtCharacterName = (EditText)findViewById(R.id.nameCharacter);
        btnLogin = (Button)findViewById(R.id.btnLoginCharacter);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gameName = etGameName.getText().toString();
                String characterCode = etCharacterCod.getText().toString();
                final String characterName = etEtCharacterName.getText().toString();
                ServerConnections.getCharacterInfo(new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("giococodice")){
                            Toast.makeText(CharacterLogin.this, "Nome gioco o Codice personaggio errati", Toast.LENGTH_LONG).show();
                        }else if(response.equals("nomepersonaggio")){
                            Toast.makeText(CharacterLogin.this, "Personaggio non trovato", Toast.LENGTH_LONG).show();
                        }else{
                            Intent intent = new Intent(CharacterLogin.this, QrcodeActivity.class);
                            intent.putExtra("name", characterName);
                            intent.putExtra("points", response);
                            //Toast.makeText(CharacterLogin.this, "Punteggio: "+response , Toast.LENGTH_LONG).show();
                            startActivity(intent);
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }, Volley.newRequestQueue(CharacterLogin.this), gameName, characterCode, characterName);
            }
        });

    }
}
