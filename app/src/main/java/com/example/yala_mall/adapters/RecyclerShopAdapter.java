package com.example.yala_mall.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.yala_mall.R;
import com.example.yala_mall.interfaces.OnItemRecyclerClicked;
import com.example.yala_mall.models.Mall;
import com.example.yala_mall.models.Shop;
import com.example.yala_mall.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.http.Body;

public class RecyclerShopAdapter  extends RecyclerView.Adapter<RecyclerShopAdapter.ViewHolder> {

    private List<Shop> list;
    private Context context;
    private OnItemRecyclerClicked onItemRecyclerClicked;


    public RecyclerShopAdapter(List<Shop> list, Context context ,OnItemRecyclerClicked onItemRecyclerClicked ) {
        this.list = list;
        this.context = context;
        this.onItemRecyclerClicked = onItemRecyclerClicked;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_rec_shop,null,false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Shop current   = list.get(i);
        viewHolder.textView.setText(current.getName());
        viewHolder.ratingBar.setRating(Integer.parseInt(current.getRate()));
        if (current.getShop_status().getId() ==1)
        {
            viewHolder.stateTextView.setTextColor(context.getResources().getColor(R.color.color_red));
        }
        else
        {
            viewHolder.stateTextView.setTextColor(context.getResources().getColor(R.color.green));
        }
        viewHolder.stateTextView.setText(current.getShop_status().getName());
        if (current.getLogo()!=null)
        Picasso.with(context).load(Constants.IMG_URL+current.getLogo()).into(viewHolder.imageView);
        else
            viewHolder.imageView.setImageResource(R.drawable.defult_image);
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemRecyclerClicked.onClickedRecyclerShopItem(current);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView  , stateTextView ;
        ImageView imageView;
        LinearLayout layout;
        RatingBar ratingBar;



        ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout_id);
            imageView = itemView.findViewById(R.id.shop_image);
            textView = itemView.findViewById(R.id.shop_name);
            stateTextView = itemView.findViewById(R.id.state);
            ratingBar =  itemView.findViewById(R.id.ratingBar);





        }

    }
}
