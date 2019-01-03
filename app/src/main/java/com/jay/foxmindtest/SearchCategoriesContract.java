package com.jay.foxmindtest;

import java.util.ArrayList;

public interface SearchCategoriesContract {

    interface View{

        void showProgress();

        void hideProgress();

        void onSuccessResponse(ArrayList<String> categoriesList);

        void onFailureResponse(Throwable throwable);
    }


    interface Presenter{

        void loadCategories();

        void onDestroy();
    }


    interface Model{

        interface OnLoadFinishedListener{

            void onLoadFinished(ArrayList<String> categoriesList);

            void onLoadFailure(Throwable throwable);
        }

        void loadData(OnLoadFinishedListener listener);
    }
}
