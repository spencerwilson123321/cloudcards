package com.example.cloudcards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cloudcards.View.HomepageActivity;
import com.example.cloudcards.Model.DBHelper;

import java.util.ArrayList;

public class Collection extends AppCompatActivity {
    private Button backButton;
    private DBHelper DB;

    int userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DB = new DBHelper(getApplicationContext());
        backButton = findViewById(R.id.backButton);
        userID = getIntent().getIntExtra("userID", 0);
        setCollectionAdapter();
        backButton.setOnClickListener(view -> {
            goBackHome(view);
        });
    }

    private void goBackHome(View view){
        Intent i = new Intent(getApplicationContext(), HomepageActivity.class);
        i.putExtra("userID", userID);
        startActivity(i);
    }

    private void setCollectionAdapter() {
        try {
            //              view
            RecyclerView collectionRecycler = findViewById(R.id.collection_recycler);
            //call to presenter
            //              presenter
            //call to model
            ArrayList<Card> cards = DB.getCardsByUserID(userID);
            //model returns data to presenter
            Card[] test_cards = new Card[cards.size()];
            test_cards = cards.toArray(test_cards);

            String[] cardNames = new String[test_cards.length];
            String[] images = new String[test_cards.length];

            for(int i = 0; i < test_cards.length; i++) {
                cardNames[i] = test_cards[i].getCard_name();
                images[i] = test_cards[i].getCard_img();
            }
            //returns data to view
            //              view
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