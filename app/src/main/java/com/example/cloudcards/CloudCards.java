package com.example.cloudcards;

import com.example.cloudcards.Presenter.CollectionAdapterPresenter;

public interface CloudCards {
    interface CollectionActivityPresenter {
        void go_CollectionInfo();
        void onDestroy();
    }
    interface CollectionActivityView {
        void showCollection(CollectionAdapterPresenter adapter);

    }


}
