package com.example.roberto.humansafari.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.roberto.humansafari.Character;
import com.example.roberto.humansafari.CustomComparatorCharacter;
import com.example.roberto.humansafari.Model;
import com.example.roberto.humansafari.R;
import com.example.roberto.humansafari.ServerConnections;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;

public class NewCharacterActivity extends AppCompatActivity {

    EditText edNameCharacter, edPoint;
    Button btnAddCharacter;
    ImageView ivPictures;

    Bitmap fixBitmap = null;
    ByteArrayOutputStream byteArrayOutputStream ;
    byte[] byteArray ;
    String convertImage = "";

    final int IMG_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_character);

        ivPictures = (ImageView)findViewById(R.id.ivProfile);
        edNameCharacter = (EditText)findViewById(R.id.etNameCharacter);
        edPoint = (EditText)findViewById(R.id.etPointCharacter);
        btnAddCharacter = (Button)findViewById(R.id.btnAddCharacter);

        byteArrayOutputStream = new ByteArrayOutputStream();
        ivPictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMG_REQUEST);
            }
        });
        //controllo se sto modificando o creando da zero
        final int charater = getIntent().getIntExtra("character", -1);
        //nuovo
        if( charater == -1){
            btnAddCharacter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String name = edNameCharacter.getText().toString();
                    final String point = edPoint.getText().toString();
                    //TODO controllare se una foto è stata selezionata o meno, altrimenti crasha
                    if (fixBitmap != null){
                        fixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        byteArray = byteArrayOutputStream.toByteArray();
                        convertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    }


                    ServerConnections.addCharacter(name, point, convertImage, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("onResponse", response);
                            //Salvo le informazioni nel model
                            Model.getInstance().getCharacters().add(new Character(name, Integer.parseInt(point)));
                            Collections.sort(Model.getInstance().getCharacters(), new CustomComparatorCharacter());

                            //Una volta salvati i dati torno all'activity precedente
                            startActivity(new Intent(NewCharacterActivity.this, MasterMainActivity.class));
                        }
                    }, null, Volley.newRequestQueue(NewCharacterActivity.this));
                }
            });
        }else{
            Character c = Model.getInstance().getCharacters().get(charater);
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

                    fixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    convertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    ServerConnections.modCharacter(oldName, name, point, convertImage, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("onResponse", response);
                            //Salvo le informazioni nel model
                            Model.getInstance().getCharacterWithName(oldName).setName(name);
                            Model.getInstance().getCharacterWithName(oldName).setPoints(Integer.parseInt(point));
                            if(convertImage != null) {
                                Model.getInstance().getCharacterWithName(oldName).updateImgSrc();
                            }

                            //Model.getInstance().getCharacters().remove(charater);
                            //Model.getInstance().getCharacters().add(new Character(name, Integer.parseInt(point), ));
                            Collections.sort(Model.getInstance().getCharacters(), new CustomComparatorCharacter());
                            //Una volta salvati i dati torno all'activity precedente
                            startActivity(new Intent(NewCharacterActivity.this, MasterMainActivity.class));
                        }
                    }, null, Volley.newRequestQueue(NewCharacterActivity.this));
                }
            });
        }


    }

    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == IMG_REQUEST && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {

                fixBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                ivPictures.setImageBitmap(fixBitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
}
