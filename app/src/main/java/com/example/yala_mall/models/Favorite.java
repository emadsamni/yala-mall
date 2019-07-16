package com.example.yala_mall.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import bolts.Bolts;

public class Favorite implements Serializable {


    @SerializedName("id")
    private
    Integer id;

    @SerializedName("customer_id")
    private
    String customerId;

    @SerializedName("product_id")
    private
    Integer productId;


    private
    Integer favoriteStatus;

    private
    Boolean myStatus;


    public Integer getFavoriteStatus() {
        return favoriteStatus;
    }

    public void setFavoriteStatus(Integer favoriteStatus) {
        this.favoriteStatus = favoriteStatus;
    }

    public void setMyStatus(Boolean myStatus) {
        this.myStatus = myStatus;
    }

    public Boolean getMyStatus() {
        return myStatus;
    }

    public Integer getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Integer getProductId() {
        return productId;
    }


}
