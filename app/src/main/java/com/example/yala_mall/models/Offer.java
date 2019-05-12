package com.example.yala_mall.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Offer implements Serializable {

    @SerializedName("id")
    private
    Integer id;

    @SerializedName("title")
    private
    String title;

    @SerializedName("description")
    private
    String description;

    @SerializedName("price")
    private
    String price;

    @SerializedName("shops")
    private
    Shop shop;

    @SerializedName("image")
    private
    String image;


    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public Shop getShop() {
        return shop;
    }

    public String getImage() {
        return image;
    }
}
