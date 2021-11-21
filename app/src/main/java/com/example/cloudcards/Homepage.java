package com.example.cloudcards;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
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

import com.example.cloudcards.database.DBHelper;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.api.MTGAPI;


public class Homepage extends AppCompatActivity  implements MenuItem.OnMenuItemClickListener {

    // private Button showMenu;
    Button showMenu;
    private String mCurrentPhotoPath;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int SEARCH_ACTIVITY_REQUEST_CODE = 2;
    private Context cont;
    ImageView image_preview;
    Uri image_uri;
    String cameraPermission[];
    String storagePermission[];
    String name;
    int userID;
    private APIHelper API;
    private DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        // Setup card collection
        setCollectionAdapter();
        userID = getIntent().getIntExtra("userID", 0);
        API = new APIHelper(getApplicationContext());
        DB = new DBHelper(getApplicationContext());
        this.cont = this;
        // Getting image preview
//        image_preview = findViewById(R.id.image_preview);
        // permissions
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

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
                            case "Add Card": takePhoto();
                            break;
                            case "View Collection":
                                Intent intent = new Intent(getApplicationContext(), Collection.class);
                                startActivity(intent);
                                // return true;
                            break;
                            case "Search Card":startSearchActivity(menuItem);
                            break;
                        }
                        return true;
                    }

                });
                dropDownMenu.show();
            }

        });

    }

    private void pickCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "NewPic");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image to text");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        // Create and start the camera intent.
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        this.startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
    }

    public void takePhoto() {
        if(!checkCameraPermissions()){
            requestCameraPermission();
        } else {
            pickCamera();
        }
    }

    public void startCollectionActivity(MenuItem menuItem) {
//        try {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//            MTGAPI.setConnectTimeout(60);
//            MTGAPI.setReadTimeout(60);
//            MTGAPI.setWriteTimeout(60);
//            List<String> a = new ArrayList<>();
//            a.add("name:avacyn");
//            List<Card> card = CardAPI.getAllCards(a);
//            Toast.makeText(this, card.get(0).getName(), Toast.LENGTH_LONG).show();
//
//        }catch (Exception e) {
//
//        }
    }

    public void startSearchActivity(MenuItem menuItem) {

    }

    public File createImageFile(String name) {
        File a = null;
        try {
            a = File.createTempFile(name, ".jpg", this.getExternalFilesDir(Environment.DIRECTORY_PICTURES) );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return a;
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEARCH_ACTIVITY_REQUEST_CODE) {

        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            CropImage.activity(image_uri).setGuidelines(CropImageView.Guidelines.ON).start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                // set image to imageview
                //image_preview.setImageURI(resultUri);
                // get drawable bitmap for text recognition
                Bitmap uriBit =  null;
                try {
                    uriBit = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                BitmapDrawable bitmapDrawable = (BitmapDrawable) image_preview.getDrawable();
//                Bitmap bitmap = bitmapDrawable.getBitmap();
                TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();
                if (!recognizer.isOperational()) {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                } else {
                    Frame frame = new Frame.Builder().setBitmap(uriBit).build();
                    SparseArray<TextBlock> items = recognizer.detect(frame);
                    name = items.valueAt(0).getValue();
                    API.getCardByName(name, new VolleyCallback() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            Card card = makeCardFromResult(result);
                            if (card == null){
                                Toast.makeText(getApplicationContext(), "We were unable to identify your card. Please try again.", Toast.LENGTH_LONG).show();
                            } else{
                                dialogueAddCard(card);
                            }

                        }
                    });
                    Toast.makeText(this, name, Toast.LENGTH_LONG).show();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
                Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermission, 200);
    }

    private boolean checkCameraPermissions() {
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result1 && result2;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 200:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted) {
                        pickCamera();
                    } else {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }

    private Card makeCardFromResult(JSONObject result) {
        Card card;
        try {

            card = new Card(result.getInt("multiverseid"), result.getString("name"), result.getString("imageUrl")
                    ,result.getString("manaCost"), result.getString("flavor"), result.getInt("power"),
                    result.getInt("toughness"), result.getString("type"));
            return card;
        } catch (JSONException a) {
            a.printStackTrace();
            return null;
        }
    }

    private void dialogueAddCard(Card card){
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage("Do you want to add this card to your collection?\n"+card.getCard_name()+"\n"+card.getCard_mana()+"\n"+card.getType())
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbAddCard(card);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();

                    }
                });
    }

    private void dbAddCard(Card card){
        DB.insertCard(userID, card.getCard_id(), card.getCard_img(), card.getCard_name(), card.getCard_mana(), card.getCard_text(), card.getPower(), card.getToughness(), card.getType());
        ArrayList<Card> cards = DB.getCardsByUserID(userID);
        Log.i("cards", cards.toString());
    }

    /* COLLECTION CODE COPIED START HERE */
    private void setCollectionAdapter() {
        try {
            RecyclerView collectionRecycler = findViewById(R.id.collection_recycler);
            com.example.cloudcards.Card[] test_cards = Card.getAllCards();

            String[] cardNames = new String[test_cards.length];
            String[] images = new String[test_cards.length];

            for(int i = 0; i < test_cards.length; i++) {
                cardNames[i] = test_cards[i].getCard_name();
                images[i] = test_cards[i].getCard_img();
            }

            CollectionAdapter adapter = new CollectionAdapter(cardNames, images);
            collectionRecycler.setAdapter(adapter);

            StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            collectionRecycler.setLayoutManager(lm);

            adapter.setListener(new CollectionAdapter.Listener() {
                @Override
                public void onClick(String cardName) {
                    Intent i = new Intent(Homepage.this, CardDetail.class);
                    i.putExtra("cardName", cardName);
                    startActivity(i);
                }
            });

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /* END COLLECTION CODE */
}