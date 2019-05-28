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
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yala_mall.R;
import com.example.yala_mall.adapters.RecyclerProductAdapter;
import com.example.yala_mall.fragments.FilterDialog;
import com.example.yala_mall.fragments.FilterDialog2;
import com.example.yala_mall.interfaces.OnClickFilterButton;
import com.example.yala_mall.interfaces.OnItemProductClicked;
import com.example.yala_mall.interfaces.OnItemRecyclerClicked;
import com.example.yala_mall.models.Category;
import com.example.yala_mall.models.Mall;
import com.example.yala_mall.models.Product;
import com.example.yala_mall.models.Shop;
import com.example.yala_mall.viewModels.DataViewModel;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class ProductsActivity extends AppCompatActivity implements OnItemRecyclerClicked, OnItemProductClicked, OnClickFilterButton {


    DataViewModel dataViewModel;
    XRecyclerView recyclerView;
    List<Product> productList;
    RecyclerProductAdapter adapter;
    TextView orderCount;
    Application master;
    RelativeLayout cartImage;
    Button filterButton, filterCancelButton;
    int categoryId;
    Category category;
    TextView pageTitle, catName;
    int productsCycle;
    boolean filter_status =false;
    HashMap<String, Integer> gSpinnerMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        assignUIReference();
        assignAction();
        Intent intent = getIntent();
        category = (Category) intent.getSerializableExtra("category");
        categoryId = category.getId();
        pageTitle.setText("Yala Mall");
        catName.setText(category.getName());
        getProductsByCategory(categoryId ,true);
    }

    private void assignUIReference() {
        dataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        recyclerView = findViewById(R.id.product_recycler_view);
        orderCount = findViewById(R.id.cart_number);
        cartImage = findViewById(R.id.linearLayout_cart);
        filterButton = findViewById(R.id.filter_button);
        filterCancelButton = findViewById(R.id.filter_cancel_button);
        changeCartCount();
        pageTitle = findViewById(R.id.page_title);
        catName = findViewById(R.id.cat_name);
        productList = new ArrayList<>();
        adapter = new RecyclerProductAdapter(productList, ProductsActivity.this, ProductsActivity.this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ProductsActivity.this);
        layoutManager = new GridLayoutManager(ProductsActivity.this, 2);
        recyclerView.setLayoutManager(layoutManager);
        productsCycle =0;
    }

    private void assignAction() {
        cartImage.setOnClickListener(this::setOnClickCartImage);
        filterButton.setOnClickListener(this::onClickFilterButton);
        filterCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter_status= false;
                productsCycle=0;
                getProductsByCategory(categoryId ,true);
                filterCancelButton.setVisibility(View.GONE);
            }
        });
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                productsCycle =0;
                productList =new ArrayList<>();
               if (filter_status)
                   getProductByFilter(true);
               else
                 getProductsByCategory(categoryId ,true);


            }

            @Override
            public void onLoadMore() {
                if (filter_status)
                    getProductByFilter(true);
                else

                  getProductsByCategory(categoryId ,false);
            }
        });
    }

    private void onClickFilterButton(View view) {
        FilterDialog2.getInstance(this, this, categoryId).show();
    }


    private void setOnClickCartImage(View view) {
        startActivity(new Intent(ProductsActivity.this, CartActivity.class));
    }


    public void getProductsByCategory(int categoryId , Boolean state) {
        if (state) {
            dataViewModel.getProductListByCategory(this, categoryId, 0).observe(this, new Observer<List<Product>>() {
                @Override
                public void onChanged(@Nullable List<Product> products) {
                    productList =products;
                    adapter = new RecyclerProductAdapter(productList, ProductsActivity.this, ProductsActivity.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.refreshComplete();
                }
            });
        }
        else
        {
            productsCycle = productsCycle+10;
            dataViewModel.getProductListByCategory(this, categoryId, productsCycle).observe(this, new Observer<List<Product>>() {

                @Override
                public void onChanged(@Nullable List<Product> products) {
                    if (!products.isEmpty()) {
                        productList.addAll(products);
                        recyclerView.notifyItemInserted(productList,productList.size()-1);

                    }
                    recyclerView.loadMoreComplete();
                }
            });
        }

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

    @Override
    public void onProductClick(Product product) {
        startActivity(new Intent(this, ProductDetailsActivity.class).putExtra("productId", String.valueOf(product.getId())).putExtra("product", product));
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeCartCount();
    }

    private void changeCartCount() {
        master = (MasterClass) getApplication();
        if (!((MasterClass) master).getProductList().isEmpty())
            orderCount.setText(String.valueOf(((MasterClass) master).getProductList().size()));
        else
        {
            orderCount.setText("0");
        }
    }

    @Override
    public void onFilterButtonClicked(HashMap<String, Integer> spinnerMap) {

        if (spinnerMap.size() != 0) {
            gSpinnerMap = spinnerMap;
           // gSpinnerMap.put("mall_id", mall.getId());
            //productList.clear();
            productsCycle = 0;
            filter_status = true;
            filter_status = true;
            getProductByFilter(true);

        }

    }


    public  void  getProductByFilter(boolean stat)
    {
        if (stat) {
            dataViewModel.getFilter(this, gSpinnerMap ,0).observe(this, new Observer<List<Product>>() {
                @Override
                public void onChanged(@Nullable List<Product> products) {
                    adapter = new RecyclerProductAdapter(products, ProductsActivity.this, ProductsActivity.this);
                    recyclerView.setAdapter(adapter);
                    recyclerView.refreshComplete();
                    filterCancelButton.setVisibility(View.VISIBLE);
                }
            });
        }
        else
        {
            dataViewModel.getFilter(this, gSpinnerMap ,0).observe(this, new Observer<List<Product>>() {
                @Override
                public void onChanged(@Nullable List<Product> products) {
                    if (!products.isEmpty()) {
                        productList.addAll(products);
                        recyclerView.notifyItemInserted(productList,productList.size()-1);
                    }
                    filterCancelButton.setVisibility(View.VISIBLE);
                }
            });
        }


    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}
