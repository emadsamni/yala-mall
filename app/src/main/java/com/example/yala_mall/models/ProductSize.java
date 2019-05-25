package com.example.yala_mall.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public  class ProductSize implements Serializable {



    @SerializedName("id")
    private
    Integer id;

    @SerializedName("product_id")
    private
    Integer product_id;

    @SerializedName("sizes")
    private
    Size sizes;

    public Size getSizes() {
        return sizes;
    }

    @NonNull
    @Override
    public String toString() {
        return sizes.getName();
    }

}
