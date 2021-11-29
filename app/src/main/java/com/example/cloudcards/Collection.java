package com.example.cloudcards;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.cloudcards.View.HomepageActivity;
import com.example.cloudcards.Model.DBHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class Collection extends AppCompatActivity {
    private Button backButton;
    private DBHelper DB;

    private APIHelper API;
    private CollectionAdapter adapter;
    int userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DB = new DBHelper(getApplicationContext());
        backButton = findViewById(R.id.backButton);
        userID = getIntent().getIntExtra("userID", 0);
        setContentView(R.layout.collection);
        showActionBar();
        //setShowMenu();
        setCollectionAdapter();
        backButton.setOnClickListener(view -> {
            goBackHome(view);
        });
    }


    private void showActionBar() {
        Toolbar searchBar = (Toolbar) findViewById(R.id.search_toolbar);
        searchBar.setTitle("Search Collection");
        setSupportActionBar(searchBar);
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
            //returns data to view
            //              view
            adapter = new CollectionAdapter(cards);
            collectionRecycler.setAdapter(adapter);
            StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            collectionRecycler.setLayoutManager(lm);
            adapter.setListener(cardName -> {
                Intent i = new Intent(Collection.this, CardDetail.class);
                i.putExtra("cardName", cardName);
                startActivity(i);
            });
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}