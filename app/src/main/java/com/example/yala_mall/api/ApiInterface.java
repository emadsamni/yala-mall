package com.example.yala_mall.api;




import com.example.yala_mall.models.Category;
import com.example.yala_mall.models.Customer;
import com.example.yala_mall.models.Mall;
import com.example.yala_mall.models.Offer;
import com.example.yala_mall.models.PcCategory;
import com.example.yala_mall.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {


    @POST("register")
    Call<ApiResponse<Customer>> register(@Query("key") String key , @Query("key") String phone);

    @POST("login")
    Call<ApiResponse<Customer>> login(@Query("key") String key , @Query("phone") String phone ,@Query("verification_code") String verification_code);

    @GET("getOffers")
    Call<ApiResponse<List<Offer>>> getOffers(@Query("key") String key);

    @GET("getPcategory")
    Call<ApiResponse<List<PcCategory>>> getPcategory(@Query("key") String key);

    @GET("getCategories")
    Call<ApiResponse<List<Category>>> getCategories(@Query("key") String key);

    @GET("getPcategoryByScategory")
    Call<ApiResponse<List<Category>>> getPcategoryByScategory(@Query("key") String key ,@Query("id") int id);

    @GET("getMalls")
    Call<ApiResponse<List<Mall>>> getMalls(@Query("key") String key);

    @GET("getProductByCategory")
    Call<ApiResponse<List<Product>>> getProductByCategory(@Query("key") String key ,@Query("id") int id);

    @GET("getShopByMall")
    Call<ApiResponse<List<Mall>>> getShopByMall(@Query("key") String key ,@Query("mall_id") int id);

    @GET("getProductByMall")
    Call<ApiResponse<List<Product>>> getProductByMall(@Query("key") String key ,@Query("mall_id") int id);

    @GET("search")
    Call<ApiResponse<List<Product>>> search(@Query("key") String key ,@Query("name") String name);

    @GET("searchByMall")
    Call<ApiResponse<List<Product>>> searchByMall(@Query("key") String key ,@Query("name") String name,@Query("mall_id") String mallId);
}


