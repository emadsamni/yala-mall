package com.example.yala_mall.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.yala_mall.R;
import com.example.yala_mall.adapters.FragmentAdapter;
import com.example.yala_mall.models.Category;
import com.example.yala_mall.viewModels.DataViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DataViewModel dataViewModel;
    TabLayout tabLayout;
    ViewPager viewPager;
    FragmentAdapter fragmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignUIReference();
        getCategories();

    }
    private void assignUIReference(){
        dataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        tabLayout = findViewById(R.id.mainTabLayOut);
        viewPager = findViewById(R.id.mainViewpager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(fragmentAdapter);
    }

    public void getCategories()
    {
       dataViewModel.getCategoryList(this).observe(this, new Observer<List<Category>>() {
           @Override
           public void onChanged(@Nullable List<Category> categories) {
               fragmentAdapter = new FragmentAdapter(getSupportFragmentManager() ,categories);
               viewPager.setAdapter(fragmentAdapter);

           }
       });
    }
}
