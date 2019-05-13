package com.example.yala_mall.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.yala_mall.models.Product;
import com.example.yala_mall.repositories.SearchRepository;
import com.example.yala_mall.utils.ProgressDialog;

import java.util.List;

public class SearchViewModel extends AndroidViewModel {

    private SearchRepository repository;
    private LiveData<List<Product>> products;


    public SearchViewModel(@NonNull Application application) {
        super(application);
        repository = SearchRepository.getInstance(application);
    }

    public LiveData<List<Product>> getProducts(Context context , String name , String mallId){
        ProgressDialog.getInstance().show(context);
        products = repository.getProducts(name, context, mallId);

        return products;
    }

}
