package com.example.yala_mall.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.yala_mall.R;
import com.example.yala_mall.activities.MainActivity;
import com.example.yala_mall.adapters.SpinnerAdapter;
import com.example.yala_mall.models.Category;
import com.example.yala_mall.models.PcCategory;
import com.example.yala_mall.viewModels.DataViewModel;

import java.util.List;

public class CategoryFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    View view;
    int catId;
    DataViewModel dataViewModel;
    boolean visible = false;
    Spinner subCatSpinner;
    Spinner sizeSpinner;


    public CategoryFragment() {

    }

    @SuppressLint("ValidFragment")
    public CategoryFragment(int catId) {
        this.catId = catId;
    }


    public static CategoryFragment getInstance(int catId) {

        return new CategoryFragment(catId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, container, false);
         assignUiReference(view);
        // assignAction();
        getSpinnerData();
         return view;
    }

    public void assignUiReference(View view) {

        subCatSpinner = view.findViewById(R.id.sub_cat_spinner);
        sizeSpinner = view.findViewById(R.id.size_spinner);
        dataViewModel = ViewModelProviders.of(getActivity()).get(DataViewModel.class);
    }



    private <T> void assignSpinner(List<T> list , Spinner spinner){

        SpinnerAdapter<T> adapter = new SpinnerAdapter<>(list, getActivity());
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(CategoryFragment.this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getSpinnerData()
    {

        dataViewModel.getPcCategoryBySCategory(getActivity(),catId).observe(getActivity(), new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {

                assignSpinner(categories.get(0).getP_category() ,subCatSpinner);

            }
        });
    }
}
