package com.example.yala_mall.viewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.yala_mall.models.Category;
import com.example.yala_mall.repositories.DataRepository;
import com.example.yala_mall.utils.ProgressDialog;

import java.util.List;

public class DataViewModel extends AndroidViewModel {
    private DataRepository repository;
    private LiveData<List<Category>> categoryList;
    private LiveData<Category> pcCategoryBySCategory;

    public DataViewModel(@NonNull Application application) {
        super(application);
        repository = DataRepository.getInstance(application);
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





}
