package com.example.roberto.humansafari;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBnLogin();
            }
        });
        findViewById(R.id.title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            }
        });
    }

    private void onClickBnLogin(){
        //Nome Utente inserito nell'editText
        String username = ((EditText)findViewById(R.id.edUserName)).getText().toString();

        //Creo una coda di richiesta Volley
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        //Chiamo la mia classe ServerConnections passandogli lo userName, definendo l'onResponse, e la requesQueue
        ServerConnections.getUserPassword(username, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Prendo la psw inserita nell'editText e la casto a String.
                // Accedo all'EditText passando dalla classe e non direttamente da this.
                String editTextPsw = ((EditText) findViewById(R.id.edPassword)).getText().toString();
                //Controllo che sia stata trovata una psw associata a quel nome utente
                //Confronto che response (password associata a nomeUtente) corrisponda con quella inserita nell'editText
                if (!response.equals("") && editTextPsw.equals(response)) {
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Nome Utente o Password errati", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Errore di connessione al server", Toast.LENGTH_LONG).show();
            }
        }, requestQueue);
    }
}
