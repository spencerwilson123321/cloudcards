package com.example.cloudcards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.api.MTGAPI;
import io.magicthegathering.javasdk.resource.Card;


public class Homepage extends AppCompatActivity  implements MenuItem.OnMenuItemClickListener {

    private Button showMenu;
    private APIHelper API;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        API = new APIHelper(getApplicationContext());

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
                        switch (menuItem.getTitle().toString()) {
                            case "View Collection":
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

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }
}