package com.example.yala_mall.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Slide  implements Serializable {


    @SerializedName("id")
    private
    int id;

    @SerializedName("title")
    private
    String title;

    @SerializedName("image")
    private
    String image;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }
}
