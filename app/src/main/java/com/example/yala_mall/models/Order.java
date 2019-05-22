package com.example.yala_mall.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {


    @SerializedName("id")
    private
    Integer id;

    @SerializedName("price")
    private
    String price;

    @SerializedName("delivery_cost")
    private
    String delivery_cost;

    @SerializedName("delivery_time")
    private
    String delivery_time;

    @SerializedName("order_time")
    private
    String order_time;

    @SerializedName("order_status_id")
    private
    Integer order_status_id;

    @SerializedName("customer_id")
    private
    Integer customer_id;

    @SerializedName("customer_location_id")
    private
    Integer customer_location_id;


    @SerializedName("driver_id")
    private
    Integer driver_id;

    @SerializedName("bills")
    private
    List<Bill> bills;

    public String getOrder_time() {
        return order_time;
    }

    public Integer getId() {
        return id;
    }

    public String getPrice() {
        return price;
    }

    public String getDelivery_cost() {
        return delivery_cost;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public Integer getOrder_status_id() {
        return order_status_id;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public Integer getCustomer_location_id() {
        return customer_location_id;
    }

    public Integer getDriver_id() {
        return driver_id;
    }

    public List<Bill> getBills() {
        return bills;
    }
}
