package com.example.yala_mall.fragments;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yala_mall.R;
import com.example.yala_mall.activities.MallActivity;
import com.example.yala_mall.activities.PaymentActivity;
import com.example.yala_mall.activities.ShopActivity;
import com.example.yala_mall.activities.StepperListener;
import com.example.yala_mall.models.Category;
import com.example.yala_mall.models.Customer;
import com.example.yala_mall.utils.Constants;
import com.example.yala_mall.viewModels.LoginViewModel;

import es.dmoral.toasty.Toasty;

public class DeliveryInfoFragment extends Fragment implements StepperListener {

    LoginViewModel loginViewModel;
    Customer myCustomer;
    LinearLayout browseButton;
    TextView addressText;
    int temp =0;
    int customer_location_id= -1;
    Button button;

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
        assignAction(view);
        return  view;

    }

    private void assignUIReference(View view) {
        loginViewModel = ViewModelProviders.of(getActivity()).get(LoginViewModel.class);
        browseButton =view.findViewById(R.id.address_browse);
        addressText =view.findViewById(R.id.address_text);
        button = view.findViewById(R.id.button_add);



        loginViewModel.getCustomer(getActivity()).observe(getActivity(), new Observer<Customer>() {
            @Override
            public void onChanged(@Nullable Customer customer) {
                myCustomer =customer;
            }
        });
    }

    private void assignAction(View view) {
        browseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp=0;
                AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
                String[] addresses = new String[myCustomer.getAddresses().size()];
                for (int i=0;i<myCustomer.getAddresses().size();i++)
                {
                    addresses[i] =myCustomer.getAddresses().get(i).getLocation().getName()+" "+myCustomer.getAddresses().get(i).getAddress();

                }

                adb.setSingleChoiceItems(addresses, 0, new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        temp = which;

                    }
                });
                adb.setNegativeButton("الغاء", null);
                adb.setPositiveButton("تعيين", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       addressText.setText(myCustomer.getAddresses().get(temp).getAddress());
                       customer_location_id =myCustomer.getAddresses().get(temp).getId();

                    }});
                adb.setTitle("أختر أحد العناوين التالية");
                adb.show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAddressDialog addAddressDialog = new AddAddressDialog() ;
                addAddressDialog.init(getActivity() ,DeliveryInfoFragment.this ,myCustomer.getAddresses().size());
                addAddressDialog.show(getActivity().getSupportFragmentManager(), "View JobDialog");
            }
        });
    }



    @Override
    public void onNextClicked() {
       if (customer_location_id!=-1)
       {
           ((PaymentActivity)getActivity()).setCustomerLocationId(customer_location_id);
           ((PaymentActivity)getActivity()).next();
       }
       else
       {
           Toasty.custom(getActivity(), R.string.address_not_selected, null,
                   getActivity().getResources().getColor(R.color.colorPrimary), Constants.TOAST_TIME, false, true).show();

       }
    }

    @Override
    public void onBackClicked() {

    }


}
