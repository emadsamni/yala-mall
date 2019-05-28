package com.example.yala_mall.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.example.yala_mall.R;
import com.example.yala_mall.models.Offer;

public class MySliderAdapter extends BaseSliderView {

    Offer offer;
    Context context;
    public MySliderAdapter(Context context , Offer offer) {
        super(context);
        this.offer =offer;
        this.context =context;

    }


    @Override
    public View getView() {

        View v = LayoutInflater.from(getContext()).inflate(R.layout.layout_slide_item,null);
        if (!offer.getTitle().equals(""))
        {
            LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.linear_offer);
            linearLayout.setVisibility(View.VISIBLE);
        }
        Typeface font = Typeface.createFromAsset(context.getAssets(),"fonts/Cairo-Bold.ttf");
        ImageView target = (ImageView)v.findViewById(R.id.daimajia_slider_image);
         TextView title = (TextView)v.findViewById(R.id.title);
         title.setTypeface(font);
        title.setText(offer.getTitle());
        TextView description = (TextView)v.findViewById(R.id.description);
        description.setText(offer.getDescription());
        bindEventAndShow(v, target);
        return v;
    }
}
