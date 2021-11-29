package com.example.cloudcards;

import com.example.cloudcards.Presenter.CollectionAdapterPresenter;

import java.util.ArrayList;

public interface CloudCards {
    interface CollectionActivityPresenter {
        void go_CollectionInfo();
        void onDestroy();
    }
    interface CollectionActivityView {
        void showCollection(CollectionAdapterPresenter adapter);

    }

    interface CollectionSearchActivityView {

    }

    interface CollectionSearchActivityPresenter {
        ArrayList<Card> searchCardsByName(int userID, String search_val);
        void onDestroy();
    }


}
