package com.example.yala_mall.models;

import com.google.gson.annotations.SerializedName;

public class Gallery {

    @SerializedName("id")
    private
    Integer id;

    @SerializedName("image")
    private
    String image;

    public Integer getId() {
        return id;
    }

    public String getImage() {
        return image;
    }
}
