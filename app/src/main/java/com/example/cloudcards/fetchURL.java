package com.example.cloudcards;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.net.Uri;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

class fetchImage extends Thread {

    String URL;
    Bitmap bitmap;
    Context context;
    ProgressDialog progressDialog;
    Handler mainHandler;
    ImageView view;

    fetchImage(String URL, Context context, ImageView a) {
        this.context = context;
        this.URL = URL;
        this.view = a;
        this.mainHandler = new Handler();

    }

    public void test(String a) throws IOException {
        URL url = new URL(URL);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        InputStream is = url.openStream();

        OutputStream os = new FileOutputStream(a);
        byte[] b = new byte[2048];
        int length;
        while ((length = is.read()) != -1) {
            os.write(b, 0, length);
        }
        is.close();
        os.close();

    }

    @Override
    public void run() {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        mainHandler.post(new Runnable() {
            @Override
            public void run() {

                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Getting your pic....");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        });

        InputStream inputStream = null;
        try {
            inputStream = new URL(URL).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainHandler.post(new Runnable() {
            @Override
            public void run() {

                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                view.setImageBitmap(bitmap);

            }
        });


    }
}