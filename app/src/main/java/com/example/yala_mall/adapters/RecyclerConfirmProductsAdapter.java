package com.example.yala_mall.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.yala_mall.R;
import com.example.yala_mall.interfaces.OnChangeNoteEditText;
import com.example.yala_mall.interfaces.OnClickElegantButton;
import com.example.yala_mall.models.Product;
import com.example.yala_mall.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerConfirmProductsAdapter extends RecyclerView.Adapter<RecyclerConfirmProductsAdapter.ViewHolder> {
    private List<Product> list;
    private Context context;
    private OnChangeNoteEditText listener;

    public RecyclerConfirmProductsAdapter(List<Product> list, Context context , OnChangeNoteEditText listener) {
        this.list = list;
        this.context = context;
        this.listener= listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recycler_confirm_products,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Product product =list.get(i);
        viewHolder.productName.setText(product.getName());
        Picasso.with(context)
                .load(Constants.IMG_URL+product.getGallery().get(0).getImage())
                .into(viewHolder.productImage);
        viewHolder.priceText.setText(product.getPrice());
        viewHolder.quantityText.setText(product.getQuantity());
        viewHolder.noteEditText.setText(product.getNote());
        viewHolder.sumPriceText.setText(Integer.parseInt(product.getPrice())*Integer.parseInt(product.getQuantity())+"");

        viewHolder.noteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 listener.changeNoteEditText(i , viewHolder.noteEditText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView quantityText;
        TextView priceText;
        TextView sumPriceText;
        EditText noteEditText;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            quantityText = itemView.findViewById(R.id.product_quantity);
            priceText = itemView.findViewById(R.id.product_price);
            sumPriceText = itemView.findViewById(R.id.product_sum);
            noteEditText = itemView.findViewById(R.id.product_note);

        }
    }
}
