
package com.joezorry.foodlist.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResult {

    @JsonProperty("serving_categories")
    private List<ServingCategory> servingCategories = new ArrayList<ServingCategory>();

    @JsonProperty("title_completed")
    private String titleCompleted;

    @JsonProperty("title_requested")
    private String titleRequested;

    @JsonProperty("language_requested")
    private String languageRequested;

    @JsonProperty("food")
    private List<Food> food = new ArrayList<Food>();

    @JsonProperty("serving_sizes")
    private List<ServingSize> servingSizes = new ArrayList<ServingSize>();

}
