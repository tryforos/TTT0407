package com.example.ttt0407projectnavigationapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.ViewParent;
import android.widget.ImageView;

import com.example.ttt0407projectnavigationapp.model.DaoImpl;
import com.example.ttt0407projectnavigationapp.model.entity.Company;
import com.example.ttt0407projectnavigationapp.model.entity.Product;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

    ImageView imageView = null;
    Context context = null;
    Integer objId;

    // constructor
    public ImageDownloader(ImageView imageView, Context context, Integer objId) {
        super();
        this.imageView = imageView;
        this.context = context;
        this.objId = objId;
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
        }
        catch (Exception e) {

            e.printStackTrace();

            // set an image for when error
            byte[] bitmapdata = getDefaultImageAsBytes();

            return bitmapdata;
        }
    }

    private Bitmap downloadBitmap(String urlString) {

        // set path of where file does / will exist
        String strFile;
        if (imageView.getTag().toString().equals("product")) {

            strFile = "product" + objId;
        }
        else if (imageView.getTag().toString().equals("company")) {

            strFile = "company" + objId;
        }
        else { // if (imageView.getTag().toString().equals("icon")) {
            try {

                URI uri = new URI(urlString);
                String path = uri.getPath();
                String idStr = path.substring(path.lastIndexOf('/') + 1);
                strFile = idStr;
            }
            catch (URISyntaxException e) {

                e.printStackTrace();
                strFile = "";
            }
        }
        File savePath = new File(context.getCacheDir().getPath() + "/" + strFile);

        // if exists, pull from there
        if(savePath.exists()) {

            // setting correct size
            int size = (int) savePath.length();
            byte[] bytes = new byte[size];

            BufferedInputStream buf = null;

            try {

                // attempt to read the file into BufferedInputStream
                buf = new BufferedInputStream(new FileInputStream(savePath));
                // read into bytes
                buf.read(bytes, 0, bytes.length);
                buf.close();

                // turn bytes into bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                // return
                return bitmap;
            }
            catch (Exception e) {

                e.printStackTrace();
            }
        }

        // this code runs if file DNE or if we receive error above
        // download file from internet
        byte[] bytes = downloadData(urlString);

        try {

            // save only if not default image
            if (!Arrays.equals(bytes, getDefaultImageAsBytes())) {

                // save downloaded file into savePath
                final OutputStream output = new FileOutputStream(savePath);
                output.write(bytes);
                output.flush();
                output.close();
            }
        }
        catch (Exception e) {

            e.printStackTrace();
        }

        // turn bytes into bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        // return
        return bitmap;
    }

    private byte[] getDefaultImageAsBytes(){

        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();

        return bitmapdata;
    }

    ////////
    ////////
    ////////

    @Override
    protected void onPreExecute() {
    }
    @Override
    protected Bitmap doInBackground(String... param) {

        // TODO Auto-generated method stub
        return downloadBitmap(param[0]);
    }
    @Override
    protected void onPostExecute(Bitmap result) {

        imageView.setImageBitmap(result);
    }
}
