package com.joezorry.foodlist;

import com.joezorry.foodlist.models.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FoodListService {

    @GET("icebox/v1/foods/en/se/Orange/")
    Call<SearchResult> searchResult();
}
