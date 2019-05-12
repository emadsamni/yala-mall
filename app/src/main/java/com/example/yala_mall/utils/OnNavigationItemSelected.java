package com.example.yala_mall.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;


import com.example.yala_mall.helps.CustomerUtils;
//import com.facebook.login.LoginManager;


public class OnNavigationItemSelected implements NavigationView.OnNavigationItemSelectedListener {

    private Activity context;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    CustomerUtils customerUtils;

     OnNavigationItemSelected(Activity context , DrawerLayout drawerLayout, NavigationView navigationView) {
        this.context = context;
        this.drawerLayout = drawerLayout;
        this.navigationView = navigationView;
        navigationView.setNavigationItemSelectedListener(this);
        customerUtils = CustomerUtils.getInstance(context);
    }

    public static OnNavigationItemSelected getInstance(Activity context, DrawerLayout drawerLayout, NavigationView navigationView){
        return new OnNavigationItemSelected(context,drawerLayout,navigationView);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        Fragment fragment;
        switch (id){


                }
        return false;


        }


}
