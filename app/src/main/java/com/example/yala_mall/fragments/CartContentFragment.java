package com.example.yala_mall.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yala_mall.R;
import com.example.yala_mall.activities.MasterClass;
import com.example.yala_mall.activities.PaymentActivity;
import com.example.yala_mall.activities.StepperListener;
import com.example.yala_mall.adapters.RecyclerConfirmProductsAdapter;
import com.example.yala_mall.helps.CustomerUtils;
import com.example.yala_mall.interfaces.OnChangeNoteEditText;
import com.example.yala_mall.models.Product;
import com.example.yala_mall.models.ProductP;
import com.example.yala_mall.viewModels.DataViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CartContentFragment extends Fragment implements StepperListener, OnChangeNoteEditText {
    CustomerUtils customerUtils;
    RecyclerConfirmProductsAdapter recyclerAdapter;
    RecyclerView recyclerView;
    DataViewModel dataViewModel;
    TextView textView;
    public CartContentFragment() {
    }

    public static CartContentFragment newInstance() {
        CartContentFragment fragment = new CartContentFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_card_content, container,false);
        assignUIReference(view);
        assignAction(view);
        return  view;
    }

    private void assignAction(View view) {
    }

    private void assignUIReference(View view) {
        recyclerView = view.findViewById(R.id.product_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAdapter = new RecyclerConfirmProductsAdapter(((MasterClass)getActivity().getApplication()).getProductList(),getActivity(), CartContentFragment.this);
        recyclerView.setAdapter(recyclerAdapter);
        dataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        textView = view.findViewById(R.id.sum_text);
        textView.setText("المجموع :"+getSum());

    }

    @Override
    public void onNextClicked() {
        Date currentTime = Calendar.getInstance().getTime();
        ArrayList<ProductP> sendedList= new ArrayList<>();
        for (int i =0 ;i<((MasterClass)getActivity().getApplication()).getProductList().size() ;i++)
        {
            ((MasterClass)getActivity().getApplication()).getProductList().get(i).setProduct_id(((MasterClass)getActivity().getApplication()).getProductList().get(i).getId());
             sendedList.add(new ProductP(((MasterClass)getActivity().getApplication()).getProductList().get(i).getId()
                , ((MasterClass)getActivity().getApplication()).getProductList().get(i).getQuantity() ,
                     ((MasterClass)getActivity().getApplication()).getProductList().get(i).getNote()));
        }
        dataViewModel.addOrder(getActivity() ,sendedList ,
                ((PaymentActivity)getActivity()).getCustomerLocationId() ,currentTime.toString() ,getSum()+"");

    }

    @Override
    public void onBackClicked() {

    }

    @Override
    public void changeNoteEditText(int i, String text) {

        ((MasterClass)getActivity().getApplication()).getProductList().get(i).setNote(text);

    }

    private  double getSum()
    {
        List<Product> temp  =((MasterClass)getActivity().getApplication()).getProductList();
        double res= 0;
        for (int i =0 ;i<temp.size() ;i++)
        {
            res =res + (Double.parseDouble(temp.get(i).getPrice() )*Double.parseDouble(temp.get(i).getDiscount())*(Double.parseDouble(temp.get(i).getQuantity())));
        }
        return  res;
    }
}
