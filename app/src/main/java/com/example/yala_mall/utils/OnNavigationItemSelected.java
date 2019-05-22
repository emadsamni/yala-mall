package com.example.yala_mall.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;


import com.example.yala_mall.R;
import com.example.yala_mall.activities.AddressActivity;
import com.example.yala_mall.activities.MainActivity;
import com.example.yala_mall.activities.MyOrderActivity;
import com.example.yala_mall.activities.RegisterActivity;
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
            case R.id.login_id:
                if (context instanceof MainActivity)
                    context.startActivity(new Intent(context, RegisterActivity.class));
                else{
                    context.startActivity(new Intent(context, RegisterActivity.class));
                    context.finish();
                }
                drawerLayout.closeDrawers();
                return true;
            case R.id.my_orders:
                if (context instanceof MainActivity)
                    context.startActivity(new Intent(context, MyOrderActivity.class));
                else{
                    context.startActivity(new Intent(context, MyOrderActivity.class));
                    context.finish();
                }
                drawerLayout.closeDrawers();
                return true;
            case R.id.my_address:
                if (context instanceof MainActivity)
                    context.startActivity(new Intent(context, AddressActivity.class));
                else{
                    context.startActivity(new Intent(context, AddressActivity.class));
                    context.finish();
                }
                drawerLayout.closeDrawers();
                return true;
            case R.id.logout_id:
                customerUtils = CustomerUtils.getInstance(context);
                customerUtils.clear();
                drawerLayout.closeDrawers();
                ((MainActivity)context).navigation_config();

                return true;
                }
        return false;


        }


}
