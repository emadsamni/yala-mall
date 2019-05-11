package com.example.yala_mall.models;

import com.google.gson.annotations.SerializedName;

public class Customer {


    @SerializedName("id")
    private
    Integer id;

    @SerializedName("phone")
    private
    String phone;

    @SerializedName("token")
    private
    String token;

    @SerializedName("verification_code")
    private
    String verification_code;

    @SerializedName("verification_request_time")
    private
    String verification_request_time;

    @SerializedName("blocked")
    private
    int blocked;

    public Integer getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getToken() {
        return token;
    }

    public String getVerification_code() {
        return verification_code;
    }

    public String getVerification_request_time() {
        return verification_request_time;
    }

    public int getBlocked() {
        return blocked;
    }
}
