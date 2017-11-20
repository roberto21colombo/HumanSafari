package com.example.roberto.humansafari;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        String username = "", password = "";
        username = ((EditText)findViewById(R.id.edUserName)).getText().toString();
        password = ((EditText)findViewById(R.id.edPassword)).getText().toString();

        if(username.equals("root") && password.equals("root")){
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        }else{
            Toast toast = Toast.makeText(this, "Nome Utente o Password Errati...", Toast.LENGTH_LONG);
            toast.show();
        }

    }
}
