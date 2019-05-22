package com.example.yala_mall.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yala_mall.R;
import com.example.yala_mall.interfaces.OnItemRecyclerClicked;
import com.example.yala_mall.models.Mall;
import com.example.yala_mall.models.Order;

import java.util.List;

public class RecyclerOrderAdapter extends RecyclerView.Adapter<RecyclerOrderAdapter.ViewHolder> {


    private List<Order> list;
    private Context context;

    public RecyclerOrderAdapter(List<Order> list, Context context ) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_order_item,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Order current = list.get(i);
        viewHolder.priceTextView.setText(current.getPrice());
        viewHolder.deliveryCostTextView.setText(current.getDelivery_cost());
        viewHolder.startTextView.setText(current.getOrder_time());
        if (current.getDelivery_time() != null )
        {
            viewHolder.endTextView.setText(current.getDelivery_time());
        }
        if (current.getOrder_status_id() ==1 )
        {
            viewHolder.statusTextView.setText("جاري التوصيل");
        }
        else
        {
            if (current.getOrder_status_id() ==2 )
            {
                viewHolder.statusTextView.setText("تم التوصيل");
            }
            else
            {
                viewHolder.statusTextView.setText("لم يتم التوصيل");
            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView priceTextView ,  deliveryCostTextView ,startTextView , endTextView ,statusTextView;



        ViewHolder(@NonNull View itemView) {
            super(itemView);
            priceTextView = itemView.findViewById(R.id.order_price);
            deliveryCostTextView = itemView.findViewById(R.id.del_order_cost);
            startTextView = itemView.findViewById(R.id.start_date);
            endTextView = itemView.findViewById(R.id.end_date);
            statusTextView = itemView.findViewById(R.id.status);




        }

    }
}
