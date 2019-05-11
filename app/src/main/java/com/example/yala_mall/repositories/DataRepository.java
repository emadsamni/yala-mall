package com.example.yala_mall.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.widget.Toast;

import com.example.yala_mall.R;
import com.example.yala_mall.api.ApiClient;
import com.example.yala_mall.api.ApiInterface;
import com.example.yala_mall.api.ApiResponse;
import com.example.yala_mall.api.CallbackWithRetry;
import com.example.yala_mall.models.Category;
import com.example.yala_mall.utils.Constants;
import com.example.yala_mall.utils.ProgressDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class DataRepository {
    private MutableLiveData<List<Category>>  categoryList;
    private MutableLiveData<Category>  pcCategoryBySCategory;


    private Application application;

    private DataRepository(Application application){
        this.application = application;
    }

    public static DataRepository getInstance(Application application){
        return new DataRepository(application);
    }

    public LiveData<List<Category>> getCategoryList(Context context){
        categoryList = new MutableLiveData<>();
        getCategories(context);
        return categoryList;
    }

    public LiveData<Category> getPcCategoryBySCategory(Context context , int sCategoryId){
        pcCategoryBySCategory = new MutableLiveData<>();
        getSubCategoryById(context , sCategoryId);
        return pcCategoryBySCategory;
    }

    private void getSubCategoryById(Context context , int sCategoryId) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<Category>> call = apiService.getPcategoryByScategory(Constants.API_KEY ,sCategoryId);

        call.enqueue(new CallbackWithRetry<ApiResponse<Category>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<Category>> call, Response<ApiResponse<Category>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    pcCategoryBySCategory.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponse<Category>> call, Throwable t) {

            }
        });
    }

    private void getCategories(Context context) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Category>>> call = apiService.getCategories(Constants.API_KEY);

        call.enqueue(new CallbackWithRetry<ApiResponse<List<Category>>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<List<Category>>> call, Response<ApiResponse<List<Category>>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    categoryList.postValue(response.body().getData());

            }

            @Override
            public void onFailure(Call<ApiResponse<List<Category>>> call, Throwable t) {

            }
        });
    }
}
