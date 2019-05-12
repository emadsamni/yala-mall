package com.example.yala_mall.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Shop implements Serializable {

    @SerializedName("name")
    private
    String name;

    @SerializedName("logo")
    private
    String logo;

    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }
}
