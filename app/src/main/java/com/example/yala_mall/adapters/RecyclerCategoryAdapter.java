package com.example.yala_mall.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.example.yala_mall.R;
import com.example.yala_mall.interfaces.OnItemRecyclerClicked;
import com.example.yala_mall.models.Category;

import java.util.List;

public class RecyclerCategoryAdapter extends RecyclerView.Adapter<RecyclerCategoryAdapter.ViewHolder> {

    private List<Category> list;
    private Context context;
    private OnItemRecyclerClicked onItemRecyclerClicked;

    public RecyclerCategoryAdapter(List<Category> list, Context context ,OnItemRecyclerClicked onItemRecyclerClicked ) {
        this.list = list;
        this.context = context;
        this.onItemRecyclerClicked = onItemRecyclerClicked;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recycler_category,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
       Category current =list.get(i);
       viewHolder.button.setText(current.getName());
       viewHolder.button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              onItemRecyclerClicked.onClickedRecyclerItem(current);
           }
       });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Button button;


    ViewHolder(@NonNull View itemView) {
        super(itemView);
        button= itemView.findViewById(R.id.category_button);

    }
}
}
