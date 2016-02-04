
package com.joezorry.foodlist.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServingSize {

    @JsonProperty("name_ru")
    private String nameRu;

    @JsonProperty("name_fr")
    private String nameFr;

    @JsonProperty("name_it")
    private String nameIt;

    @JsonProperty("name")
    private String name;

    @JsonProperty("name_no")
    private String nameNo;

    @JsonProperty("name_pl")
    private String namePl;

    @JsonProperty("created")
    private int created;

    @JsonProperty("proportion")
    private double proportion;

    @JsonProperty("name_es")
    private String nameEs;

    @JsonProperty("countryfilter")
    private String countryfilter;

    @JsonProperty("name_de")
    private String nameDe;

    @JsonProperty("servingcategory")
    private int servingcategory;

    @JsonProperty("lastupdated")
    private int lastupdated;

    @JsonProperty("name_da")
    private String nameDa;

    @JsonProperty("name_nl")
    private String nameNl;

    @JsonProperty("name_sv")
    private String nameSv;

    @JsonProperty("name_en")
    private String nameEn;

    @JsonProperty("name_pt")
    private String namePt;

    @JsonProperty("oid")
    private int oid;

    @JsonProperty("source")
    private String source;
}
