package com.example.cloudcards.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.example.cloudcards.CardDetail;
import com.example.cloudcards.CloudCards;

import com.example.cloudcards.CollectionAdapter;
import com.example.cloudcards.Homepage;
import com.example.cloudcards.Presenter.CollectionActivityPresenter;
import com.example.cloudcards.R;
import com.example.cloudcards.database.DBHelper;



public class CollectionActivity extends AppCompatActivity implements CloudCards.CollectionActivityView {
    private CollectionActivityPresenter presenter;
    private Button backButton;
    private RecyclerView collectionRecycler;
    private int userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection);
        backButton = findViewById(R.id.backButton);
        userID = getIntent().getIntExtra("userID", 0);
        collectionRecycler = findViewById(R.id.collection_recycler);
        presenter = new CollectionActivityPresenter(new DBHelper(getApplicationContext()), this, userID);
        setCollectionAdapter();
        backButton.setOnClickListener(view -> {
            goBackHome(view);
        });

    }

    private void setCollectionAdapter() {
        try {
            presenter.go_CollectionInfo();

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void showCollection(CollectionAdapter adapter) {

        collectionRecycler.setAdapter(adapter);
        StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        collectionRecycler.setLayoutManager(lm);
        adapter.setListener(cardName -> {
            Intent i = new Intent(CollectionActivity.this, CardDetail.class);
            i.putExtra("cardName", cardName);
            startActivity(i);
        });
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
    
    private void goBackHome(View view){
        Intent i = new Intent(getApplicationContext(), Homepage.class);
        i.putExtra("userID", userID);
        startActivity(i);
    }
}
