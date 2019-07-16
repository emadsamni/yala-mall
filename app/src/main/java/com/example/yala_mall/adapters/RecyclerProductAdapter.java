package com.example.yala_mall.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.yala_mall.R;
import com.example.yala_mall.interfaces.OnItemProductClicked;
import com.example.yala_mall.models.Mall;
import com.example.yala_mall.models.Product;
import com.example.yala_mall.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerProductAdapter  extends RecyclerView.Adapter<RecyclerProductAdapter.ViewHolder>  {
    private List<Product> list;
    private Context context;
    private OnItemProductClicked listener;



    public RecyclerProductAdapter(List<Product> list, Context context , OnItemProductClicked listener) {
        this.list = list;
        this.context = context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recycler_pro,null,false);
        return new ViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Product current   = list.get(i);

        viewHolder.textView.setText(current.getName());
        viewHolder.ratingBar.setRating(Integer.parseInt(current.getRate()));
        viewHolder.priceTextView.setText(current.getPrice() + " ل . س");
        if ( !current.getGallery().isEmpty())
        Picasso.with(context).load(Constants.IMG_URL+current.getGallery().get(0).getImage()).into(viewHolder.imageView);
        else  viewHolder.imageView.setImageResource(R.drawable.defult_image);
        if ( Double.parseDouble(list.get(i).getDiscount()) != 1 )
        {
            viewHolder.line.setVisibility(View.VISIBLE);
            viewHolder.priceAfterDiscount.setVisibility(View.VISIBLE);
            viewHolder.priceAfterDiscount.setText(Double.parseDouble(list.get(i).getDiscount()) *Double.parseDouble(list.get(i).getPrice()) +"ل . س");
        }
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onProductClick(current);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView  , priceTextView , priceAfterDiscount;
        ImageView imageView;
        LinearLayout layout;
        View line;
        RatingBar ratingBar;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.pro_name);
            imageView = itemView.findViewById(R.id.pro_image);
            layout = itemView.findViewById(R.id.layout_id);
            priceTextView = itemView.findViewById(R.id.price);
            priceAfterDiscount = itemView.findViewById(R.id.price_after_discount);
            line= itemView.findViewById(R.id.line);
            ratingBar =  itemView.findViewById(R.id.ratingBar);


        }

    }
}
