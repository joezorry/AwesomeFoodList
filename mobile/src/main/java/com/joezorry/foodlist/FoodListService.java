package com.joezorry.foodlist;

import com.joezorry.foodlist.models.SearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FoodListService {

    @GET("icebox/v1/foods/en/se/{search}/")
    Call<SearchResult> searchResult(@Path("search") String searchQuery);
}
