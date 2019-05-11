package com.example.yala_mall.api;

import com.google.gson.annotations.SerializedName;

public class ApiResponse <T> {
    @SerializedName("message")
    private
    String message;

    @SerializedName("status")
    private
    String status;

    @SerializedName("data")
    private
    T data;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }
}
