package com.example.yala_mall.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.yala_mall.models.Category;
import com.example.yala_mall.models.Mall;
import com.example.yala_mall.models.Offer;
import com.example.yala_mall.models.Product;
import com.example.yala_mall.models.Size;
import com.example.yala_mall.repositories.DataRepository;
import com.example.yala_mall.utils.ProgressDialog;

import java.util.HashMap;
import java.util.List;

public class DataViewModel extends AndroidViewModel {
    private DataRepository repository;
    private LiveData<List<Category>> categoryList;
    private LiveData<Category> pcCategoryBySCategory;
    private LiveData<List<Offer>> offers;
    private LiveData<List<Mall>> malls;
    private LiveData<List<Product>> products;
    private LiveData<List<Product>> productsByCategory;
    private LiveData<List<Product>> productsByMall;
    private LiveData<List<Product>> productsByShop;
    private LiveData<List<Mall>> mall;
    private LiveData<List<Size>> sizes;
    private LiveData<List<Product>> productDetails;

    public DataViewModel(@NonNull Application application) {
        super(application);
        repository = DataRepository.getInstance(application);
    }

    public LiveData<List<Mall>> getMalls(Context context){
        if (malls==null) {
            ProgressDialog.getInstance().show(context);
            malls = repository.getMalls(context);
        }
        return malls;
    }

    public LiveData<List<Offer>> getOffers(Context context){
        if (offers==null) {
            ProgressDialog.getInstance().show(context);
            offers = repository.getOffers(context);
        }
        return offers;
    }

    public LiveData<List<Category>> getCategoryList(Context context){
        if (categoryList==null) {
            ProgressDialog.getInstance().show(context);
            categoryList = repository.getCategoryList(context);
        }
        return categoryList;
    }

    public LiveData<Category> getPcCategoryBySCategory(Context context , int sCategory ){

            ProgressDialog.getInstance().show(context);
            pcCategoryBySCategory = repository.getPcCategoryBySCategory(context ,sCategory);

        return pcCategoryBySCategory;
    }

    public LiveData<List<Size>> getSizeByPCategory(Context context , int id ){

        ProgressDialog.getInstance().show(context);
        sizes = repository.getSizeByPCategory(context ,id);

        return sizes;
    }

    public LiveData<List<Product>> getProductListByCategory(Context context , int category ){
        if (productsByCategory==null) {
            ProgressDialog.getInstance().show(context);
            productsByCategory = repository.getProductsByCategory(context ,category);
        }
        return productsByCategory;
    }

    public LiveData<List<Mall>> getShopsByMall(Context context , int mallId ){
        if (mall==null) {
            ProgressDialog.getInstance().show(context);
            mall = repository.getShopsByMall(context ,mallId);
        }
        return mall;
    }

    public LiveData<List<Product>> getProductsByMall(Context context , int mallId ){
        if (productsByMall==null) {
            ProgressDialog.getInstance().show(context);
            productsByMall = repository.getProductsByMall(context ,mallId);
        }
        return productsByMall;
    }

    public LiveData<List<Product>> getProductsByShop(Context context , int shopId ){
        if (productsByShop==null) {
            ProgressDialog.getInstance().show(context);
            productsByShop = repository.getProductsByShop(context ,shopId);
        }
        return productsByShop;
    }

    public LiveData<List<Product>> getFilter(Context context , HashMap<String, Integer> selectedMap){

            ProgressDialog.getInstance().show(context);
            products = repository.getFilter(context ,selectedMap);

        return products;
    }

    public LiveData<List<Product>> getProductDetails(Context context , String productId ){
        if (productDetails==null) {
            ProgressDialog.getInstance().show(context);
            productDetails = repository.getProductDetails(context ,productId);
        }
        return productDetails;
    }

}
