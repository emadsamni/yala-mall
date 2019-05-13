package com.example.yala_mall.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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


    public RecyclerMallAdapter(List<Mall> list, Context context ,OnItemRecyclerClicked onItemRecyclerClicked ) {
        this.list = list;
        this.context = context;
        this.onItemRecyclerClicked = onItemRecyclerClicked;

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
        Picasso.with(context).load(Constants.IMG_URL+current.getLogo()).into(viewHolder.imageView);

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemRecyclerClicked.onClickedRecyclerMallItem(current);
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


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.mall_name);
            imageView = itemView.findViewById(R.id.mall_image);



        }

    }
}
