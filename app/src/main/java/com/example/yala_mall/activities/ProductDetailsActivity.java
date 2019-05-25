package com.example.yala_mall.activities;

import android.app.Application;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.yala_mall.R;
import com.example.yala_mall.adapters.MySliderAdapter;
import com.example.yala_mall.adapters.SpinnerAdapter;
import com.example.yala_mall.adapters.SpinnerAdapter2;
import com.example.yala_mall.fragments.MessageDialog;
import com.example.yala_mall.models.Gallery;
import com.example.yala_mall.models.Offer;
import com.example.yala_mall.models.Product;
import com.example.yala_mall.models.ProductSize;
import com.example.yala_mall.models.Size;
import com.example.yala_mall.utils.Constants;
import com.example.yala_mall.viewModels.DataViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.crypto.spec.IvParameterSpec;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class ProductDetailsActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener {
    String productId;
    Product product;
    DataViewModel dataViewModel;
    TextView productName,productDescription,productPrice,orderCount  , priceAfterDiscount;
    Button addCartButton;
    SliderLayout mDemoSlider;
    ElegantNumberButton elegantNumberButton;
    Application master;
    RelativeLayout cartImage;
    View view;
    Typeface typeface;
    Spinner spinner;
    SpinnerAdapter2 spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        assignUIReference();
        assignAction();
        getProductDetails();
        getSizes();

        //typeface =  Typeface.createFromAsset(getAssets(),"font/app_font.ttf");
    }



    private void assignUIReference(){
        elegantNumberButton =  findViewById(R.id.quantity_button);
        productId = getIntent().getStringExtra("productId");
        product = (Product) getIntent().getSerializableExtra("product");
        dataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        productName = findViewById(R.id.product_name);
        productDescription = findViewById(R.id.product_description);
        productPrice = findViewById(R.id.product_price);
        orderCount = findViewById(R.id.cart_number);
        addCartButton = findViewById(R.id.add_cart_button);
        cartImage = findViewById(R.id.linearLayout_cart);
        priceAfterDiscount =findViewById(R.id.price);
        view = findViewById(R.id.line);
        spinner = findViewById(R.id.size_spinner);


        changeCartCount();
    }

    private void assignAction(){
        cartImage.setOnClickListener(this::setOnClickCartImage);

        addCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Product> list = ((MasterClass) master).getProductList();
                if (!list.isEmpty()) {
                    if (list.get(0).getMall_id() != product.getMall_id())
                        MessageDialog.getInstance(ProductDetailsActivity.this,getResources().getString(R.string.cart_permission)).show();
                    else
                        increaseCartCount(list);
                } else
                    increaseCartCount(list);
                }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                product.setSize(((ProductSize)spinner.getSelectedItem()).getSizes());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getProductDetails(){
      dataViewModel.getProductDetails(this, productId).observe(this, new Observer<List<Product>>() {
          @Override
          public void onChanged(@Nullable List<Product> products) {
              if (!products.get(0).getGallery().isEmpty()){
                 assignSlider(products.get(0).getGallery());
              }

              productName.setText(products.get(0).getName());
              productDescription.setText(products.get(0).getDescription());
              productPrice.setText(products.get(0).getPrice()+" ل.س");
              if (Double.parseDouble(products.get(0).getDiscount()) != 1) {
                  view.setVisibility(View.VISIBLE);
                  priceAfterDiscount.setVisibility(View.VISIBLE);
                  priceAfterDiscount.setText(Double.parseDouble(products.get(0).getDiscount())* Double.parseDouble(products.get(0).getPrice()) +" ل.س");
              }

          }
      });
    }
    private void getSizes() {
        dataViewModel.getSizeByProduct(this ,Integer.parseInt(productId)).observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {

                if (!products.get(0).getProductsize().isEmpty()) {
                    spinnerAdapter = new SpinnerAdapter2(products.get(0).getProductsize(), ProductDetailsActivity.this);
                    spinner.setAdapter(spinnerAdapter);
                    product.setSize(products.get(0).getProductsize().get(0).getSizes());
                }
                else
                {
                    spinner.setVisibility(View.INVISIBLE);

                    product.setSize(new Size(-1,""));
                }

            }
        });
    }
    private void assignSlider(List<Gallery> galleries){
        mDemoSlider = findViewById(R.id.slider_Products);

        HashMap<String,String> file_maps = new HashMap<>();
        for (Gallery gallery : galleries)
            file_maps.put("product"+gallery.getId(), Constants.IMG_URL +gallery.getImage());


        for(String name : file_maps.keySet()){
            MySliderAdapter textSliderView = new MySliderAdapter(this ,new Offer("", ""));
            // initialize a SliderLayout
            textSliderView
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.FitCenterCrop)
                    .setOnSliderClickListener(ProductDetailsActivity.this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        changeCartCount();
    }

    private void increaseCartCount(ArrayList<Product> list){
        int quantity;
        int index=0; // index for each item
        boolean check = false;  // check item if exist , we do not need to add it to list

        for (Product product1 :list ){
            if (product1.getId().equals(product.getId()) && product1.getSize().getId()==  product.getSize().getId()){
                quantity = Integer.parseInt(product1.getQuantity()) +  Integer.parseInt(elegantNumberButton.getNumber());
                product1.setQuantity(String.valueOf(quantity));
                list.set(index,product1);
                check = true;
            }
            index++;
        }

        if (!check){
            quantity = Integer.parseInt(elegantNumberButton.getNumber());
            product.setQuantity(String.valueOf(quantity));
            list.add(product.clone());
        }
        ((MasterClass) master).setProductList(list);

        orderCount.setText(String.valueOf(((MasterClass) master).getProductList().size()));
        Toast.makeText(this,R.string.productAdded,Toast.LENGTH_LONG).show();
    }

    private void changeCartCount(){
        master = (MasterClass) getApplication();
        if (!((MasterClass) master).getProductList().isEmpty())
            orderCount.setText(String.valueOf(((MasterClass) master).getProductList().size()));
    }
    private void setOnClickCartImage(View view){
        startActivity(new Intent(ProductDetailsActivity.this,CartActivity.class));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


}
