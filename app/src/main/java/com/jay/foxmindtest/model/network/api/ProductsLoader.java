package com.jay.foxmindtest.model.network.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jay.foxmindtest.SearchProductsContract;
import com.jay.foxmindtest.model.network.entity.Product;
import com.jay.foxmindtest.model.network.entity.Products;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsLoader implements SearchProductsContract.Model {

    private ArrayList<String> titlesList = new ArrayList<>();
    private ArrayList<String> imagesList = new ArrayList<>();
    private ArrayList<String> descriptionsList = new ArrayList<>();
    private ArrayList<String> pricesList = new ArrayList<>();
    private ArrayList<String> currenciesCodesList = new ArrayList<>();

    private ApiService apiService = ApiClient.getClient().create(ApiService.class);

    @Override
    public void startLoadProducts(LoadFinishedListener listener, String category) {

        String apiKey = "&api_key=l6pdqjuf7hdf97h1yvzadfce";
        String url = "listings/active?includes=Images&category=";
        String address = url + category + apiKey;

        apiService.getProducts(address).enqueue(new Callback<Products>() {
            @Override
            public void onResponse(@NonNull Call<Products> call, @NonNull Response<Products> response) {

                if (response.body() != null) {

                    titlesList.clear();
                    imagesList.clear();
                    descriptionsList.clear();
                    pricesList.clear();
                    currenciesCodesList.clear();

                    for (Product products : response.body().getProductResults()) {

                        if (products.getTitle() != null) {
                            titlesList.add(products.getTitle()
                                    .replaceAll("&#39;", "\'")
                                    .replaceAll("&quot;", "\"")
                                    .replaceAll("&lt;", "<")
                                    .replaceAll("&gt;", ">"));
                        }

                        if (products.getImages() != null) {
                            imagesList.add(products.getImages().get(0).getImage170x135());
                        }

                        if (products.getDescription() != null) {
                            descriptionsList.add(products.getDescription()
                                    .replaceAll("&#39;", "\'")
                                    .replaceAll("&quot;", "\"")
                                    .replaceAll("&lt;", "<")
                                    .replaceAll("&gt;", ">"));
                        }

                        if (products.getPrice() != null) {
                            pricesList.add(products.getPrice());
                        }

                        if (products.getCurrencyCode() != null) {
                            currenciesCodesList.add(products.getCurrencyCode());
                        }
                    }
                }
                listener.onLoadFinished(titlesList, imagesList, descriptionsList, pricesList,
                        currenciesCodesList);
            }

            @Override
            public void onFailure(@NonNull Call<Products> call, @NonNull Throwable t) {

                listener.onLoadFailure(t);
            }
        });
    }


    @Override
    public void startLoadNextPage(LoadFinishedListener listener, String category, int pageIndex) {

        String page = String.valueOf(pageIndex);

        String apiKey = "&api_key=l6pdqjuf7hdf97h1yvzadfce";
        String url = "listings/active?includes=Images&category=";
        String address = url + category + "&page=" + page + apiKey;

        apiService.getProducts(address).enqueue(new Callback<Products>() {
            @Override
            public void onResponse(@NonNull Call<Products> call, @NonNull Response<Products> response) {

                if (response.body() != null) {

                    titlesList.clear();
                    imagesList.clear();
                    descriptionsList.clear();
                    pricesList.clear();
                    currenciesCodesList.clear();

                    for (Product products : response.body().getProductResults()) {

                        if (products.getTitle() != null) {
                            titlesList.add(products.getTitle()
                                    .replaceAll("&#39;", "\'")
                                    .replaceAll("&quot;", "\"")
                                    .replaceAll("&lt;", "<")
                                    .replaceAll("&gt;", ">"));
                        }

                        if (products.getImages() != null) {
                            imagesList.add(products.getImages().get(0).getImage170x135());
                        }

                        if (products.getDescription() != null) {
                            descriptionsList.add(products.getDescription()
                                    .replaceAll("&#39;", "\'")
                                    .replaceAll("&quot;", "\"")
                                    .replaceAll("&lt;", "<")
                                    .replaceAll("&gt;", ">"));
                        }

                        if (products.getPrice() != null) {
                            pricesList.add(products.getPrice());
                        }

                        if (products.getCurrencyCode() != null) {
                            currenciesCodesList.add(products.getCurrencyCode());
                        }
                    }
                }
                listener.onLoadFinished(titlesList, imagesList, descriptionsList, pricesList,
                        currenciesCodesList);
            }

            @Override
            public void onFailure(@NonNull Call<Products> call, @NonNull Throwable t) {

                listener.onLoadFailure(t);
            }
        });
    }
}
