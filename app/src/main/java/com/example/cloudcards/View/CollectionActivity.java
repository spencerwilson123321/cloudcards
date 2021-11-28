package com.example.cloudcards.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.example.cloudcards.CardDetail;
import com.example.cloudcards.CloudCards;

import com.example.cloudcards.CollectionAdapter;
import com.example.cloudcards.Presenter.CollectionActivityPresenter;
import com.example.cloudcards.R;




public class CollectionActivity extends AppCompatActivity implements CloudCards.CollectionActivityView {
    private CollectionActivityPresenter presenter;
    private Button backButton;
    private RecyclerView collectionRecycler;
    private CollectionAdapter tmpAdapter;
    private int userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection);

        backButton = findViewById(R.id.backButton);
        userID = getIntent().getIntExtra("userID", 0);
        collectionRecycler = findViewById(R.id.collection_recycler);
        presenter = new CollectionActivityPresenter(getApplicationContext(), this, userID);
        setCollectionAdapter();
        showActionBar();
        backButton.setOnClickListener(view -> {
            goBackHome(view);
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                tmpAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    private void showActionBar() {
        Toolbar searchBar = (Toolbar) findViewById(R.id.search_toolbar);
        searchBar.setTitle("Search Collection");
        setSupportActionBar(searchBar);
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
        tmpAdapter = adapter;
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
        Intent i = new Intent(getApplicationContext(), HomepageActivity.class);
        i.putExtra("userID", userID);
        startActivity(i);
    }
}
