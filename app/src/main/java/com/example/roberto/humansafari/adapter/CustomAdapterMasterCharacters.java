package com.example.roberto.humansafari.adapter;

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

import com.example.roberto.humansafari.Character;
import com.example.roberto.humansafari.R;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by roberto on 05/10/17.
 */

public class CustomAdapterMasterCharacters extends ArrayAdapter<Character>{
    private TextView name;

    public CustomAdapterMasterCharacters(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Character> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.raw_master_character, null);


        name = convertView.findViewById(R.id.editTextNameCharacter);
        TextView points = convertView.findViewById(R.id.editTextPoint);
        ImageView imageView = convertView.findViewById(R.id.imageViewCharacter);


        final Character c = getItem(position);

        name.setText(c.getName());
        points.setText("" + c.getPoints());
        if(c.getImgSrc() != "null") {
            String url = "http://www.aclitriuggio.it/wp-pinguino/foto/" + c.getImgSrc();
            new ImageDownloaderTask(new WeakReference(imageView)).execute(url);
        }


        setBackgroundView(c.isCatchable(), position, convertView);


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

    private void setBackgroundView(Boolean isCatchable, int position, View view){
        if(isCatchable){
            //view.setAlpha(100);
            if(position%2==0){
                view.setBackgroundColor(Color.parseColor("#BBDEFB"));
            }else{
                view.setBackgroundColor(Color.parseColor("#E3F2FD"));
            }
        }else{
            //view.setAlpha(50);
            view.setBackgroundColor(Color.LTGRAY);
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


