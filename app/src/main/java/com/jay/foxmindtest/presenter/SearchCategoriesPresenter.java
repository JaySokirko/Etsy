package com.jay.foxmindtest.presenter;

import com.jay.foxmindtest.SearchCategoriesContract;
import com.jay.foxmindtest.model.network.api.CategoriesLoader;

import java.util.ArrayList;

import javax.inject.Inject;

public class SearchCategoriesPresenter implements
        SearchCategoriesContract.Presenter, SearchCategoriesContract.Model.OnLoadFinishedListener{

    private SearchCategoriesContract.View view;

    private SearchCategoriesContract.Model model = new CategoriesLoader();

    @Inject
    public SearchCategoriesPresenter(SearchCategoriesContract.View view) {
        this.view = view;
    }

    @Override
    public void loadCategories() {

        if (view != null){

            view.showProgress();
            model.loadData(this);
        }
    }

    @Override
    public void onDestroy() {

        this.view = null;
        this.model = null;
    }


    @Override
    public void onLoadFinished(ArrayList<String> categoriesList) {

        if (view != null){

            view.hideProgress();
            view.onSuccessResponse(categoriesList);
        }
    }

    @Override
    public void onLoadFailure(Throwable throwable) {

        if (view != null){

            view.hideProgress();
            view.onFailureResponse(throwable);
        }

    }
}
