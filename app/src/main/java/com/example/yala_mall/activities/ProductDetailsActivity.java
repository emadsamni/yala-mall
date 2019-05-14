package com.example.yala_mall.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.yala_mall.R;
import com.example.yala_mall.models.Gallery;
import com.example.yala_mall.models.Offer;
import com.example.yala_mall.models.Product;
import com.example.yala_mall.utils.Constants;
import com.example.yala_mall.viewModels.DataViewModel;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener {
    String productId;
    DataViewModel dataViewModel;
    TextView productName,productDescription,productPrice,orderCount;
    Button addCartButton;
    SliderLayout mDemoSlider;
    ElegantNumberButton elegantNumberButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        assignUIReference();
        assignAction();
        getProductDetails();
    }

    private void assignUIReference(){
        elegantNumberButton =  findViewById(R.id.quantity_button);
        productId = getIntent().getStringExtra("productId");
        dataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        productName = findViewById(R.id.product_name);
        productDescription = findViewById(R.id.product_description);
        productPrice = findViewById(R.id.product_price);
        orderCount = findViewById(R.id.cart_number);
        addCartButton = findViewById(R.id.add_cart_button);
    }

    private void assignAction(){
        addCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderCount.setText(elegantNumberButton.getNumber());
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
              productPrice.setText(products.get(0).getPrice());
          }
      });
    }

    private void assignSlider(List<Gallery> galleries){
        mDemoSlider = findViewById(R.id.slider_Products);

        HashMap<String,String> file_maps = new HashMap<>();
        for (Gallery gallery : galleries)
            file_maps.put("product"+gallery.getId(), Constants.IMG_URL +gallery.getImage());


        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
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
}
