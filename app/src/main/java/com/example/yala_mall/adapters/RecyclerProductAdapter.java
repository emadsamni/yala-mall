package com.example.yala_mall.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    public RecyclerProductAdapter(List<Product> list, Context context ,OnItemProductClicked listener) {
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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Product current   = list.get(i);

        viewHolder.textView.setText(current.getName());
        Picasso.with(context).load(Constants.IMG_URL+current.getGallery().get(0).getImage()).into(viewHolder.imageView);
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
        TextView textView;
        ImageView imageView;
        LinearLayout layout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.pro_name);
            imageView = itemView.findViewById(R.id.pro_image);
            layout = itemView.findViewById(R.id.layout_id);

        }

    }
}
