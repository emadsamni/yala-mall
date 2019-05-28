package com.example.yala_mall.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShopStatus   implements Serializable {


    @SerializedName("id")
    private
    int id;

    @SerializedName("name")
    private
    String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}