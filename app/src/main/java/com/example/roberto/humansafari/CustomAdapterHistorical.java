package com.example.roberto.humansafari;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by roberto on 05/10/17.
 */

public class CustomAdapterHistorical extends ArrayAdapter<String[]>{
    TextView textViewUserName;

    public CustomAdapterHistorical(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<String[]> objects) {
        super(context, resource, objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.raw_hist, null);


        textViewUserName = convertView.findViewById(R.id.editTextNameUser);
        TextView textViewDate = convertView.findViewById(R.id.editTextDate);
        ImageView imageView = convertView.findViewById(R.id.imageViewCharacter);



        int idCharacter = Integer.parseInt(getItem(position)[0]);
        int idUser = Integer.parseInt(getItem(position)[1]);
        String date = getItem(position)[2];

        String nameUser = Model.getInstance().getUsers().get(idUser).getName();


        Character character = Model.getInstance().getCharacterWithId(idCharacter);
        String imgCharacter = character.getImgSrc();

        textViewUserName.setText(nameUser);
        textViewDate.setText(date);
        if(imgCharacter != "null") {
            String url = "http://www.aclitriuggio.it/wp-pinguino/foto/" + imgCharacter;
            new CustomAdapterHistorical.ImageDownloaderTask(new WeakReference(imageView)).execute(url);
        }




        setBackgroundView(position, convertView);


        return convertView;
    }





    private class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap>{
        private final WeakReference<ImageView> imageViewReference;

        public ImageDownloaderTask(WeakReference<ImageView> imageViewReference) {
            this.imageViewReference = imageViewReference;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    if (bitmap != null) {
                        imageView.setImageBitmap(getCroppedBitmap(bitmap));
                    } else {
                        Drawable placeholder = imageView.getContext().getResources().getDrawable(R.drawable.binocularflat);
                        imageView.setImageDrawable(placeholder);
                    }
                }
            }
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            Bitmap bmp = null;
            try {
                InputStream in = new URL(url[0]).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                // log error
            }
            return bmp;
        }
    }

    private void setBackgroundView(int position, View view){
        if(position%2==0){
            view.setBackgroundColor(Color.parseColor("#616161"));
        }else{
            view.setBackgroundColor(Color.parseColor("#9E9E9E"));
        }

    }

    private Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }
}
