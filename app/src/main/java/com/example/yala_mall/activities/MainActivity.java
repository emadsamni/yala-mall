package com.example.yala_mall.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.yala_mall.R;
import com.example.yala_mall.adapters.FragmentAdapter;
import com.example.yala_mall.models.Category;
import com.example.yala_mall.models.Offer;
import com.example.yala_mall.models.PcCategory;
import com.example.yala_mall.viewModels.DataViewModel;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener {

    DataViewModel dataViewModel;
    TabLayout tabLayout;
    ViewPager viewPager;
    FragmentAdapter fragmentAdapter;
    List<PcCategory> categoryList;
    SliderLayout mDemoSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignUIReference();
        getOffers();
        //getCategories();

    }

    private void assignUIReference(){
        dataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);
//        tabLayout = findViewById(R.id.mainTabLayOut);
//        viewPager = findViewById(R.id.mainViewpager);
//        tabLayout.setupWithViewPager(viewPager);
//        viewPager.setAdapter(fragmentAdapter);
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

//    public void getCategories()
//    {
//       dataViewModel.getCategoryList(this).observe(this, new Observer<List<Category>>() {
//           @Override
//           public void onChanged(@Nullable List<Category> categories) {
//               fragmentAdapter = new FragmentAdapter(getSupportFragmentManager() ,categories);
//               viewPager.setAdapter(fragmentAdapter);
//           }
//       });
//    }
}
