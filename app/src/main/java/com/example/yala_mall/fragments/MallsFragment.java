package com.example.yala_mall.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yala_mall.R;

public class MallsFragment extends Fragment {
    View view;

    public MallsFragment() {}


    public static MallsFragment getInstance(){
        return new MallsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_malls, container, false);

        return view;
    }

}


