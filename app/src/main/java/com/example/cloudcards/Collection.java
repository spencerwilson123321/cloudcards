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

import java.util.concurrent.CompletableFuture;

public class Collection extends AppCompatActivity {
    private Button showMenu;
    private DBHelper db;
    private APIHelper API;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        API = new APIHelper(getApplicationContext());
        setContentView(R.layout.collection);
        setShowMenu();
        setCollectionAdapter();
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

        });
    }

    private void setCollectionAdapter() {
        try {
            RecyclerView collectionRecycler = findViewById(R.id.collection_recycler);
            Card[] test_cards = Card.getAllCards();

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
                public void onClick(String foodName) {
                    Intent i = new Intent(Collection.this, Homepage.class);
                    startActivity(i);
                }
            });

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}