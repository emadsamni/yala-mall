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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.yala_mall.R;
import com.example.yala_mall.adapters.RecyclerCategoryAdapter;
import com.example.yala_mall.adapters.RecyclerMallAdapter;
import com.example.yala_mall.adapters.RecyclerProductAdapter;
import com.example.yala_mall.adapters.RecyclerShopAdapter;
import com.example.yala_mall.models.Mall;
import com.example.yala_mall.models.Product;
import com.example.yala_mall.viewModels.DataViewModel;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

public class MallActivity extends AppCompatActivity {

    DataViewModel dataViewModel;
    RecyclerView recyclerView;
    RecyclerShopAdapter adapter;
    RecyclerView productsRecyclerView;
    RecyclerProductAdapter productsAdapter;
    LinearLayout searchLayout;
    MaterialSearchView searchView;
    int mallId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall);

        assignUIReference();
        assignAction();
        Intent intent = getIntent();
        mallId = intent.getExtras().getInt("mall_id");
        getShops(mallId);
        getProduct(mallId);
    }

    private void assignUIReference() {
        dataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        recyclerView = findViewById(R.id.mall_recycler_view);
        productsRecyclerView= findViewById(R.id.product_recycler_view);
        searchView = findViewById(R.id.search_view);
        searchLayout = findViewById(R.id.linearLayout_search);
    }

    private void assignAction() {
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             searchView.showSearch(true);
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startActivity(new Intent(MallActivity.this,SearchActivity.class).putExtra("mallId",String.valueOf(mallId)).putExtra("name",query));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void getShops(int mallId) {

        dataViewModel.getShopsByMall(this ,mallId).observe(this, new Observer<List<Mall>>() {
            @Override
            public void onChanged(@Nullable List<Mall> malls) {
                adapter = new RecyclerShopAdapter(malls.get(0).getShop() ,MallActivity.this );
                recyclerView.setAdapter(adapter);
                LinearLayoutManager layoutManager =new LinearLayoutManager( MallActivity.this);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(layoutManager);
            }
        });
    }

    private void getProduct(int mallId) {
        dataViewModel.getProductsByMall(this ,mallId).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                productsAdapter = new RecyclerProductAdapter(products ,MallActivity.this );
                productsRecyclerView.setAdapter(productsAdapter);
                LinearLayoutManager layoutManager =new LinearLayoutManager( MallActivity.this);
                layoutManager =new GridLayoutManager(MallActivity.this,2);
                productsRecyclerView.setLayoutManager(layoutManager);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }
}
