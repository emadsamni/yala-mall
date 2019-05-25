package com.example.yala_mall.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.yala_mall.R;
import com.example.yala_mall.interfaces.OnClickElegantButton;
import com.example.yala_mall.models.Product;
import com.example.yala_mall.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerCartProductsAdapter extends RecyclerView.Adapter<RecyclerCartProductsAdapter.ViewHolder> {

    private List<Product> list;
    private Context context;
    private OnClickElegantButton listener;

    public RecyclerCartProductsAdapter(List<Product> list, Context context , OnClickElegantButton listener) {
        this.list = list;
        this.context = context;
        this.listener= listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recycler_cart_products,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
       Product product =list.get(i);

       viewHolder.productName.setText(product.getName());
        if ( !product.getGallery().isEmpty())
        Picasso.with(context)
                .load(Constants.IMG_URL+product.getGallery().get(0).getImage())
                .into(viewHolder.productImage);
        else
                viewHolder.productImage.setImageResource(R.drawable.defult_image);

        viewHolder.numberButton.setNumber(product.getQuantity());
        if (product.getSize().getId()!=-1)
          viewHolder.productSize.setText(product.getSize().getName());
        else
            viewHolder.productSize.setVisibility(View.GONE);

        viewHolder.numberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
             listener.clickElegantButton(product,viewHolder.numberButton.getNumber());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName , productSize;
        ElegantNumberButton numberButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
           productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            numberButton = itemView.findViewById(R.id.quantity_button);
            productSize = itemView.findViewById(R.id.size);

        }
    }
}
