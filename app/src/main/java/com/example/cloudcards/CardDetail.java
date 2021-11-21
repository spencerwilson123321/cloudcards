package com.example.cloudcards;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CardDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_detail);
        displayCard();
    }

    private void displayCard() {
        String cardName = (String) getIntent().getExtras().get("cardName");

        Card card = Card.getCardByName(cardName);

        if(card != null) {
            TextView card_Name = findViewById(R.id.card_name);
            card_Name.setText(card.getCard_name());

            TextView card_text = findViewById(R.id.card_text);
            card_text.setText(card.getCard_text());

            ImageView card_img = findViewById(R.id.card_image);
            Picasso
                    .get()
                    .load(card.getCard_img())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(card_img);

        }
    }
}