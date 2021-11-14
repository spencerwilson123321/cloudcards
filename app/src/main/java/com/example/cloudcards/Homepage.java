package com.example.cloudcards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Homepage extends AppCompatActivity  implements MenuItem.OnMenuItemClickListener {

    Button showMenu;
    private String mCurrentPhotoPath;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int SEARCH_ACTIVITY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        showMenu = (Button) findViewById(R.id.show_dropdown_menu);
        showMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context wrapper = new ContextThemeWrapper(getApplicationContext(), R.style.PopupMenu);
                PopupMenu dropDownMenu = new PopupMenu(wrapper, showMenu);
                dropDownMenu.getMenuInflater().inflate(R.menu.drop_down_menu, dropDownMenu.getMenu());
                showMenu.setText("Menu");
                dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Toast.makeText(getApplicationContext(), "You have clicked" + menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        switch (menuItem.getTitle().toString()) {
                            case "Add Card": takePhoto(menuItem);
                            case "View Collection":startCollectionActivity(menuItem);
                            case "Search Card":startSearchActivity(menuItem);
                        }
                        return true;
                    }

                });
                dropDownMenu.show();
            }

        });

    }

    public void takePhoto(MenuItem menuItem) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                //Uri photoURI = FileProvider.getUriForFile(this,"com.example.cloudcards",photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,mCurrentPhotoPath);
                this.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    public void startCollectionActivity(MenuItem menuItem) {

    }

    public void startSearchActivity(MenuItem menuItem) {

    }

    public File createImageFile() throws IOException{
        // Create an image file name
        String name = ""+System.currentTimeMillis();
        //File imagePath = new File(getApplicationContext().getFilesDir(), "temp");
        File image = File.createTempFile(name, ".jpg", this.getExternalFilesDir(Environment.DIRECTORY_PICTURES) );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEARCH_ACTIVITY_REQUEST_CODE) {

        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            ImageView mImageView = (ImageView) findViewById(R.id.imageView);
            mImageView.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
            //photos = presenter.findPhotos(new Date(Long.MIN_VALUE), new Date(), "", "", "");
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }
}