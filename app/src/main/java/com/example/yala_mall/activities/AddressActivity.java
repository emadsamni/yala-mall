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
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yala_mall.R;
import com.example.yala_mall.adapters.RecyclerAddressAdapter;
import com.example.yala_mall.adapters.RecyclerCategoryAdapter;
import com.example.yala_mall.adapters.RecyclerShopAdapter;
import com.example.yala_mall.models.Customer;
import com.example.yala_mall.viewModels.DataViewModel;
import com.example.yala_mall.viewModels.LoginViewModel;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class AddressActivity extends AppCompatActivity {

    LoginViewModel loginViewModel;
    RecyclerView recyclerView;
    RecyclerAddressAdapter adapter;
    TextView orderCount;
    Application master;
    RelativeLayout cartImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        assignUIReference();
        assignAction();
        changeCartCount();

    }

    private void assignUIReference() {
        cartImage = findViewById(R.id.linearLayout_cart);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        recyclerView =  findViewById(R.id.address_recycler_view);
    }

    private void assignAction() {
        cartImage.setOnClickListener(this::setOnClickCartImage);
        loginViewModel.getCustomer(this).observe(this, new Observer<Customer>() {
            @Override
            public void onChanged(@Nullable Customer customer) {
                adapter = new RecyclerAddressAdapter(customer.getAddresses(),AddressActivity.this );
                recyclerView.setAdapter(adapter);
                LinearLayoutManager layoutManager =new LinearLayoutManager( AddressActivity.this);
                layoutManager =new GridLayoutManager(AddressActivity.this,2);
                recyclerView.setLayoutManager(layoutManager);
            }

        });

    }

    private void setOnClickCartImage(View view){
        startActivity(new Intent(AddressActivity.this,CartActivity.class));
    }

    private void changeCartCount(){
        master = (MasterClass) getApplication();
        if (!((MasterClass) master).getProductList().isEmpty())
            orderCount.setText(String.valueOf(((MasterClass) master).getProductList().size()));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}
