package com.example.cloudcards.Presenter;

import android.content.Context;

import com.example.cloudcards.Card;
import com.example.cloudcards.CloudCards;
import com.example.cloudcards.Model.DBHelper;

import java.util.ArrayList;

public class CollectionActivityPresenter implements CloudCards.CollectionActivityPresenter {
    private DBHelper DB;
    private CloudCards.CollectionActivityView cView;
    private int userID;

    public CollectionAdapterPresenter getAdapter() {
        return adapter;
    }

    private CollectionAdapterPresenter adapter;
    public CollectionActivityPresenter(Context context, CloudCards.CollectionActivityView collectionView, int uID) {
        DB = new DBHelper(context);
        cView = collectionView;
        userID = uID;
    }

    @Override
    public void go_CollectionInfo() {
        ArrayList<Card> cards = DB.getCardsByUserID(userID);
        //model returns data to presenter
        CollectionAdapterPresenter adapter = new CollectionAdapterPresenter(cards);
        //returns data to view
        cView.showCollection(adapter);
    }

    @Override
    public void onDestroy() {
        cView = null;
    }

    
}
