
package com.joezorry.foodlist.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.Table;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Table
public class Food {

    @JsonProperty("categoryid")
    private int categoryid;

    @JsonProperty("headcategoryid")
    private int headcategoryid;

    @JsonProperty("showonlysametype")
    private int showonlysametype;

    @JsonProperty("fiber")
    private double fiber;

    @Column(indexed = true)
    @JsonProperty("id")
    public int id;

    @Column
    @JsonProperty("protein")
    public double protein;

    @JsonProperty("unsaturatedfat")
    private double unsaturatedfat;

    @JsonProperty("saturatedfat")
    private double saturatedfat;

    @JsonProperty("category")
    private String category;

    @JsonProperty("verified")
    private boolean verified;

    @JsonProperty("sodium")
    private double sodium;

    @JsonProperty("carbohydrates")
    private double carbohydrates;

    @JsonProperty("mlingram")
    private double mlingram;

    @JsonProperty("sugar")
    private double sugar;

    @JsonProperty("source")
    private String source;

    @JsonProperty("measurementid")
    private int measurementid;

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("servingcategory")
    private String servingcategory;

    @JsonProperty("pcsingram")
    private double pcsingram;

    @JsonProperty("potassium")
    private double potassium;

    @JsonProperty("fat")
    private double fat;

    @JsonProperty("typeofmeasurement")
    private int typeofmeasurement;

    @JsonProperty("defaultserving")
    private Object defaultserving;

    @JsonProperty("pcstext")
    private String pcstext;

    @Column(indexed = true)
    @JsonProperty("title")
    public String title;

    @JsonProperty("calories")
    private int calories;

    @JsonProperty("cholesterol")
    private double cholesterol;

    @JsonProperty("gramsperserving")
    private double gramsperserving;

    @JsonProperty("showmeasurement")
    private int showmeasurement;

    @Column
    @JsonIgnore
    public boolean isFavorite;

    public int getCategoryid() {
        return categoryid;
    }

    public int getHeadcategoryid() {
        return headcategoryid;
    }

    public int getShowonlysametype() {
        return showonlysametype;
    }

    public double getFiber() {
        return fiber;
    }

    public int getId() {
        return id;
    }

    public double getProtein() {
        return protein;
    }

    public double getUnsaturatedfat() {
        return unsaturatedfat;
    }

    public double getSaturatedfat() {
        return saturatedfat;
    }

    public String getCategory() {
        return category;
    }

    public boolean isVerified() {
        return verified;
    }

    public double getSodium() {
        return sodium;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public double getMlingram() {
        return mlingram;
    }

    public double getSugar() {
        return sugar;
    }

    public String getSource() {
        return source;
    }

    public int getMeasurementid() {
        return measurementid;
    }

    public String getBrand() {
        return brand;
    }

    public String getServingcategory() {
        return servingcategory;
    }

    public double getPcsingram() {
        return pcsingram;
    }

    public double getPotassium() {
        return potassium;
    }

    public double getFat() {
        return fat;
    }

    public int getTypeofmeasurement() {
        return typeofmeasurement;
    }

    public Object getDefaultserving() {
        return defaultserving;
    }

    public String getPcstext() {
        return pcstext;
    }

    public String getTitle() {
        return title;
    }

    public int getCalories() {
        return calories;
    }

    public double getCholesterol() {
        return cholesterol;
    }

    public double getGramsperserving() {
        return gramsperserving;
    }

    public int getShowmeasurement() {
        return showmeasurement;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void setFavorite(final boolean favorite) {
        isFavorite = favorite;
    }
}
