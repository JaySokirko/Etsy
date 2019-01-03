package com.jay.foxmindtest.model.network.api;

import android.support.annotation.NonNull;

import com.jay.foxmindtest.SearchCategoriesContract;
import com.jay.foxmindtest.model.network.entity.*;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesLoader implements SearchCategoriesContract.Model {

    @Override
    public void loadData(final OnLoadFinishedListener listener) {

        ApiService apiService =
                ApiClient.getClient().create(ApiService.class);

        apiService.getCategories().enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(@NonNull Call<Categories> call, @NonNull Response<Categories> response) {

                ArrayList<String> categoriesList = new ArrayList<>();

                if (response.body() != null) {

                    for (Category result : response.body().getResults()){

                        categoriesList.add(result.getName().replaceAll("_"," "));
                    }
                }

                listener.onLoadFinished(categoriesList);
            }

            @Override
            public void onFailure(@NonNull Call<Categories> call, @NonNull Throwable t) {

                listener.onLoadFailure(t);
            }
        });
    }
}
