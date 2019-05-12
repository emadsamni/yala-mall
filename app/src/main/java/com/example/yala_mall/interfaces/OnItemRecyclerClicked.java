package com.example.yala_mall.interfaces;


import com.example.yala_mall.models.Category;
import com.example.yala_mall.models.Mall;

public interface OnItemRecyclerClicked {
    void onClickedRecyclerItem(Category category);

    void onClickedRecyclerMallItem(Mall current);
}
