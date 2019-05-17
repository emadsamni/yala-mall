package com.example.yala_mall.api;




import com.example.yala_mall.models.Address;
import com.example.yala_mall.models.Category;
import com.example.yala_mall.models.City;
import com.example.yala_mall.models.Customer;
import com.example.yala_mall.models.Mall;
import com.example.yala_mall.models.Offer;
import com.example.yala_mall.models.PcCategory;
import com.example.yala_mall.models.Product;
import com.example.yala_mall.models.Size;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {


    @POST("register")
    Call<ApiResponse<Customer>> register(@Query("key") String key , @Query("phone") String phone);

    @POST("login")
    Call<ApiResponse<Customer>> login(@Query("key") String key , @Query("phone") String phone ,@Query("verification_code") String verification_code);

    @GET("getOffers")
    Call<ApiResponse<List<Offer>>> getOffers(@Query("key") String key);


    @GET("getCities")
    Call<ApiResponse<List<City>>> getCities(@Query("key") String key);

    @POST("addCustomerLocation")
    Call<ApiResponse<Address>> addCustomerLocation(@Query("key") String key , @Query("token") String token
                                                        , @Query("location_id") int  location_id , @Query("address") String address);

    @GET("getCustomer")
    Call<ApiResponse<Customer>> getCustomer(@Query("key") String key , @Query("token") String token);

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

    @GET("getProductByshop")
    Call<ApiResponse<List<Product>>> getProductByShop(@Query("key") String key ,@Query("id") int id);

    @GET("search")
    Call<ApiResponse<List<Product>>> search(@Query("key") String key ,@Query("name") String name);

    @GET("searchByMall")
    Call<ApiResponse<List<Product>>> searchByMall(@Query("key") String key ,@Query("name") String name,@Query("mall_id") String mallId);

    @GET("searchByShop")
    Call<ApiResponse<List<Product>>> searchByShop(@Query("key") String key ,@Query("name") String name,@Query("shop_id") String shopId);

    @GET("getSizeByPcategory")
    Call<ApiResponse<List<Size>>> getSizeByPCategory(@Query("key") String key , @Query("id") int id);

    @GET("getProductDetails")
    Call<ApiResponse<List<Product>>> getProductDetails(@Query("key") String key , @Query("id") String id);

    @GET("getFilter")
    Call<ApiResponse<List<Product>>> getFilter(@Query("key") String key , @Query("scategory_id") Integer  sCategory ,@Query("pcategory_id") Integer  pCategory ,@Query("size") Integer  size  ,@Query("mall_id") Integer  mallId  ,@Query("shop_id") Integer  Shop_id);
}


