package com.example.yala_mall.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {

    @SerializedName("id")
    private
    Integer id;

    @SerializedName("product_id")
    private
    Integer product_id;

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    @SerializedName("name")
    private
    String name;

    @SerializedName("available")
    private
    int available;


    @SerializedName("description")
    private
    String description;

    @SerializedName("price")
    private
    String price;

    @SerializedName("discount")
    private
    String discount;

    @SerializedName("mall_id")
    private
    int mall_id;

    @SerializedName("shop_id")
    private
    int shop_id;

    @SerializedName("size_pcategory_id")
    private
    int size_pcategory_id;

    @SerializedName("gallery")
    private
    List<Gallery> gallery;

    @SerializedName("quantity")
    private String quantity;

    @SerializedName("notes")
    private String notes="";

    public Product(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAvailable() {
        return available;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getDiscount() {
        return discount;
    }

    public int getMall_id() {
        return mall_id;
    }

    public int getShop_id() {
        return shop_id;
    }

    public int getSize_pcategory_id() {
        return size_pcategory_id;
    }

    public List<Gallery> getGallery() {
        return gallery;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return notes;
    }

    public void setNote(String note) {
        this.notes = note;
    }
}
