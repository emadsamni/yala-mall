package com.example.yala_mall.adapters;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.yala_mall.fragments.CategoryFragment;
import com.example.yala_mall.fragments.HomeFragment;
import com.example.yala_mall.fragments.MallsFragment;
import com.example.yala_mall.models.Category;

import java.util.List;


public class FragmentAdapter extends FragmentPagerAdapter {

    List<Category> categoryList;


    public FragmentAdapter(FragmentManager fm , List<Category> categoryList ) {
        super(fm);
        this.categoryList =categoryList;
        this.categoryList.add(0,new Category(-1 ,"الرئيسية"));
        this.categoryList.add(1,new Category(-1 ,"المولات"));

    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;

        switch (i) {
            case 0:
                fragment = HomeFragment.getInstance();
                break;

            case 1:
                fragment = MallsFragment.getInstance();
                break;

            default:
                fragment = CategoryFragment.getInstance(categoryList.get(i).getId());
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categoryList.get(position).getName();

    }
}
