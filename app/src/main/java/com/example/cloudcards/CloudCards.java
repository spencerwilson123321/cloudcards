package com.example.cloudcards;

import android.view.View;

public interface CloudCards {
    interface CollectionActivityPresenter {
        void go_CollectionInfo();
        void onDestroy();
    }
    interface CollectionActivityView {
        void showCollection(CollectionAdapter adapter);

    }


}
