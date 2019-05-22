package com.example.yala_mall.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.yala_mall.models.Category;
import com.example.yala_mall.models.City;
import com.example.yala_mall.models.Mall;
import com.example.yala_mall.models.Offer;
import com.example.yala_mall.models.Product;
import com.example.yala_mall.models.ProductP;
import com.example.yala_mall.models.Size;
import com.example.yala_mall.repositories.DataRepository;
import com.example.yala_mall.utils.ProgressDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataViewModel extends AndroidViewModel {
    private DataRepository repository;
    private LiveData<List<Category>> categoryList;
    private LiveData<Category> pcCategoryBySCategory;
    private LiveData<List<Offer>> offers;
    private LiveData<List<Offer>> offersByShop;
    private LiveData<Mall> offersByMall;
    private LiveData<List<Mall>> malls;
    private LiveData<List<Product>> products;
    private LiveData<List<Product>> productsByCategory;
    private LiveData<List<Product>> productsByMall;
    private LiveData<List<Product>> productsByShop;
    private LiveData<List<Mall>> mall;
    private LiveData<List<Size>> sizes;
    private LiveData<List<Product>> productDetails;
    private LiveData<List<City>> cities;

    public DataViewModel(@NonNull Application application) {
        super(application);
        repository = DataRepository.getInstance(application);
    }

    public LiveData<List<City>> getCities(Context context){
        if (cities==null) {
            ProgressDialog.getInstance().show(context);
            cities = repository.getCities(context);
        }
        return cities;
    }

    public LiveData<List<Mall>> getMalls(Context context){
        if (malls==null) {
            ProgressDialog.getInstance().show(context);
            malls = repository.getMalls(context);
        }
        return malls;
    }

    public LiveData<List<Offer>> getOffers(Context context){
            ProgressDialog.getInstance().show(context);
            offers = repository.getOffers(context);

        return offers;
    }


    public LiveData<List<Offer>> getOffersByShop(Context context , int shopId){
            ProgressDialog.getInstance().show(context);
            offers = repository.getOffersByShop(context , shopId);

        return offers;
    }


    public LiveData<Mall> getOffersByMall(Context context , int mall_id){
        ProgressDialog.getInstance().show(context);
        offersByMall = repository.getOffersByMall(context , mall_id);
        return offersByMall;
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

            //ProgressDialog.getInstance().show(context);
            mall = repository.getShopsByMall(context ,mallId);

        return mall;
    }

    public LiveData<List<Product>> getProductsByMall(Context context , int mallId ){

            //ProgressDialog.getInstance().show(context);
            productsByMall = repository.getProductsByMall(context ,mallId);

        return productsByMall;
    }

    public LiveData<List<Product>> getProductsByShop(Context context , int shopId ){

            ProgressDialog.getInstance().show(context);
            productsByShop = repository.getProductsByShop(context ,shopId);

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

    public void addOrder(Context context , ArrayList<ProductP> products , int  customer_location_id , String  order_time , String  order_price){
        ProgressDialog.getInstance().show(context);
        repository.addOrder(context,products,customer_location_id ,order_time ,order_price);
    }

}
