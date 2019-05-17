package com.example.yala_mall.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BillProduct implements Serializable {


    @SerializedName("id")
    private
    Integer id;

    @SerializedName("quantity")
    private
    Integer quantity;

    @SerializedName("notes")
    private
    String notes;

    @SerializedName("sale")
    private
    String sale;

    @SerializedName("bill_id")
    private
    Integer bill_id;

    @SerializedName("product_id")
    private
    Integer product_id;

    @SerializedName("product")
    private
    Product product;

    public Integer getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getNotes() {
        return notes;
    }

    public String getSale() {
        return sale;
    }

    public Integer getBill_id() {
        return bill_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public Product getProduct() {
        return product;
    }
}
