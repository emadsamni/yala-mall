package com.example.yala_mall.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.yala_mall.R;
import com.example.yala_mall.activities.MainActivity;
import com.example.yala_mall.activities.MasterClass;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class MessageDialog {
    private Activity context;
    private AlertDialog dialog;
//    private OnClickFilterButton listener;
    private int screenSize;
    private String message;


    private MessageDialog(Activity context , String message) {
        this.context = context;
//        this.listener = listener;
        this.message = message;
        screenSize = context.getResources().getDisplayMetrics().densityDpi;
    }

    public static MessageDialog getInstance(Activity context , String message){
        return new MessageDialog(context,message);
    }

    public void show(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        assignUIReferenceDialog(builder);
        dialog = builder.create();
        dialog.show();
        assignAction();
    }

    private void assignUIReferenceDialog (AlertDialog.Builder builder) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_cart_permission, null, false);

        TextView messageView = view.findViewById(R.id.message_id);
        messageView.setText(message);
        builder.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (context instanceof MainActivity){
                    ((MasterClass)context.getApplication()).productList = new ArrayList<>();
                    context.finish();
                }
            }
        });

        builder.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setView(view);
    }

    private void assignAction() {

    }
}


