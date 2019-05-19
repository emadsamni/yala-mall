package com.example.yala_mall.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yala_mall.R;
import com.example.yala_mall.adapters.RecyclerCartProductsAdapter;
import com.example.yala_mall.helps.CustomerUtils;
import com.example.yala_mall.interfaces.OnClickElegantButton;
import com.example.yala_mall.models.Product;
import com.example.yala_mall.utils.Constants;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements OnClickElegantButton {
    RecyclerView recyclerView;
    RecyclerCartProductsAdapter recyclerAdapter;
    Button checkoutButton;
    TextView totalPrice;
    CustomerUtils customerUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        assignUIReference();
        assignAction();
    }

    private void assignUIReference(){
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new RecyclerCartProductsAdapter(((MasterClass)getApplication()).getProductList(),this,this);
        recyclerView.setAdapter(recyclerAdapter);
        checkoutButton = findViewById(R.id.checkout_btn);
        totalPrice = findViewById(R.id.total_price);
        customerUtils = CustomerUtils.getInstance(this);



        totalPrice.setText("المجموع : "+String.valueOf(getSum()));
    }

    private void assignAction(){
        checkoutButton.setOnClickListener(this::setOnClickCheckoutButton);
    }

    private void setOnClickCheckoutButton(View view){
        if (getSum() == 0.0)
        {
            Toast.makeText(this, "السلة فارغة", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (customerUtils.isFound(Constants.PREF_TOKEN))
            {

                startActivity(new Intent(CartActivity.this ,PaymentActivity.class));
                finish();
            }
            else
            {
                Toast.makeText(this, "يتوجب تسجيل الدخول قبل البدء بعملية الدفع", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CartActivity.this ,RegisterActivity.class));
            }
        }
    }

                // edit count of quantity in cart activity
    @Override
    public void clickElegantButton(Product product , String quantity) {
        int index=0;
        ArrayList<Product> list = ((MasterClass)getApplication()).getProductList();
        for (Product product1 :list ){
            if (product1.getId().equals(product.getId())){
                product1.setQuantity(quantity);
                ((MasterClass)getApplication()).getProductList().set(index,product1);
            }
            index++;
        }



        totalPrice.setText("المجموع : "+String.valueOf(getSum()));

    }


    private double getSum()
    {
        Double amount=0.0;
        ArrayList<Product> products= ((MasterClass)getApplication()).getProductList();
        for (Product product : products)
            amount = amount + (Integer.parseInt(product.getPrice())*(Integer.parseInt(product.getQuantity())));
        return amount;
    }
}
