package com.example.yala_mall.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.yala_mall.models.Category;
import com.example.yala_mall.models.Offer;
import com.example.yala_mall.repositories.DataRepository;
import com.example.yala_mall.utils.ProgressDialog;

import java.util.List;

public class DataViewModel extends AndroidViewModel {
    private DataRepository repository;
    private LiveData<List<Category>> categoryList;
    private LiveData<List<Category>> pcCategoryBySCategory;
    private LiveData<List<Offer>> offers;

    public DataViewModel(@NonNull Application application) {
        super(application);
        repository = DataRepository.getInstance(application);
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

    public LiveData<List<Category>> getPcCategoryBySCategory(Context context , int sCategory ){
        if (pcCategoryBySCategory==null) {
            ProgressDialog.getInstance().show(context);
            pcCategoryBySCategory = repository.getPcCategoryBySCategory(context ,sCategory);
        }
        return pcCategoryBySCategory;
    }

}
