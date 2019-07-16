package com.example.yala_mall.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.example.yala_mall.R;
import com.example.yala_mall.activities.MainActivity;
import com.example.yala_mall.interfaces.OnItemRecyclerClicked;
import com.example.yala_mall.models.Category;
import com.example.yala_mall.models.Mall;
import com.example.yala_mall.models.Offer;
import com.example.yala_mall.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RecyclerMallAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Mall> list;
    private List<Offer> offers;
    private List<Category> categories;
    private Context context;
    private OnItemRecyclerClicked onItemRecyclerClicked;
    private  RelativeLayout relativeLayout;
    private static int type = 0;
    private static final int TYPE_MALL = 0, TYPE_SLIDER = 1;



    public RecyclerMallAdapter(List<Mall> list,List<Offer> offers, Context context , OnItemRecyclerClicked onItemRecyclerClicked  , RelativeLayout relativeLayout) {
        this.list = list;
        this.context = context;
        this.onItemRecyclerClicked = onItemRecyclerClicked;
        this.relativeLayout = relativeLayout;
        this.offers =offers;

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_SLIDER;
        else
            return TYPE_MALL;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if ( i  ==TYPE_SLIDER)
        {
             view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_slider_item,null,false);
            return new ViewHolderSlider(view);
        }
        else
        {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recycler_mall,null,false);
                return new ViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if (i != 0 ) {
            ViewHolder vv = (ViewHolder) viewHolder;
            Mall current = list.get(i-1);
            Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Cairo-Bold.ttf");
            vv.textView.setText(current.getName());
            vv.textView.setTypeface(font);
            vv.addressTextView.setText(current.getAddress());
            if (!current.getLogo().equals(""))
                Picasso.with(context).load(Constants.IMG_URL + current.getLogo()).into(vv.imageView);
            else vv.imageView.setImageResource(R.drawable.defult_image);
            // Picasso.with(context).load(Constants.IMG_URL+current.getLogo()).into(viewHolder.imageView);

            vv.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemRecyclerClicked.onClickedRecyclerMallItem(current);
                }
            });

            if (i % 2 == 0) {
                vv.linearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                vv.coloredLinearLayout.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
                vv.line.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryTrans));
                vv.textView.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            } else {
                vv.linearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                vv.coloredLinearLayout.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                vv.line.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDarkTrans));
                vv.textView.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    100
            );
            Random random = new Random();
            int sss = random.nextInt(5) * 20 + 10;
            params.setMargins(0, sss, 0, 0);
            vv.line.setLayoutParams(params);
            final ViewTreeObserver observer = relativeLayout.getViewTreeObserver();
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {

                    vv.coloredLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(relativeLayout.getWidth() / 2, LinearLayout.LayoutParams.MATCH_PARENT));


                }
            });
        }
        else
        {

                ViewHolderSlider vv = (ViewHolderSlider) viewHolder;
                assignSlider(offers, vv.mDemoSlider ,vv.pagerIndicator);

        }

    }


    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView ,  addressTextView;
        ImageView imageView;
        LinearLayout linearLayout;
        LinearLayout coloredLinearLayout , line;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.mall_name);
            addressTextView = itemView.findViewById(R.id.mall_address);
            imageView = itemView.findViewById(R.id.mall_image);
            linearLayout = itemView.findViewById(R.id.mall_layout);
            coloredLinearLayout = itemView.findViewById(R.id.colored_layout);
            line =  itemView.findViewById(R.id.line);




        }

    }

    class ViewHolderSlider extends RecyclerView.ViewHolder {
        SliderLayout mDemoSlider;
        PagerIndicator pagerIndicator;


        ViewHolderSlider(@NonNull View itemView) {
            super(itemView);
            mDemoSlider = itemView.findViewById(R.id.slider_offers);
            pagerIndicator = itemView.findViewById(R.id.custom_indicator);

        }

    }

    private void assignSlider(List<Offer> offers ,SliderLayout mDemoSlider , PagerIndicator pagerIndicator) {



       /* HashMap<String, Integer> file_maps = new HashMap<>();
        file_maps.put("slider1", R.drawable.slide1);
        file_maps.put("slider2", R.drawable.slide2);
        file_maps.put("slider3", R.drawable.slide3);


        List<Offer> offerList =new ArrayList<>();

        offerList.add(new Offer("Qassion Mall",""));
        offerList.add(new Offer("City Mall",""));
        offerList.add(new Offer("Damaskino Mall",""));*/



        HashMap<String, String> file_maps = new HashMap<>();
        for (Offer offer : offers)
            file_maps.put("offer" + offer.getId(), Constants.IMG_URL + offer.getImage());

        mDemoSlider.removeAllSliders();
        int j = 0;
        for (String name : file_maps.keySet()) {
            MySliderAdapter textSliderView = new MySliderAdapter(context, offers.get(j));
            // initialize a SliderLayout
            textSliderView
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);


            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
            j++;
        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.setCustomIndicator(pagerIndicator);
    }
}
