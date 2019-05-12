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
    List<Category> p_category;



    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Category> getP_category() {
        return p_category;
    }
}
