package com.example.yala_mall.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.security.PublicKey;
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

    @SerializedName("productsize")
    private
    List<ProductSize> productsize;

    public List<ProductSize> getProductsize() {
        return productsize;
    }

    @SerializedName("gallery")
    private
    List<Gallery> gallery;

    @SerializedName("quantity")
    private String quantity;

    @SerializedName("size")
    private Size size;


    public  Product clone()
    {
        Product temp =   new Product(this.name ,this.price);
        temp.setQuantity(this.getQuantity());
        temp.id = this.id;
        temp.product_id = this.product_id;
        temp.mall_id = this.mall_id;
        temp.quantity = this.quantity;
        temp.gallery = this.gallery;
        temp.discount = this.discount;
        temp.size = this.size;

      return  temp;

    }
    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @SerializedName("notes")
    private String notes="";

    public Product(String name, String price ) {
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
