package com.jay.foxmindtest;

import java.util.ArrayList;

public interface SearchProductsContract {


    interface View {

        void showProgressBar();

        void hideProgressBar();

        void showBottomProgressBar();

        void hideBottomProgressBar();

        void pagination();

        void stopRefreshing();

        void onSuccessResponse(ArrayList<String> titlesList, ArrayList<String> imagesList,
                               ArrayList<String> descriptionsList, ArrayList<String> pricesList,
                               ArrayList<String> currenciesCodesList);

        void onFailureResponse(Throwable throwable);
    }


    interface Presenter {

        void loadProducts(String category);

        void onPaginationListener();

        void onSwipeRefreshing(String category);

        void loadNextPage(String category, int pageIndex);

        void onDestroy();
    }


    interface Model {

        interface LoadFinishedListener {

            void onLoadFinished(ArrayList<String> titlesList, ArrayList<String> imagesList,
                                ArrayList<String> descriptionsList, ArrayList<String> pricesList,
                                ArrayList<String> currenciesCodesList);

            void onLoadFailure(Throwable throwable);
        }

        void startLoadProducts(LoadFinishedListener listener, String category);

        void startLoadNextPage(LoadFinishedListener listener, String category, int pageIndex);
    }
}
