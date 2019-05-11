package com.example.yala_mall.utils;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.design.widget.NavigationView;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;


public class ChangeNavigationFont {
    private Activity context;
    private NavigationView navigationView;

     ChangeNavigationFont(Activity context, NavigationView navigationView) {
        this.context = context;
        this.navigationView = navigationView;
    }

    public static ChangeNavigationFont getInstance(Activity context, NavigationView navigationView){
        return new ChangeNavigationFont(context,navigationView);
    }

    public void changeFontStyleNavigation(){
        Menu m = navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //for applying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), Constants.FONT_STYLE);
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

}
