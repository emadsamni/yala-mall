package com.example.yala_mall.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yala_mall.R;
import com.example.yala_mall.models.Mall;
import com.example.yala_mall.models.Shop;
import com.example.yala_mall.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerShopAdapter  extends RecyclerView.Adapter<RecyclerShopAdapter.ViewHolder> {

    private List<Shop> list;
    private Context context;


    public RecyclerShopAdapter(List<Shop> list, Context context ) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recycler_shop,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Shop current   = list.get(i);

        viewHolder.textView.setText(current.getName());
        Picasso.with(context).load(Constants.IMG_URL+current.getLogo()).into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.shop_name);
            imageView = itemView.findViewById(R.id.shop_image);



        }

    }
}
