package com.example.roberto.humansafari.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roberto.humansafari.R;
import com.example.roberto.humansafari.ServerConnections;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Random;

public class CreateGameActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher{

    EditText edGameName, edCodCharacter;
    Button btnCreateGame;
    String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        edGameName = (EditText)findViewById(R.id.gameName);
        edCodCharacter = (EditText)findViewById(R.id.codCharacter);
        btnCreateGame = (Button)findViewById(R.id.btnCreateGame);

        Random r = new Random();
        code = ""+r.nextInt(10)+r.nextInt(10)+r.nextInt(10)+r.nextInt(10);
        edGameName.addTextChangedListener(this);
        btnCreateGame.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String gameName = edGameName.getText().toString();
        String codCharacter = edCodCharacter.getText().toString();
        String data = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "-" + (Calendar.getInstance().get(Calendar.MONTH)+1) + "-" + Calendar.getInstance().get(Calendar.YEAR);

        //Salvo la partita in memoria locale
        saveInternalStorage(gameName+";"+codCharacter+";"+data+"\n" ); //riga da salvare
        //Salvo la nuova partita sul DB
        ServerConnections.addGame(this, gameName, codCharacter, data);
        //Lancio l'activity precedente
        startActivity(new Intent(CreateGameActivity.this, ListGameMasterActivity.class));
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            edCodCharacter.setText(charSequence+"_"+code);
    }

    @Override
    public void afterTextChanged(Editable s) {
        String result = s.toString().replaceAll(" ", "");
        if (!s.toString().equals(result)) {
            edGameName.setText(result);
            edGameName.setSelection(result.length());
            Toast.makeText(getApplicationContext(), "spazio non consentito", Toast.LENGTH_SHORT).show();

        }
    }

    private void saveInternalStorage(String text){
        //Creo il file corrispondente
        // /data/user/0/com.example.roberto.humansafari/files/myfile
        File file = new File(this.getFilesDir(), "master_games_file");

        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(file.getName(), Context.MODE_APPEND); //in questo modo non cancella i dati vecchi
            outputStream.write(text.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
