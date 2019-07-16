package com.example.yala_mall.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Shop implements Serializable {


        @SerializedName("id")
        private
        int id;

        @SerializedName("name")
        private
        String name;

    @SerializedName("logo")
    private
    String logo;

    @SerializedName("flour")
    private
    int flour;

    @SerializedName("open_time")
    private
    String open_time;

    @SerializedName("close_time")
    private
    String close_time;

    @SerializedName("shop_status_id")
    private
    int  shop_status_id;

    @SerializedName("rate")
    private
    String  rate;

    @SerializedName("mall_id")
    private
    int  mall_id;

    public int getMall_id() {
        return mall_id;
    }

    public String getRate() {
        return rate;
    }

    @SerializedName("offers")
    private
    List<Offer> offers;

    @SerializedName("shop_status")
    private
    ShopStatus shop_status;


    private
    int rate_status;

    private
    int myRate;

    public int getMyRate() {
        return myRate;
    }

    public void setMyRate(int myRate) {
        this.myRate = myRate;
    }

    public int getRate_status() {
        return rate_status;
    }

    public void setRate_status(int rate_status) {
        this.rate_status = rate_status;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public int getId() {
        return id;
    }

    public int getFlour() {
        return flour;
    }

    public String getOpen_time() {
        return open_time;
    }

    public String getClose_time() {
        return close_time;
    }

    public int getShop_status_id() {
        return shop_status_id;
    }

    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }

    public ShopStatus getShop_status() {
        return shop_status;
    }
}
