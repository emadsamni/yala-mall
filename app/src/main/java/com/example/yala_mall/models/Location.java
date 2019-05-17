package com.example.yala_mall.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Location implements Serializable {

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Location(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getCity_id() {
        return city_id;
    }

    @SerializedName("id")
    private
    Integer id;

    @SerializedName("name")
    private
    String name;

    @SerializedName("city_id")
    private
    Integer city_id;

    @SerializedName("city")
    private
    City city;

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
