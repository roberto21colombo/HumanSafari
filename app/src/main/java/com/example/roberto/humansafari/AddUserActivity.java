package com.example.roberto.humansafari;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

public class AddUserActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edUserName = null, edPassword = null, edConfirmPassword = null;
    Button btnConferma = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        edUserName = (EditText)findViewById(R.id.newUserName);
        edPassword = (EditText)findViewById(R.id.newPassword);
        edConfirmPassword = (EditText)findViewById(R.id.confirmNewPassword);
        btnConferma = (Button)findViewById(R.id.btnAddUser);
        btnConferma.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        final String userName = edUserName.getText().toString();
        final String password = edPassword.getText().toString();
        final String confirmPassword = edConfirmPassword.getText().toString();

        ServerConnections.addUser(userName, password, confirmPassword, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("errorpass")){
                    Toast.makeText(AddUserActivity.this, "Le password non corrispondono", Toast.LENGTH_LONG).show();
                }if(response.equals("exists")){
                    Toast.makeText(AddUserActivity.this, "Nome utente già esistente...", Toast.LENGTH_LONG).show();
                }if(response.equals("success")){
                    Toast.makeText(AddUserActivity.this, "Utente aggiunto!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddUserActivity.this, LoginActivity.class));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddUserActivity.this, "Errore di connessione al server", Toast.LENGTH_LONG).show();
            }
        }, Volley.newRequestQueue(AddUserActivity.this));
    }

}
