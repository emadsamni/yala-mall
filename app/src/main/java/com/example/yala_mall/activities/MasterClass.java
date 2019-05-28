package com.example.yala_mall.activities;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.yala_mall.R;
import com.example.yala_mall.models.Product;

import java.util.ArrayList;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class MasterClass extends Application {



    @Override
    public void onCreate() {
        super.onCreate();
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/Cairo-Regular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    public  ArrayList<Product> productList = new ArrayList<>();

    public void setProductList(ArrayList<Product> list){
        productList = list;
    }

    public ArrayList<Product> getProductList(){
       return productList;
    }

}
