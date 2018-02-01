package com.example.roberto.humansafari.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roberto.humansafari.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QrcodeActivity extends AppCompatActivity {

    TextView tvInfoCharacter;
    ImageView ivQrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        tvInfoCharacter = (TextView)findViewById(R.id.textViewInfo);
        ivQrcode = (ImageView)findViewById(R.id.ivQrcode);

        String nameCharacter = getIntent().getStringExtra("name");
        String pointCharater = getIntent().getStringExtra("points");

        tvInfoCharacter.setText("Nome: " + nameCharacter + " - " + pointCharater + " punti");

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(nameCharacter + ";" + pointCharater, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ivQrcode.setImageBitmap(bitmap);
        }catch (WriterException e){
            e.printStackTrace();
        }


    }
}
