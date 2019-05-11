package com.example.yala_mall.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yala_mall.R;

public class HomeFragment extends Fragment {
    View view;


    public HomeFragment() {

    }



    public static HomeFragment getInstance(){
        return new HomeFragment();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }

}
