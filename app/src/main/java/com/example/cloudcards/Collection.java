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
    }

    private void showActionBar() {
        Toolbar searchBar = (Toolbar) findViewById(R.id.search_toolbar);
        searchBar.setTitle("Search Collection");
        setSupportActionBar(searchBar);
    }

    private void setShowMenu() {
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
                        switch (menuItem.getItemId()) {
                            case R.id.dropdown_menu2:
                                Intent intent = new Intent(getApplicationContext(), Collection.class);
                                startActivity(intent);
                                return true;
                        }
                        return true;
                    }

                });
                dropDownMenu.show();
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
            //ArrayList<Card> cards = new ArrayList<>(Arrays.asList(Card.getAllCards()));

            adapter = new CollectionAdapter(cards);
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