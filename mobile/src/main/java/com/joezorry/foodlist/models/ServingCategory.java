
package com.joezorry.foodlist.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServingCategory {

    @JsonProperty("name_fr")
    private String nameFr;

    @JsonProperty("name_de")
    private String nameDe;

    @JsonProperty("name_da")
    private String nameDa;

    @JsonProperty("name_ru")
    private String nameRu;

    @JsonProperty("linearsize")
    private int linearsize;

    @JsonProperty("name_it")
    private String nameIt;

    @JsonProperty("name_pl")
    private String namePl;

    @JsonProperty("lastupdated")
    private int lastupdated;

    @JsonProperty("source")
    private int source;

    @JsonProperty("name_pt")
    private String namePt;

    @JsonProperty("usemedian")
    private int usemedian;

    @JsonProperty("name_es")
    private String nameEs;

    @JsonProperty("linearquantity")
    private double linearquantity;

    @JsonProperty("oid")
    private int oid;

    @JsonProperty("name_en")
    private String nameEn;

    @JsonProperty("name_nl")
    private String nameNl;

    @JsonProperty("name_sv")
    private String nameSv;

    @JsonProperty("name")
    private String name;

    @JsonProperty("name_no")
    private String nameNo;

    @JsonProperty("created")
    private int created;

    @JsonProperty("defaultsize")
    private int defaultsize;
}