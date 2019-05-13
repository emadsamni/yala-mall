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
import com.example.yala_mall.interfaces.OnItemRecyclerClicked;
import com.example.yala_mall.models.Category;
import com.example.yala_mall.models.Mall;
import com.example.yala_mall.models.Product;
import com.example.yala_mall.models.Shop;
import com.example.yala_mall.viewModels.DataViewModel;

import java.util.List;

public class ProductsActivity extends AppCompatActivity implements OnItemRecyclerClicked {


    DataViewModel dataViewModel;
    RecyclerView recyclerView;
    RecyclerProductAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        assignUIReference();
        Intent intent = getIntent();
        int categoryId = intent.getExtras().getInt("category");
        getProductsByCategory(categoryId);


    }

    private void assignUIReference() {
        dataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        recyclerView = findViewById(R.id.product_recycler_view);
    }


    public void getProductsByCategory(int categoryId)
    {
        dataViewModel.getProductListByCategory(this  ,categoryId).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                adapter = new RecyclerProductAdapter(products , ProductsActivity.this );
                recyclerView.setAdapter(adapter);
                LinearLayoutManager layoutManager =new LinearLayoutManager( ProductsActivity.this);
                layoutManager =new GridLayoutManager(ProductsActivity.this,2);
                recyclerView.setLayoutManager(layoutManager);
            }
        });

    }

    @Override
    public void onClickedRecyclerItem(Category category) {

    }

    @Override
    public void onClickedRecyclerMallItem(Mall current) {

    }

    @Override
    public void onClickedRecyclerShopItem(Shop current) {

    }
}
