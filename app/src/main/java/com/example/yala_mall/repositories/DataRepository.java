package com.example.yala_mall.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.yala_mall.R;
import com.example.yala_mall.api.ApiClient;
import com.example.yala_mall.api.ApiInterface;
import com.example.yala_mall.api.ApiResponse;
import com.example.yala_mall.api.CallbackWithRetry;
import com.example.yala_mall.models.Category;
import com.example.yala_mall.models.Mall;
import com.example.yala_mall.models.Offer;
import com.example.yala_mall.models.Product;
import com.example.yala_mall.models.Size;
import com.example.yala_mall.utils.Constants;
import com.example.yala_mall.utils.ProgressDialog;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {
    private MutableLiveData<List<Category>>  categoryList;
    private MutableLiveData<Category>  pcCategoryBySCategory;
    private MutableLiveData<List<Offer>> offers;
    private MutableLiveData<List<Mall>> malls;
    private MutableLiveData<List<Product>> products;
    private MutableLiveData<List<Mall>> mall;
    private MutableLiveData<List<Product>> productsByMall;
    private MutableLiveData<List<Product>> productsByShop;
    private MutableLiveData<List<com.example.yala_mall.models.Size>> sizes;
    private MutableLiveData<List<Product>> productDetails;


    private Application application;

    private DataRepository(Application application){
        this.application = application;
    }

    public static DataRepository getInstance(Application application){
        return new DataRepository(application);
    }

    public LiveData<List<Product>> getFilter(Context context , HashMap<String,Integer> selectedMap){
        products = new MutableLiveData<>();
        getFilterList(context ,selectedMap);
        return products;
    }

    public LiveData<List<Size>> getSizeByPCategory(Context context ,int id){
        sizes = new MutableLiveData<>();
        getSizeListByPCategory(context ,id);
        return sizes;
    }


    public LiveData<List<Product>> getProductsByShop(Context context ,int shop_id){
        productsByShop = new MutableLiveData<>();
        getProductsListByShop(context ,shop_id);
        return productsByShop;
    }

    public LiveData<List<Product>> getProductsByMall(Context context ,int mall_id){
        productsByMall = new MutableLiveData<>();
        getProductsListByMall(context ,mall_id);
        return productsByMall;
    }

    public LiveData<List<Mall>> getShopsByMall(Context context ,int mall_id){
        mall = new MutableLiveData<>();
        getShopsListByMall(context ,mall_id);
        return mall;
    }

    public LiveData<List<Product>> getProductsByCategory(Context context ,int categoryId){
        products = new MutableLiveData<>();
        getProductListByCategory(context ,categoryId);
        return products;
    }
    public LiveData<List<Offer>> getOffers(Context context){
        offers = new MutableLiveData<>();
        getOffersApi(context);
        return offers;
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

    public LiveData<List<Mall>> getMalls(Context context ){
        malls = new MutableLiveData<>();
        getMallsList(context);
        return malls;
    }

    public LiveData<List<Product>> getProductDetails(Context context ,String productId){
        productDetails = new MutableLiveData<>();
        getProductDetail(context ,productId);
        return productDetails;
    }

    private void getFilterList(Context context, HashMap<String, Integer> selectedMap) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Product>>> call = apiService.getFilter(Constants.API_KEY, selectedMap.get("Cat_id") ,selectedMap.get("sub_id"),selectedMap.get("size"),selectedMap.get("mall_id"),selectedMap.get("shop_id"));
        call.enqueue(new Callback<ApiResponse<List<Product>>>() {
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

            }
        });
     }
    private void getSizeListByPCategory(Context context, int id) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Size>>> call = apiService.getSizeByPCategory(Constants.API_KEY, id);

        call.enqueue(new Callback<ApiResponse<List<Size>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Size>>> call, Response<ApiResponse<List<Size>>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    sizes.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Size>>> call, Throwable t) {

            }
        });

    }
    private  void  getProductsListByMall (Context context ,int  mall_id ) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Product>>> call = apiService.getProductByMall(Constants.API_KEY, mall_id);

        call.enqueue(new Callback<ApiResponse<List<Product>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Product>>> call, Response<ApiResponse<List<Product>>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    productsByMall.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Product>>> call, Throwable t) {

            }
        });
    }

    private  void  getProductsListByShop (Context context ,int  shop_id ) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Product>>> call = apiService.getProductByShop(Constants.API_KEY, shop_id);

        call.enqueue(new Callback<ApiResponse<List<Product>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Product>>> call, Response<ApiResponse<List<Product>>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    productsByShop.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Product>>> call, Throwable t) {

            }
        });
    }
    private void getShopsListByMall(Context context ,int  mall_id )
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Mall>>> call = apiService.getShopByMall(Constants.API_KEY ,mall_id);

        call.enqueue(new Callback<ApiResponse<List<Mall>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Mall>>> call, Response<ApiResponse<List<Mall>>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    mall.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Mall>>> call, Throwable t) {

            }
        });
    }
    private  void  getProductListByCategory(Context context ,int  categoryId )
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Product>>> call = apiService.getProductByCategory(Constants.API_KEY ,categoryId);

        call.enqueue(new Callback<ApiResponse<List<Product>>>() {
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

            }
        });
    }
    private void getMallsList(Context context) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Mall>>> call = apiService.getMalls(Constants.API_KEY);

        call.enqueue(new Callback<ApiResponse<List<Mall>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Mall>>> call, Response<ApiResponse<List<Mall>>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    malls.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Mall>>> call, Throwable t) {
                Log.d("TSTS",t.getMessage());
            }
        });
    }

    private void getOffersApi(Context context) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Offer>>> call = apiService.getOffers(Constants.API_KEY);

        call.enqueue(new CallbackWithRetry<ApiResponse<List<Offer>>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<List<Offer>>> call, Response<ApiResponse<List<Offer>>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    offers.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Offer>>> call, Throwable t) {
                Log.d("TSTS",t.getMessage());
            }
        });
    }

    private void getSubCategoryById(Context context , int sCategoryId) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Category>>> call = apiService.getPcategoryByScategory(Constants.API_KEY ,sCategoryId);

        call.enqueue(new CallbackWithRetry<ApiResponse<List<Category>>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<List<Category>>> call, Response<ApiResponse<List<Category>>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    pcCategoryBySCategory.postValue(response.body().getData().get(0));
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Category>>> call, Throwable t) {
                Log.d("TSTS",t.getMessage());
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

    private void getProductDetail (Context context ,String  productId ) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Product>>> call = apiService.getProductDetails(Constants.API_KEY, productId);

        call.enqueue(new Callback<ApiResponse<List<Product>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Product>>> call, Response<ApiResponse<List<Product>>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    productDetails.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Product>>> call, Throwable t) {
                Log.d("TSTS",t.getMessage());
            }
        });
    }
}
