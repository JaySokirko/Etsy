package com.jay.foxmindtest.presenter;

import com.jay.foxmindtest.SearchProductsContract;
import com.jay.foxmindtest.model.network.api.ProductsLoader;

import java.util.ArrayList;

public class SearchProductsPresenter implements SearchProductsContract.Presenter,
        SearchProductsContract.Model.LoadFinishedListener {


    private SearchProductsContract.View view;
    private SearchProductsContract.Model model = new ProductsLoader();

    private ArrayList<String> titlesList = new ArrayList<>();
    private ArrayList<String> imagesList = new ArrayList<>();
    private ArrayList<String> descriptionsList = new ArrayList<>();
    private ArrayList<String> pricesList = new ArrayList<>();
    private ArrayList<String> currenciesCodesList = new ArrayList<>();


    public SearchProductsPresenter(SearchProductsContract.View view) {
        this.view = view;
    }


    @Override
    public void loadProducts(String category) {

        if (view != null) {

            view.showProgressBar();
            model.startLoadProducts(this, category);
        }
    }


    @Override
    public void onLoadFinished(ArrayList<String> titlesList, ArrayList<String> imagesList,
                               ArrayList<String> descriptionsList, ArrayList<String> pricesList,
                               ArrayList<String> currenciesCodesList) {

        if (view != null) {

            this.titlesList.addAll(titlesList);
            this.imagesList.addAll(imagesList);
            this.descriptionsList.addAll(descriptionsList);
            this.pricesList.addAll(pricesList);
            this.currenciesCodesList.addAll(currenciesCodesList);

            view.hideProgressBar();
            view.hideBottomProgressBar();
            view.stopRefreshing();

            view.onSuccessResponse(titlesList, imagesList, descriptionsList, pricesList, currenciesCodesList);
        }
    }


    @Override
    public void onLoadFailure(Throwable throwable) {

        if (view != null) {

            view.hideProgressBar();
            view.hideBottomProgressBar();
            view.stopRefreshing();

            view.onFailureResponse(throwable);
        }
    }


    @Override
    public void onPaginationListener() {

        if (view != null) {

            view.pagination();
        }
    }


    @Override
    public void onSwipeRefreshing(String category) {

        if (view != null) {

            titlesList.clear();
            imagesList.clear();
            descriptionsList.clear();
            pricesList.clear();
            currenciesCodesList.clear();

            model.startLoadProducts(this, category);
        }
    }


    @Override
    public void loadNextPage(String category, int pageIndex) {

        if (view != null) {

            view.showBottomProgressBar();
            model.startLoadNextPage(this, category, pageIndex);
        }
    }


    @Override
    public void onDestroy() {
        this.view = null;
        this.model = null;
    }


    public ArrayList<String> getTitlesList() {
        return titlesList;
    }

    public ArrayList<String> getImagesList() {
        return imagesList;
    }

    public ArrayList<String> getDescriptionsList() {
        return descriptionsList;
    }

    public ArrayList<String> getPricesList() {
        return pricesList;
    }

    public ArrayList<String> getCurrenciesCodesList() {
        return currenciesCodesList;
    }
}
