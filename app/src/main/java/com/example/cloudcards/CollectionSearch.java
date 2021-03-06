package com.example.cloudcards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cloudcards.Presenter.CollectionAdapterPresenter;
import com.example.cloudcards.View.HomepageActivity;
import com.example.cloudcards.Model.DBHelper;

import java.util.ArrayList;

public class CollectionSearch extends AppCompatActivity {

    private Button backButton;
    private DBHelper DB;
    int userID;
    EditText search_bar;
    Button search_button;
    RecyclerView collectionRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_search);
        DB = new DBHelper(getApplicationContext());
        userID = getIntent().getIntExtra("userID", 0);
        search_bar = (EditText) findViewById(R.id.search_bar);
        search_button = (Button) findViewById(R.id.search_button);
        collectionRecycler = findViewById(R.id.collection_recycler);
        backButton = findViewById(R.id.backButton);

        search_button.setOnClickListener(view -> {
            String search = search_bar.getText().toString();
            if (search.equals("")) {
                Toast.makeText(getApplicationContext(), "Empty search field, please type something.", Toast.LENGTH_SHORT).show();
            } else {
                ArrayList<Card> cardList = searchCardsByName(userID, search);
                if (cardList.size() == 0) {
                    Toast.makeText(getApplicationContext(), "No Matching Cards Found", Toast.LENGTH_LONG).show();
                } else {
                    setCollectionAdapter(cardList);
                }
            }
        });
        backButton.setOnClickListener(view -> {
            goBackHome(view);
        });
    }

    private void goBackHome(View view) {
        Intent i = new Intent(getApplicationContext(), HomepageActivity.class);
        i.putExtra("userID", userID);
        startActivity(i);
    }

    public ArrayList<Card> searchCardsByName(int userID, String search_val){
        ArrayList<Card> cards = new ArrayList<>();
        SQLiteDatabase db = DB.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from cards where userID = ? " +
                "and card_name LIKE '%"+search_val+"%' ", new String[] {String.valueOf(userID)});
        if(cursor.moveToFirst()) {
            do {
                cards.add(
                        new Card(Integer.parseInt(cursor.getString(1)),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5),
                                Integer.parseInt(cursor.getString(6)),
                                Integer.parseInt(cursor.getString(7)))
                );
            } while (cursor.moveToNext());
        }
        return cards;
    };

    private void setCollectionAdapter(ArrayList<Card> cards) {
        try {
            CollectionAdapterPresenter adapter = new CollectionAdapterPresenter(cards);
            collectionRecycler.setAdapter(adapter);
            StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            collectionRecycler.setLayoutManager(lm);
            adapter.setListener(cardName -> {
                Intent i = new Intent(CollectionSearch.this, CardDetail.class);
                i.putExtra("cardName", cardName);
                startActivity(i);
            });
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}