package com.jay.foxmindtest.model.network.api;

import com.jay.foxmindtest.model.network.entity.*;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiService {

    @GET("taxonomy/categories?api_key=hz1eg53dxyxrqkqg3c6yczby")
    Call<Categories> getCategories();


    @GET
    Call<Products> getProducts(@Url String category);
}
