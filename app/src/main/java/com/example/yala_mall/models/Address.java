package com.example.yala_mall.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Address implements Serializable {


    @SerializedName("id")
    private
    Integer id;

    @SerializedName("address")
    private
    String address;

    @SerializedName("customer_id")
    private
    Integer customer_id;

    @SerializedName("location_id")
    private
    Integer location_id;

    @SerializedName("location")
    private
    Location location;

    public Integer getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public Integer getLocation_id() {
        return location_id;
    }

    public Location getLocation() {
        return location;
    }
}
