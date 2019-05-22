package com.example.yala_mall.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yala_mall.R;
import com.example.yala_mall.interfaces.OnItemRecyclerClicked;
import com.example.yala_mall.models.Category;
import com.example.yala_mall.models.Mall;
import com.example.yala_mall.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerMallAdapter extends RecyclerView.Adapter<RecyclerMallAdapter.ViewHolder> {

    private List<Mall> list;
    private Context context;
    private OnItemRecyclerClicked onItemRecyclerClicked;
    private  RelativeLayout relativeLayout;


    public RecyclerMallAdapter(List<Mall> list, Context context , OnItemRecyclerClicked onItemRecyclerClicked  , RelativeLayout relativeLayout) {
        this.list = list;
        this.context = context;
        this.onItemRecyclerClicked = onItemRecyclerClicked;
        this.relativeLayout = relativeLayout;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recycler_mall,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Mall current   = list.get(i);
        viewHolder.textView.setText(current.getName());
        viewHolder.addressTextView.setText(current.getAddress());
        Picasso.with(context).load(Constants.IMG_URL+current.getLogo()).into(viewHolder.imageView);

        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemRecyclerClicked.onClickedRecyclerMallItem(current);
            }
        });

        if (i %2 == 0)
        {
             viewHolder.linearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
             viewHolder.coloredLinearLayout.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
             viewHolder.textView.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }
        else
        {
            viewHolder.linearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            viewHolder.coloredLinearLayout.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            viewHolder.textView.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }
        final ViewTreeObserver observer = relativeLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                viewHolder.coloredLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(relativeLayout.getWidth()/2  ,LinearLayout.LayoutParams.MATCH_PARENT  ));


            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView ,  addressTextView;
        ImageView imageView;
        LinearLayout linearLayout;
        LinearLayout coloredLinearLayout;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.mall_name);
            addressTextView = itemView.findViewById(R.id.mall_address);
            imageView = itemView.findViewById(R.id.mall_image);
            linearLayout = itemView.findViewById(R.id.mall_layout);
            coloredLinearLayout = itemView.findViewById(R.id.colored_layout);




        }

    }
}
