package com.example.yala_mall.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.yala_mall.models.City;
import com.example.yala_mall.models.Customer;
import com.example.yala_mall.repositories.LoginRepository;
import com.example.yala_mall.utils.ProgressDialog;

import java.util.List;


public class LoginViewModel extends AndroidViewModel {
    private LoginRepository repository;
    private LiveData<Customer> customer;

    public LoginViewModel(Application application) {
        super(application);
        repository = LoginRepository.getInstance(application);
    }
    public void registerUser(Context context,  String phone ){
         ProgressDialog.getInstance().show(context);
         repository.registerUser(context,phone);
    }

    public void login(Context context,  String phone , String code){
        ProgressDialog.getInstance().show(context);
        repository.login(context,phone,code);
    }

    public boolean AddCustomerLocation(Context context , int location_id , String address){
        ProgressDialog.getInstance().show(context);
        return  repository.addCustomerLocation(context,location_id,address);
    }

    public LiveData<Customer> getCustomer(Context context ){
        if (customer==null) {
            ProgressDialog.getInstance().show(context);
            customer = repository.getCustomerInfo(context);
        }
        return customer;


    }


}
