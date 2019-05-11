package com.example.yala_mall.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PcCategory implements Serializable {

    @SerializedName("id")
    private
    Integer id;

    @SerializedName("name")
    private
    String name;

    @SerializedName("scatogory_id")
    private
    int scatogory_id;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScatogory_id() {
        return scatogory_id;
    }
}
