package com.example.yala_mall.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.yala_mall.R;
import com.example.yala_mall.interfaces.OnItemRecyclerClicked;
import com.example.yala_mall.models.Address;
import com.example.yala_mall.models.Category;

import java.util.List;

public class RecyclerAddressAdapter extends RecyclerView.Adapter<RecyclerAddressAdapter.ViewHolder> {

    private List<Address> list;
    private Context context;
    private OnItemRecyclerClicked onItemRecyclerClicked;

    public RecyclerAddressAdapter(List<Address> list, Context context ) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_address_item,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Address current =list.get(i);
       viewHolder.button.setText(current.getAddress());

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
