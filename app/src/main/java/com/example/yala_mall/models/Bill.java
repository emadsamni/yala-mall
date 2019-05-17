package com.example.yala_mall.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Bill  implements Serializable {


    @SerializedName("id")
    private
    Integer id;

    @SerializedName("price")
    private
    String price;

    @SerializedName("shop_id")
    private
    Integer shop_id;

    @SerializedName("order_id")
    private
    Integer order_id;

    @SerializedName("bill_product")
    private
    List<BillProduct> bill_product;

    public Integer getId() {
        return id;
    }

    public String getPrice() {
        return price;
    }

    public Integer getShop_id() {
        return shop_id;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public List<BillProduct> getBill_product() {
        return bill_product;
    }
}
