package com.example.yala_mall.activities;

import android.app.Application;

import com.example.yala_mall.models.Product;

import java.util.ArrayList;

public class MasterClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public  ArrayList<Product> productList = new ArrayList<>();

    public void setProductList(ArrayList<Product> list){
        productList = list;
    }

    public ArrayList<Product> getProductList(){
       return productList;
    }

}
