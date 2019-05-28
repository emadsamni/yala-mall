package com.example.yala_mall.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProductP implements Serializable {

    public ProductP(int product_id, String quantity, String notes , int size_id) {
        this.product_id = product_id;
        this.quantity = quantity;
        if ( notes !=null)
           this.notes = notes;
        else
            this.notes ="";
        if (size_id == -1)
            this.size_id ="";
        else
           this.size_id =size_id+"";
    }

    @SerializedName("product_id")
    private
    int product_id;

    @SerializedName("size_id")
    private
    String size_id;


    @SerializedName("quantity")
    private
    String quantity;

    @SerializedName("notes")
    private
    String notes;

    public String getSize_id() {
        return size_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getNotes() {
        return notes;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
