package com.example.yala_mall.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.example.yala_mall.R;
import com.example.yala_mall.activities.StepperListener;

public class FinishFragment extends Fragment implements StepperListener {
    int type;
    TextView textView;

    @SuppressLint("ValidFragment")
    public FinishFragment(int type) {
    }


    public static FinishFragment newInstance(int type) {
        FinishFragment fragment = new FinishFragment(type);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.fragment_finish, container,false);
        textView =  view.findViewById(R.id.message);
        if (type ==0)
        {
            textView.setText("لقد تم تأكيد طلبك بنجاح شكراً لك على ثقتك بنا");
        }

        return  view;

    }

    @Override
    public void onNextClicked() {

    }

    @Override
    public void onBackClicked() {

    }
}
