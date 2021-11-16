package com.example.cloudcards;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import android.os.Handler;
import android.widget.ImageView;

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

    }

    @Override
    public void run() {

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