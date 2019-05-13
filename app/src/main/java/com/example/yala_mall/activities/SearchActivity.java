package com.example.yala_mall.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import com.example.yala_mall.models.Product;
import com.example.yala_mall.viewModels.SearchViewModel;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

public class SearchActivity extends AppCompatActivity {
    SearchViewModel searchViewModel;
    String name;
    String mallId;
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
        Log.d("TSTS","mall : "+mallId);
        recyclerView = findViewById(R.id.recycler_view);
    }

    private void getProducts(){
        searchViewModel.getProducts(this,name,mallId).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                if (!products.isEmpty()){
                    recyclerAdapter = new RecyclerProductAdapter(products , SearchActivity.this );
                    recyclerView.setAdapter(recyclerAdapter);
                    LinearLayoutManager layoutManager =new GridLayoutManager(SearchActivity.this,2);
                    recyclerView.setLayoutManager(layoutManager);
                }
            }
        });
    }
}
