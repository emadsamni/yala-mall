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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.yala_mall.R;
import com.example.yala_mall.adapters.RecyclerProductAdapter;
import com.example.yala_mall.interfaces.OnItemProductClicked;
import com.example.yala_mall.models.Product;
import com.example.yala_mall.viewModels.SearchViewModel;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements OnItemProductClicked {
    SearchViewModel searchViewModel;
    String name;
    String mallId;
    String shopId;
    RecyclerView recyclerView;
    RecyclerProductAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        assignUIReference();
        getProducts();
    }

    private void assignUIReference(){
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        name = getIntent().getStringExtra("name");
        mallId = getIntent().getStringExtra("mallId");
        shopId = getIntent().getStringExtra("shopId");
        recyclerView = findViewById(R.id.recycler_view);
    }

    private void getProducts(){
        searchViewModel.getProducts(this,name,mallId,shopId).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                if (!products.isEmpty()){
                    recyclerAdapter = new RecyclerProductAdapter(products , SearchActivity.this,SearchActivity.this );
                    recyclerView.setAdapter(recyclerAdapter);
                    LinearLayoutManager layoutManager =new GridLayoutManager(SearchActivity.this,2);
                    recyclerView.setLayoutManager(layoutManager);
                }
            }
        });
    }

    @Override
    public void onProductClick(Product product) {
        startActivity(new Intent(this,ProductDetailsActivity.class).putExtra("productId",String.valueOf(product.getId())));
    }
}
