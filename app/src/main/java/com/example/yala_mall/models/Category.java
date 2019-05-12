package com.example.yala_mall.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {

    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @SerializedName("id")
    private
    Integer id;

    @SerializedName("name")
    private
    String name;

    @SerializedName("p_category")
    private
    List<PcCategory> p_category;


    @SerializedName("image")
    private
    String image;

    public String getImage() {
        return image;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<PcCategory> getP_category() {
        return p_category;
    }
}
