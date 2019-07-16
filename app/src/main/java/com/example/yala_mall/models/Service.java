package com.example.yala_mall.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Service implements Serializable {


    @SerializedName("id")
    private
    int id;

    @SerializedName("name")
    private
    String name;

    @SerializedName("active")
    private
    int active;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getActive() {
        return active;
    }
}
