
package com.joezorry.foodlist.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResult {

    @JsonProperty("serving_categories")
    private List<ServingCategory> mServingCategories;

    @JsonProperty("title_completed")
    private String mTitleCompleted;

    @JsonProperty("title_requested")
    private String mTitleRequested;

    @JsonProperty("language_requested")
    private String mLanguageRequested;

    @JsonProperty("food")
    private List<Food> mFoods;

    @JsonProperty("serving_sizes")
    private List<ServingSize> mServingSizes;

    public List<ServingCategory> getServingCategories() {
        return mServingCategories;
    }

    public String getTitleCompleted() {
        return mTitleCompleted;
    }

    public String getTitleRequested() {
        return mTitleRequested;
    }

    public String getLanguageRequested() {
        return mLanguageRequested;
    }

    public List<Food> getFood() {
        return mFoods;
    }

    public List<ServingSize> getServingSizes() {
        return mServingSizes;
    }
}
