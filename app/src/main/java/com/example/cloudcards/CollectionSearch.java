package com.example.cloudcards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.cloudcards.database.DBHelper;

import java.util.ArrayList;

public class CollectionSearch extends AppCompatActivity {

    private Button showMenu;
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

        setShowMenu();
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

    private void setCollectionAdapter(ArrayList<Card> cards) {
        try {
            Card[] test_cards = new Card[cards.size()];
            test_cards = cards.toArray(test_cards);

            String[] cardNames = new String[test_cards.length];
            String[] images = new String[test_cards.length];

            for(int i = 0; i < test_cards.length; i++) {
                cardNames[i] = test_cards[i].getCard_name();
                images[i] = test_cards[i].getCard_img();
            }

            CollectionAdapter adapter = new CollectionAdapter(cards);
            collectionRecycler.setAdapter(adapter);

            StaggeredGridLayoutManager lm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            collectionRecycler.setLayoutManager(lm);

            adapter.setListener(new CollectionAdapter.Listener() {
                @Override
                public void onClick(Card cardName) {
                    Intent i = new Intent(CollectionSearch.this, CardDetail.class);
                    i.putExtra("cardName", cardName);
                    startActivity(i);
                }
            });



        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}