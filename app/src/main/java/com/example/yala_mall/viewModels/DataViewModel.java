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
import com.example.yala_mall.repositories.DataRepository;
import com.example.yala_mall.utils.ProgressDialog;

import java.util.List;

public class DataViewModel extends AndroidViewModel {
    private DataRepository repository;
    private LiveData<List<Category>> categoryList;
    private LiveData<Category> pcCategoryBySCategory;
    private LiveData<List<Offer>> offers;
    private LiveData<List<Mall>> malls;
    private LiveData<List<Product>> products;
    private LiveData<List<Product>> productsByMall;
    private LiveData<List<Mall>> mall;

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
        if (pcCategoryBySCategory==null) {
            ProgressDialog.getInstance().show(context);
            pcCategoryBySCategory = repository.getPcCategoryBySCategory(context ,sCategory);
        }
        return pcCategoryBySCategory;
    }

    public LiveData<List<Product>> getProductListByCategory(Context context , int category ){
        if (products==null) {
            ProgressDialog.getInstance().show(context);
            products = repository.getProductsByCategory(context ,category);
        }
        return products;
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

}
