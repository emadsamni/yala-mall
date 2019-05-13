package com.example.yala_mall.interfaces;


import com.example.yala_mall.models.Category;
import com.example.yala_mall.models.Mall;
import com.example.yala_mall.models.Shop;

public interface OnItemRecyclerClicked {
    void onClickedRecyclerItem(Category category);

    void onClickedRecyclerMallItem(Mall current);

    void onClickedRecyclerShopItem(Shop current);
}
