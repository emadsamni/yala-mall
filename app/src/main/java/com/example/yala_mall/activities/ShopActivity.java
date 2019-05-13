package com.example.yala_mall.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.yala_mall.R;
import com.example.yala_mall.adapters.RecyclerProductAdapter;
import com.example.yala_mall.adapters.RecyclerShopAdapter;
import com.example.yala_mall.models.Product;
import com.example.yala_mall.viewModels.DataViewModel;

import java.util.List;

public class ShopActivity extends AppCompatActivity {

    DataViewModel dataViewModel;
    RecyclerView productsRecyclerView;
    RecyclerProductAdapter productsAdapter;
    int shopId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        assignUIReference();
        assignAction();
        Intent intent = getIntent();
        shopId = intent.getExtras().getInt("shop_id");
        getProduct(shopId);
    }


    private void assignUIReference() {
        dataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        productsRecyclerView= findViewById(R.id.product_recycler_view);

    }



    private void assignAction() {
    }

    private void getProduct(int shopId) {
        dataViewModel.getProductsByShop(this ,shopId).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                productsAdapter = new RecyclerProductAdapter(products ,ShopActivity.this );
                productsRecyclerView.setAdapter(productsAdapter);
                LinearLayoutManager layoutManager =new LinearLayoutManager( ShopActivity.this);
                layoutManager =new GridLayoutManager(ShopActivity.this,2);
                productsRecyclerView.setLayoutManager(layoutManager);
            }
        });
    }
}
