package com.example.cloudcards.Presenter;

import androidx.appcompat.app.AlertDialog;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.widget.Toast;
import com.example.cloudcards.APIHelper;
import com.example.cloudcards.Card;
import com.example.cloudcards.VolleyCallback;
import com.example.cloudcards.Model.DBHelper;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import org.json.JSONObject;
import java.io.IOException;

public class HomepageActivityPresenter {

    private Context homepageContext;
    private Activity homepageActivity;
    String name;
    int userID;
    private APIHelper API;
    private DBHelper DB;

    public HomepageActivityPresenter(Context homepageContext, Activity activity, int userID) {
        this.homepageContext = homepageContext;
        homepageActivity = activity;
        this.userID = userID;
        API = new APIHelper(homepageContext);
        DB = new DBHelper(homepageContext);
    }

    public void detectTextOnCropResult(CropImage.ActivityResult result){
        Uri resultUri = result.getUri();
        // get drawable bitmap for text recognition
        Bitmap uriBit =  null;
        try {
            uriBit = MediaStore.Images.Media.getBitmap(homepageContext.getContentResolver(), resultUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        TextRecognizer recognizer = new TextRecognizer.Builder(homepageContext).build();
        if (!recognizer.isOperational()) {
            Toast.makeText(homepageContext, "Error", Toast.LENGTH_SHORT).show();
        } else {
            Frame frame = new Frame.Builder().setBitmap(uriBit).build();
            SparseArray<TextBlock> items = recognizer.detect(frame);
            name = items.valueAt(0).getValue();
            API.getCardByName(name, new VolleyCallback() {
                @Override
                public void onSuccess(JSONObject result) {
                    if (result != null) {
                        Card card = new Card(result);
                        dialogueAddCard(card);
                    } else {
                        Toast.makeText(homepageContext, "Unable to Identify Card, please try again.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    };

    private void dialogueAddCard(Card card){
        AlertDialog.Builder builder = new AlertDialog.Builder(homepageActivity);
        builder.setMessage("Do you want to add this card to your collection?"+"\n"+card.getCard_name());
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean result = DB.insertCard(userID, card);
                if (result == true) {
                    Toast.makeText(homepageContext, "Card added to collection.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(homepageContext, "Add Card Failed.", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {
            dialogInterface.cancel();
            Toast.makeText(homepageContext, "Card not added to Collection", Toast.LENGTH_LONG).show();
        });
        builder.setTitle("Confirm Card");
        builder.show();
    }

}
