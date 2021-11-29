package com.example.cloudcards;

import android.view.View;

import java.util.ArrayList;

public interface CloudCards {
    interface CollectionActivityPresenter {
        void go_CollectionInfo();
        void onDestroy();
    }
    interface CollectionActivityView {
        void showCollection(CollectionAdapter adapter);

    }

    interface CollectionSearchActivityView {

    }

    interface CollectionSearchActivityPresenter {
        ArrayList<Card> searchCardsByName(int userID, String search_val);
        void onDestroy();
    }


}
