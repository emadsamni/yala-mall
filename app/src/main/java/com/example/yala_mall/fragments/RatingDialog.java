package com.example.yala_mall.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yala_mall.R;
import com.example.yala_mall.interfaces.OnClickFilterButton;
import com.example.yala_mall.interfaces.OnClickRatingButton;
import com.example.yala_mall.models.Product;
import com.example.yala_mall.models.Shop;

import java.util.HashMap;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class RatingDialog {

    private Activity context;
    private AlertDialog dialog;
    private OnClickRatingButton listener;
    private int screenSize;
    RatingBar ratingBar;
    EditText notes;
    float myRating ;
    float currentRate;
    Shop shop;
    private RatingDialog(Activity context, OnClickRatingButton listener ,float currentRate ,Shop shop) {
        this.context = context;
        this.listener =listener;
        screenSize = context.getResources().getDisplayMetrics().densityDpi;
        myRating =0;
        this.currentRate= currentRate;
        this.shop= shop;

    }

    public static RatingDialog getInstance(Activity context ,OnClickRatingButton listener ,float currentRate ,Shop shop){
        return new RatingDialog(context , listener  ,currentRate ,shop);
    }

    public void show(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        assignUIReferenceDialog(builder);
        dialog = builder.create();

        dialog.show();

        //designDialog();
    }

    private void assignUIReferenceDialog(AlertDialog.Builder builder) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_dialog_rating, null, false);
        ratingBar = view.findViewById(R.id.ratingBar);
        ratingBar.setRating(currentRate);
        notes = view.findViewById(R.id.my_note);
        builder.setPositiveButton("ارسال", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int newRate = Math.round(ratingBar.getRating());
                if (ratingBar.getRating() != 0) {
                    listener.onRatingButtonClicked(notes.getText().toString(), newRate, shop.getMyRate());
                }
                else {
                    Toast.makeText(context, context.getResources().getString(R.string.zero_rate), Toast.LENGTH_SHORT).show();
                    listener.onRatingButtonClicked("", -1, shop.getMyRate());
                }

            }
        });

        builder.setNegativeButton("تجاهل", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onRatingButtonClicked("" ,-1, shop.getMyRate());
            }


        });
        builder.setView(view);
    }
    public  void assignAction()
    {
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                 myRating =rating;
            }
        });
    }
}
