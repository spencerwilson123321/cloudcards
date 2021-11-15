package com.example.cloudcards;

import android.content.Context;

import io.magicthegathering.javasdk.api.CardAPI;
import io.magicthegathering.javasdk.resource.Card;

public class APIHelper {

    public APIHelper(Context context){
    }

    public Card getCardByName(String name) {
        Card card = CardAPI.getCard(name);
        return card;
    }


}
