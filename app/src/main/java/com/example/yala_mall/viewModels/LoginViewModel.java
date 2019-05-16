package com.example.yala_mall.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import com.example.yala_mall.repositories.LoginRepository;
import com.example.yala_mall.utils.ProgressDialog;


public class LoginViewModel extends AndroidViewModel {
    private LoginRepository repository;

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


}
