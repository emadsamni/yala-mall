package com.example.yala_mall.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yala_mall.R;
import com.example.yala_mall.activities.StepperListener;

public class DeliveryInfoFragment extends Fragment implements StepperListener {

    public DeliveryInfoFragment() {
    }

    public static DeliveryInfoFragment newInstance() {
        DeliveryInfoFragment fragment = new DeliveryInfoFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_delivery_info, container,false);
        assignUIReference(view);
        return  view;

    }

    private void assignUIReference(View view) {
    }

    @Override
    public void onNextClicked() {

    }

    @Override
    public void onBackClicked() {

    }
}
