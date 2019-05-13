package com.example.yala_mall.repositories;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.yala_mall.R;
import com.example.yala_mall.api.ApiClient;
import com.example.yala_mall.api.ApiInterface;
import com.example.yala_mall.api.ApiResponse;
import com.example.yala_mall.api.CallbackWithRetry;
import com.example.yala_mall.models.Product;
import com.example.yala_mall.utils.Constants;
import com.example.yala_mall.utils.ProgressDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class SearchRepository {
    private Application application;
    private MutableLiveData<List<Product>> products;

    private SearchRepository(Application application) {
        this.application = application;
    }

    public static SearchRepository getInstance(Application application){
        return new SearchRepository(application);
    }

    public MutableLiveData<List<Product>> getProducts(String name , Context context,String mallId) {
        products = new MutableLiveData<>();
        if (mallId == null)
            getProduct(name,context);
        else
            getProductByMall(name,context,mallId);

        return products;
    }

    private void getProduct(String name , Context context){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Product>>> call = apiService.search(Constants.API_KEY,name);

        call.enqueue(new CallbackWithRetry<ApiResponse<List<Product>>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<List<Product>>> call, Response<ApiResponse<List<Product>>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    products.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Product>>> call, Throwable t) {
                Log.d("TSTS",t.getMessage());
            }
        });
    }

    private void getProductByMall(String name , Context context , String mallId){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Product>>> call = apiService.searchByMall(Constants.API_KEY,name,mallId);

        call.enqueue(new CallbackWithRetry<ApiResponse<List<Product>>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<List<Product>>> call, Response<ApiResponse<List<Product>>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    products.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Product>>> call, Throwable t) {
                Log.d("TSTS",t.getMessage());
            }
        });
    }
}
