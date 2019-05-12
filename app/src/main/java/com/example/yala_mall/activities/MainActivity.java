package com.example.yala_mall.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.yala_mall.R;
import com.example.yala_mall.adapters.RecyclerCategoryAdapter;
import com.example.yala_mall.adapters.RecyclerMallAdapter;
import com.example.yala_mall.interfaces.OnItemRecyclerClicked;
import com.example.yala_mall.models.Category;
import com.example.yala_mall.models.Mall;
import com.example.yala_mall.models.Offer;
import com.example.yala_mall.viewModels.DataViewModel;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, OnItemRecyclerClicked {

    DataViewModel dataViewModel;
    SliderLayout mDemoSlider;
    RecyclerView recyclerView;
    RecyclerCategoryAdapter adapter;
    RecyclerView mallsRecyclerView;
    RecyclerMallAdapter mallsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignUIReference();
        getOffers();
        getCategories();
        getMalls();

    }

    private void assignUIReference(){
        dataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        recyclerView = findViewById(R.id.recycler_view);
        mallsRecyclerView = findViewById(R.id.mall_recycler_view);


    }

    private void getOffers(){
        dataViewModel.getOffers(this).observe(this, new Observer<List<Offer>>() {
            @Override
            public void onChanged(@Nullable List<Offer> offers) {
                if (!offers.isEmpty())
                    assignSlider(offers);
            }
        });
    }

    private void assignSlider(List<Offer> offers){
        mDemoSlider = findViewById(R.id.slider_offers);

        HashMap<String,String> file_maps = new HashMap<>();
         for (Offer offer : offers)
            file_maps.put("offer"+offer.getId(),"https://mall.yala-shop.com/images/"+offer.getImage());


        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(MainActivity.this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Fade);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    public void getCategories()
    {
       dataViewModel.getCategoryList(this).observe(this, new Observer<List<Category>>() {
           @Override
           public void onChanged(@Nullable List<Category> categories) {
               adapter = new RecyclerCategoryAdapter(categories ,MainActivity.this ,MainActivity.this );
               recyclerView.setAdapter(adapter);
               LinearLayoutManager layoutManager =new LinearLayoutManager( MainActivity.this);
               layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
               recyclerView.setLayoutManager(layoutManager);

           }
       });
    }


    public void getMalls()
    {
        dataViewModel.getMalls(this).observe(this, new Observer<List<Mall>>() {
            @Override
            public void onChanged(@Nullable List<Mall> malls) {
                mallsAdapter = new RecyclerMallAdapter(malls ,MainActivity.this ,MainActivity.this);
                mallsRecyclerView.setAdapter(mallsAdapter);
                LinearLayoutManager layoutManager =new LinearLayoutManager( MainActivity.this);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mallsRecyclerView.setLayoutManager(layoutManager);
            }
        });

    }

    @Override
    public void onClickedRecyclerItem(Category category) {
        Intent intent = new Intent(MainActivity.this, ProductsActivity.class);
        intent.putExtra("category" ,category.getId());
       startActivity(intent);
    }

    @Override
    public void onClickedRecyclerMallItem(Mall current) {
        Intent intent = new Intent(MainActivity.this, MallActivity.class);
        intent.putExtra("mall_id" ,current.getId());
        startActivity(intent);
    }
}
