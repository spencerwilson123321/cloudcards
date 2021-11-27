package com.example.cloudcards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cloudcards.database.DBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class Collection extends AppCompatActivity {
    private Button backButton;
    private DBHelper DB;
    private APIHelper API;
    int userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection);
        API = new APIHelper(getApplicationContext());
        DB = new DBHelper(getApplicationContext());
        backButton = findViewById(R.id.backButton);
        userID = getIntent().getIntExtra("userID", 0);
        setCollectionAdapter();
        backButton.setOnClickListener(view -> {
            goBackHome(view);
        });
    }

    private void goBackHome(View view){
        Intent i = new Intent(getApplicationContext(), Homepage.class);
        i.putExtra("userID", userID);
        startActivity(i);
    }

    private void setCollectionAdapter() {
        try {
            RecyclerView collectionRecycler = findViewById(R.id.collection_recycler);

            ArrayList<Card> cards = DB.getCardsByUserID(userID);
            Card[] test_cards = new Card[cards.size()];
            test_cards = cards.toArray(test_cards);

            String[] cardNames = new String[test_cards.length];
            String[] images = new String[test_cards.length];

            for(int i = 0; i < test_cards.length; i++) {
                cardNames[i] = test_cards[i].getCard_name();
                images[i] = test_cards[i].getCard_img();
            }
            CollectionAdapter adapter = new CollectionAdapter(cardNames, images, cards);
            collectionRecycler.setAdapter(adapter);
            StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            collectionRecycler.setLayoutManager(lm);
            adapter.setListener(new CollectionAdapter.Listener() {
                @Override
                public void onClick(Card cardName) {
                    Intent i = new Intent(Collection.this, CardDetail.class);
                    i.putExtra("cardName", cardName);
                    startActivity(i);
                }
            });
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}