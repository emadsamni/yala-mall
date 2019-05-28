package com.example.yala_mall.activities;

import android.app.Application;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
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
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yala_mall.R;
import com.example.yala_mall.adapters.RecyclerProductAdapter;
import com.example.yala_mall.interfaces.OnItemProductClicked;
import com.example.yala_mall.models.Product;
import com.example.yala_mall.viewModels.SearchViewModel;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class SearchActivity extends AppCompatActivity implements OnItemProductClicked {
    SearchViewModel searchViewModel;
    String name;
    String mallId;
    String shopId;
    RecyclerView recyclerView;
    RecyclerProductAdapter recyclerAdapter;
    TextView orderCount;
    Application master;
    RelativeLayout cartImage;
    TextView pageTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        assignUIReference();
        assignAction();
        getProducts();
    }

    private void assignUIReference(){
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        name = getIntent().getStringExtra("name");
        mallId = getIntent().getStringExtra("mallId");
        shopId = getIntent().getStringExtra("shopId");
        recyclerView = findViewById(R.id.recycler_view);
        orderCount = findViewById(R.id.cart_number);
        cartImage = findViewById(R.id.linearLayout_cart);
        changeCartCount();
        pageTitle = findViewById(R.id.page_title);
        pageTitle.setText(name);
    }

    private void assignAction(){
        cartImage.setOnClickListener(this::setOnClickCartImage);
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
        startActivity(new Intent(this,ProductDetailsActivity.class).putExtra("productId",String.valueOf(product.getId())).putExtra("product",product));
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeCartCount();
    }

    private void changeCartCount(){
        master = (MasterClass) getApplication();
        if (!((MasterClass) master).getProductList().isEmpty())
            orderCount.setText(String.valueOf(((MasterClass) master).getProductList().size()));
        else
        {
            orderCount.setText("0");
        }
    }

    private void setOnClickCartImage(View view){
        startActivity(new Intent(SearchActivity.this,CartActivity.class));
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}
