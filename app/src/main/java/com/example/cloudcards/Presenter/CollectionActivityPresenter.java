package com.example.cloudcards.Presenter;

import android.content.Context;

import com.example.cloudcards.Card;
import com.example.cloudcards.CloudCards;
import com.example.cloudcards.CollectionAdapter;
import com.example.cloudcards.Model.DBHelper;

import java.util.ArrayList;

public class CollectionActivityPresenter implements CloudCards.CollectionActivityPresenter {
    private DBHelper DB;
    private CloudCards.CollectionActivityView cView;
    private int userID;
    public CollectionActivityPresenter(Context context, CloudCards.CollectionActivityView collectionView, int uID) {
        DB = new DBHelper(context);
        cView = collectionView;
        userID = uID;
    }

    @Override
    public void go_CollectionInfo() {
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
        CollectionAdapter adapter = new CollectionAdapter(cardNames, images, cards);
        //returns data to view
        cView.showCollection(adapter);
    }

    @Override
    public void onDestroy() {
        cView = null;
    }

    
}
