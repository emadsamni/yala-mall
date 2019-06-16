package com.example.yala_mall.repositories;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.yala_mall.R;
import com.example.yala_mall.activities.MainActivity;
import com.example.yala_mall.activities.RegisterActivity;
import com.example.yala_mall.api.ApiClient;
import com.example.yala_mall.api.ApiInterface;
import com.example.yala_mall.api.ApiResponse;
import com.example.yala_mall.api.CallbackWithRetry;
import com.example.yala_mall.helps.CustomerUtils;
import com.example.yala_mall.models.Address;
import com.example.yala_mall.models.City;
import com.example.yala_mall.models.Customer;
import com.example.yala_mall.models.Size;
import com.example.yala_mall.utils.Constants;
import com.example.yala_mall.utils.ProgressDialog;
import com.example.yala_mall.utils.Utils;

import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Response;

public class LoginRepository {
    private Application application;
    private CustomerUtils customerUtils;
    private MutableLiveData<Customer> customer;

    private boolean added =false;


    private LoginRepository(Application application){
        this.application = application;
        this.customerUtils = CustomerUtils.getInstance(application);
    }

    public static LoginRepository getInstance(Application application){
        return new LoginRepository(application);
    }


    public void registerUser(Context context , String phone){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<Customer>> call=apiService.register(Constants.API_KEY ,phone);

        call.enqueue(new CallbackWithRetry<ApiResponse<Customer>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<Customer>> call, Response<ApiResponse<Customer>> response) {
                if (! response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    return;
                }
                ProgressDialog.getInstance().cancel();
                ((RegisterActivity) context).setPhone(phone);
                ((RegisterActivity) context).next();
                Date date = new Date(System.currentTimeMillis());
                CustomerUtils.getInstance(context).addLong(Constants.PREF_TIM,date.getTime());
            }

            @Override
            public void onFailure(Call<ApiResponse<Customer>> call, Throwable t) {

            }
        });


    }

    public void login(Context context , String phone , String code){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<Customer>> call=apiService.login(Constants.API_KEY ,phone,code);
        call.enqueue(new CallbackWithRetry<ApiResponse<Customer>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<Customer>> call, Response<ApiResponse<Customer>> response) {
                if (! response.isSuccessful()){

                    ProgressDialog.getInstance().cancel();
                        Toasty.custom(application,R.string.unexpected_api_error,null,
                                application.getResources().getColor(R.color.colorPrimary),Constants.TOAST_TIME,false,true).show();
                    return;
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getMessage().equals("invaild verification code") ) {
                    Toasty.custom(application, R.string.login_failed, null,
                            application.getResources().getColor(R.color.colorPrimary), Constants.TOAST_TIME, false, true).show();
                    return;
                }

                String token = response.body().getData().getToken();
                customerUtils.addString(Constants.PREF_TOKEN, token);
                Intent toMain = new Intent(context, MainActivity.class);
                ((RegisterActivity) context).next();

                //toMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //context.startActivity(toMain);
               // ((Activity)context).finish();

            }

            @Override
            public void onFailure(Call<ApiResponse<Customer>> call, Throwable t) {
                Utils.getInstance(application).problemWithConnection(application);
            }
        });


    }

    public  boolean addCustomerLocation(Context context , int location_id , String address)
    {
        addCustomerLocationApi(context ,location_id ,address);
        return added;
    }
    public void addCustomerLocationApi(Context context , int location_id , String address){
        added =false;
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<Address>> call=apiService.addCustomerLocation(Constants.API_KEY ,customerUtils.getString(Constants.PREF_TOKEN) , location_id ,address);

        call.enqueue(new CallbackWithRetry<ApiResponse<Address>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<Address>> call, Response<ApiResponse<Address>> response) {
                if (! response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toasty.custom(application, R.string.internet_error, null,
                            application.getResources().getColor(R.color.colorPrimary), Constants.TOAST_TIME, false, true).show();
                    return ;
                }
                ProgressDialog.getInstance().cancel();
                Toasty.custom(application, R.string.address_added, null,
                        application.getResources().getColor(R.color.colorPrimary), Constants.TOAST_TIME, false, true).show();
                getCustomer(context);
                added=true;
                return;

            }

            @Override
            public void onFailure(Call<ApiResponse<Address>> call, Throwable t) {
                ProgressDialog.getInstance().cancel();
                Toasty.custom(application, R.string.internet_error, null,
                        application.getResources().getColor(R.color.colorPrimary), Constants.TOAST_TIME, false, true).show();
            }
        });

    }

    public LiveData<Customer> getCustomerInfo(Context context){
        customer = new MutableLiveData<>();
        getCustomer(context );
        return customer;
    }
    public void getCustomer(Context context){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<Customer>> call=apiService.getCustomer(Constants.API_KEY ,customerUtils.getString(Constants.PREF_TOKEN) );

        call.enqueue(new CallbackWithRetry<ApiResponse<Customer>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<Customer>> call, Response<ApiResponse<Customer>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                    return;
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    customer.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponse<Customer>> call, Throwable t) {
                Toast.makeText(context, "cdc", Toast.LENGTH_SHORT).show();


            }
        });

    }
}
