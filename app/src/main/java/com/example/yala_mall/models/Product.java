package com.example.yala_mall.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

    @SerializedName("id")
    private
    Integer id;

    @SerializedName("name")
    private
    String name;

    @SerializedName("available")
    private
    int available;


    @SerializedName("description")
    private
    String description;

    @SerializedName("price")
    private
    String price;

    @SerializedName("discount")
    private
    String discount;

    @SerializedName("mall_id")
    private
    int mall_id;

    @SerializedName("shop_id")
    private
    int shop_id;

    @SerializedName("size_pcategory_id")
    private
    int size_pcategory_id;

    @SerializedName("gallery")
    private
    List<Gallery> gallery;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAvailable() {
        return available;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getDiscount() {
        return discount;
    }

    public int getMall_id() {
        return mall_id;
    }

    public int getShop_id() {
        return shop_id;
    }

    public int getSize_pcategory_id() {
        return size_pcategory_id;
    }

    public List<Gallery> getGallery() {
        return gallery;
    }
}
