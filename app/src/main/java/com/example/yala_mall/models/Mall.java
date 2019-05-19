package com.example.yala_mall.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Mall implements Serializable {


    @SerializedName("id")
    private
    Integer id;

    @SerializedName("name")
    private
    String name;

    @SerializedName("logo")
    private
    String logo;

    @SerializedName("address")
    private
    String address;

    @SerializedName("phone")
    private
    String phone;

    @SerializedName("website")
    private
    String website;

    @SerializedName("location_id")
    private
    String location_id;

    @SerializedName("shop")
    private
    List<Shop> shop;

    public List<Shop> getShop() {
        return shop;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public String getLocation_id() {
        return location_id;
    }
}
