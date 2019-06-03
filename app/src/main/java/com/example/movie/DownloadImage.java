package com.example.movie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageView;

    @Override
    protected Bitmap doInBackground(String... urls) {
        String imageBaseUrl = "https://image.tmdb.org/t/p/original/";
        String completeUrl = imageBaseUrl + urls[0];
        Bitmap mIcon = null;
        try {
            InputStream in = new java.net.URL(completeUrl).openStream();
            mIcon = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon;
    }

    public DownloadImage(ImageView bmImage) {
        this.imageView = bmImage;
    }

    public void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}
