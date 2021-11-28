package com.example.cloudcards.Presenter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cloudcards.Card;
import com.example.cloudcards.CloudCards;
import com.example.cloudcards.Model.DBHelper;

import java.util.ArrayList;

public class CollectionSearchActivityPresenter implements CloudCards.CollectionSearchActivityPresenter {
    private DBHelper DB;
    private Context context;
    private int userID;

    public CollectionSearchActivityPresenter(Context c, int uID){
        context = c;
        DB = new DBHelper(context);
        userID = uID;
    }

    @Override
    public ArrayList<Card> searchCardsByName(int userID, String search_val){
        return DB.getCardsByName(search_val, userID);
    }

    @Override
    public void onDestroy(){
        context = null;
        DB = null;
    }
}
