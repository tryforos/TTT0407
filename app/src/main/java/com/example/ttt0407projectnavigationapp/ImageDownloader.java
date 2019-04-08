package com.example.ttt0407projectnavigationapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloader  extends AsyncTask<String, Void, Bitmap> {

    ImageView imageView = null;

    // constructor
    public ImageDownloader(ImageView imageView) {
        super();
        this.imageView = imageView;
    }
    // END constructor


    private byte[] downloadData(String urlString) {
        try {

            URL url = new URL(urlString);
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();

            InputStream is = httpCon.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead, totalBytesRead = 0;

            byte[] data = new byte[2048];

            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
                totalBytesRead += nRead;
            }

            byte[] bytes = buffer.toByteArray();

            return bytes;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    // This is a typical implementation for downloading a resource from the
    // Internet using HTTP
    private Bitmap downloadBitmap(String urlString) {

        byte[] bytes = downloadData(urlString);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }


    @Override
    protected void onPreExecute() {
        /*
         * onPreExecute(), invoked on the UI thread before the background task
         * is executed. This is on the UI thread so you can show something on
         * the UI
         */
    }


    @Override
    protected void onPostExecute(Bitmap result) {
        /*
         * onPostExecute(Result), is also invoked on the UI thread after the
         * background computation finishes. Here you get a chance to populate
         * the UI with the results of the background task
         */

        imageView.setImageBitmap(result);
    }


    @Override
    protected Bitmap doInBackground(String... param) {
        /*
         * This runs in background i.e. in parallel with the UI. Your users can
         * still work with your app while this method is executing. This method
         * cannot update the UI. For that, you should use the onPostExecute(...)
         * method
         */

        // TODO Auto-generated method stub
        return downloadBitmap(param[0]);
    }
}
