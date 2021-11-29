package com.example.cloudcards.View;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.cloudcards.Card;
import com.example.cloudcards.CardDetail;
import com.example.cloudcards.CloudCards;
import com.example.cloudcards.Listener;
import com.example.cloudcards.Presenter.CollectionAdapterPresenter;
import com.example.cloudcards.CollectionSearch;
import com.example.cloudcards.Model.DBHelper;
import com.example.cloudcards.Presenter.CollectionSearchActivityPresenter;
import com.example.cloudcards.R;

import java.util.ArrayList;

public class CollectionSearchActivity extends AppCompatActivity implements CloudCards.CollectionSearchActivityView {
    private Button backButton;
    private CollectionSearchActivityPresenter presenter;
    int userID;
    EditText search_bar;
    Button search_button;
    RecyclerView collectionRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_search);


        userID = getIntent().getIntExtra("userID", 0);
        presenter = new CollectionSearchActivityPresenter(this, userID);
        search_bar = (EditText) findViewById(R.id.search_bar);
        search_button = (Button) findViewById(R.id.search_button);
        collectionRecycler = findViewById(R.id.collection_recycler);
        backButton = findViewById(R.id.backButton);

        //Maybe refactor this??? not sure tbh. Does deal with getting some data
        search_button.setOnClickListener(view -> {
            String search = search_bar.getText().toString();
            if (search.equals("")) {
                Toast.makeText(getApplicationContext(), "Empty search field, please type something.", Toast.LENGTH_SHORT).show();
            } else {
                ArrayList<Card> cardList = presenter.searchCardsByName(userID, search);
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



    private void setCollectionAdapter(ArrayList<Card> cards) {
        try {
            CollectionAdapterPresenter adapter = new CollectionAdapterPresenter (cards);
            collectionRecycler.setAdapter(adapter);
            StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            collectionRecycler.setLayoutManager(lm);
            adapter.setListener(cardName -> {
                Intent i = new Intent(CollectionSearchActivity.this, CardDetail.class);
                i.putExtra("cardName", cardName);
                startActivity(i);
            });
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        presenter.onDestroy();
    }
}
