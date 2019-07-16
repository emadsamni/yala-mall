package com.example.yala_mall.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.yala_mall.R;
import com.example.yala_mall.activities.MainActivity;
import com.example.yala_mall.activities.MasterClass;
import com.example.yala_mall.activities.PaymentActivity;
import com.example.yala_mall.api.ApiClient;
import com.example.yala_mall.api.ApiInterface;
import com.example.yala_mall.api.ApiResponse;
import com.example.yala_mall.api.CallbackWithRetry;
import com.example.yala_mall.helps.CustomerUtils;
import com.example.yala_mall.models.Address;
import com.example.yala_mall.models.Category;
import com.example.yala_mall.models.City;
import com.example.yala_mall.models.Customer;
import com.example.yala_mall.models.Favorite;
import com.example.yala_mall.models.Mall;
import com.example.yala_mall.models.Offer;
import com.example.yala_mall.models.Order;
import com.example.yala_mall.models.Product;
import com.example.yala_mall.models.ProductP;
import com.example.yala_mall.models.Service;
import com.example.yala_mall.models.Shop;
import com.example.yala_mall.models.Size;
import com.example.yala_mall.models.Slide;
import com.example.yala_mall.utils.Constants;
import com.example.yala_mall.utils.ProgressDialog;
import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataRepository {
    private MutableLiveData<List<Category>>  categoryList;
    private MutableLiveData<Category>  pcCategoryBySCategory;
    private MutableLiveData<List<Offer>> offers;
    private MutableLiveData<List<Offer>> sliders;
    private MutableLiveData<List<Offer>> offersByShop;
    private MutableLiveData<Mall> offersByMall;
    private MutableLiveData<List<Mall>> malls;
    private MutableLiveData<List<Product>> products;
    private MutableLiveData<List<Mall>> mall;
    private MutableLiveData<List<Product>> productsByMall;
    private MutableLiveData<List<Product>> productsByShop;
    private MutableLiveData<List<com.example.yala_mall.models.Size>> sizes;
    private MutableLiveData<List<Product>> productDetails;
    private MutableLiveData<List<City>> cities;
    private MutableLiveData<List<Product>> sizesByProduct;
    private MutableLiveData<List<Service>> services;
    private CustomerUtils customerUtils;
    private MutableLiveData<Shop> ratedShop;
    private MutableLiveData<Favorite> favorite;



    private Application application;

    private DataRepository(Application application){
        this.application = application;
        this.customerUtils = CustomerUtils.getInstance(application);
    }

    public static DataRepository getInstance(Application application){
        return new DataRepository(application);
    }

    public LiveData<List<Product>> getFilter(Context context , HashMap<String,Integer> selectedMap , int lastId){
        products = new MutableLiveData<>();
        getFilterList(context ,selectedMap , lastId);
        return products;
    }
    public LiveData<Shop> rateShop(Context context , Shop  shop , int  rate , String  notes ){
        ratedShop = new MutableLiveData<>();
        rateShopFun(context ,shop,rate,notes);
        return ratedShop;
    }

    public LiveData<Favorite> addFavoirte(Context context , Product  product  ){
        favorite = new MutableLiveData<>();
        addFavoriteFun(context ,product);
        return favorite;
    }

    public LiveData<Favorite> deleteFavoirte(Context context , Product  product  ){
        favorite = new MutableLiveData<>();
        deleteFavoriteFun(context ,product);
        return favorite;
    }


    public LiveData<List<Product>> getSizeByProduct(Context context , int productId){
        sizesByProduct = new MutableLiveData<>();
        getSizesByProductApi(context ,productId);
        return sizesByProduct;
    }

    public LiveData<List<Service>> getServices(Context context ){
        services = new MutableLiveData<>();
        getServicesApi(context);
        return services;
    }



    public LiveData<List<City>> getCities(Context context){
        cities = new MutableLiveData<>();
        getCityList(context);
        return cities;
    }


    public LiveData<List<Offer>> getSliders(Context context){
        sliders = new MutableLiveData<>();
        getSlidersList(context);
        return sliders;
    }



    public LiveData<List<Size>> getSizeByPCategory(Context context ,int id){
        sizes = new MutableLiveData<>();
        getSizeListByPCategory(context ,id);
        return sizes;
    }


    public LiveData<List<Product>> getProductsByShop(Context context ,int shop_id , int lastId){
        productsByShop = new MutableLiveData<>();
        getProductsListByShop(context ,shop_id , lastId);
        return productsByShop;
    }

    public LiveData<List<Product>> getProductsByMall(Context context ,int mall_id , int lastId){
        productsByMall = new MutableLiveData<>();
        getProductsListByMall(context ,mall_id , lastId);
        return productsByMall;
    }

    public LiveData<List<Mall>> getShopsByMall(Context context ,int mall_id){
        mall = new MutableLiveData<>();
        getShopsListByMall(context ,mall_id);
        return mall;
    }

    public LiveData<List<Product>> getProductsByCategory(Context context ,int categoryId ,int lastId){
        products = new MutableLiveData<>();
        getProductListByCategory(context ,categoryId ,lastId);
        return products;
    }
    public LiveData<List<Offer>> getOffers(Context context){
        offers = new MutableLiveData<>();
        getOffersApi(context);
        return offers;
    }

    public LiveData<List<Offer>> getOffersByShop(Context context , int shopId){
        offersByShop = new MutableLiveData<>();
        getOffersByShopApi(context , shopId);
        return offersByShop;
    }


    public LiveData<Mall> getOffersByMall(Context context , int mall_id){
        offersByMall = new MutableLiveData<>();
        getOffersByMallApi(context , mall_id);
        return offersByMall;
    }



    public LiveData<List<Category>> getCategoryList(Context context){
        categoryList = new MutableLiveData<>();
        getCategories(context);
        return categoryList;
    }

    public LiveData<Category> getPcCategoryBySCategory(Context context , int sCategoryId){
        pcCategoryBySCategory = new MutableLiveData<>();
        getSubCategoryById(context , sCategoryId);
        return pcCategoryBySCategory;
    }

    public LiveData<List<Mall>> getMalls(Context context ){
        malls = new MutableLiveData<>();
        getMallsList(context);
        return malls;
    }

    public LiveData<List<Product>> getProductDetails(Context context ,String productId){
        productDetails = new MutableLiveData<>();
        getProductDetail(context ,productId);
        return productDetails;
    }

    private void getCityList(Context context) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<City>>> call = apiService.getCities(Constants.API_KEY);


        call.enqueue(new CallbackWithRetry<ApiResponse<List<City>>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<List<City>>> call, Response<ApiResponse<List<City>>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    cities.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponse<List<City>>> call, Throwable t) {
                onFailure(t);
            }
        });
    }

    private void getOffersByMallApi(Context context, int mall_id) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Mall>>> call = apiService.getOffersByMall(Constants.API_KEY , mall_id);

        call.enqueue(new CallbackWithRetry<ApiResponse<List<Mall>>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<List<Mall>>> call, Response<ApiResponse<List<Mall>>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null  && response.body().getData().size()!= 0)
                    offersByMall.postValue(response.body().getData().get(0));
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Mall>>> call, Throwable t) {
                onFailure(t);
            }
        });

    }

    private void getFilterList(Context context, HashMap<String, Integer> selectedMap , int lastId) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Product>>> call = apiService.getFilter(Constants.API_KEY, selectedMap.get("Cat_id") ,selectedMap.get("sub_id"),selectedMap.get("size_id"),selectedMap.get("mall_id"),selectedMap.get("shop_id") , lastId);

        call.enqueue(new CallbackWithRetry<ApiResponse<List<Product>>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<List<Product>>> call, Response<ApiResponse<List<Product>>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                    return;
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    products.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Product>>> call, Throwable t) {
                onFailure(t);
            }
        });


     }
    private void getSizeListByPCategory(Context context, int id) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Size>>> call = apiService.getSizeByPCategory(Constants.API_KEY, id);

        call.enqueue(new CallbackWithRetry<ApiResponse<List<Size>>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<List<Size>>> call, Response<ApiResponse<List<Size>>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    sizes.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Size>>> call, Throwable t) {
                onFailure(t);
            }
        });


    }
    private  void  getProductsListByMall (Context context ,int  mall_id , int lastId ) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Product>>> call = apiService.getProductByMall(Constants.API_KEY, mall_id , lastId);
         call.enqueue(new CallbackWithRetry<ApiResponse<List<Product>>>(call,context,3) {
             @Override
             public void onResponse(Call<ApiResponse<List<Product>>> call, Response<ApiResponse<List<Product>>> response) {
                 if (!response.isSuccessful()){
                     ProgressDialog.getInstance().cancel();
                     Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                     return;
                 }
                 ProgressDialog.getInstance().cancel();
                 if (response.body().getData() != null)
                     productsByMall.postValue(response.body().getData());
             }

             @Override
             public void onFailure(Call<ApiResponse<List<Product>>> call, Throwable t) {
                 onFailure(t);
             }
         });

    }

    private  void  getProductsListByShop (Context context ,int  shop_id  , int lastId ) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Product>>> call = apiService.getProductByShop(Constants.API_KEY, shop_id ,lastId);

        call.enqueue(new CallbackWithRetry<ApiResponse<List<Product>>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<List<Product>>> call, Response<ApiResponse<List<Product>>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                    return;
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    productsByShop.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Product>>> call, Throwable t) {
                onFailure(t);
            }
        });

    }

    private void getSlidersList(Context context) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Offer>>> call = apiService.getSliders(Constants.API_KEY);
        call.enqueue(new CallbackWithRetry<ApiResponse<List<Offer>>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<List<Offer>>> call, Response<ApiResponse<List<Offer>>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                    return;
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    sliders.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Offer>>> call, Throwable t) {
                onFailure(t);
            }
        });
    }

    private void getShopsListByMall(Context context ,int  mall_id )
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Mall>>> call = apiService.getShopByMall(Constants.API_KEY ,mall_id);

        call.enqueue(new CallbackWithRetry<ApiResponse<List<Mall>>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<List<Mall>>> call, Response<ApiResponse<List<Mall>>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                }
                // ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    mall.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Mall>>> call, Throwable t) {
                onFailure(t);
            }
        });

    }
    private  void  getProductListByCategory(Context context ,int  categoryId ,int lastId )
    {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Product>>> call = apiService.getProductByCategory(Constants.API_KEY ,categoryId ,lastId);

        call.enqueue(new CallbackWithRetry<ApiResponse<List<Product>>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<List<Product>>> call, Response<ApiResponse<List<Product>>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                    return;
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    products.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Product>>> call, Throwable t) {
                onFailure(t);
            }
        });

    }
    private void getMallsList(Context context) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Mall>>> call = apiService.getMalls(Constants.API_KEY);

        call.enqueue(new CallbackWithRetry<ApiResponse<List<Mall>>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<List<Mall>>> call, Response<ApiResponse<List<Mall>>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    malls.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Mall>>> call, Throwable t) {
                onFailure(t);
            }
        });

    }
    private void getOffersByShopApi(Context context, int shopId) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Offer>>> call = apiService.getOffersByShop(Constants.API_KEY ,shopId);

        call.enqueue(new CallbackWithRetry<ApiResponse<List<Offer>>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<List<Offer>>> call, Response<ApiResponse<List<Offer>>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    offersByShop.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Offer>>> call, Throwable t) {
                onFailure(t);
            }
        });
    }
    private void getOffersApi(Context context) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Offer>>> call = apiService.getOffers(Constants.API_KEY);

        call.enqueue(new CallbackWithRetry<ApiResponse<List<Offer>>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<List<Offer>>> call, Response<ApiResponse<List<Offer>>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                }

                if (response.body().getData() != null)
                    offers.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Offer>>> call, Throwable t) {
                onFailure(t);
            }
        });
    }

    private void getSubCategoryById(Context context , int sCategoryId) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Category>>> call = apiService.getPcategoryByScategory(Constants.API_KEY ,sCategoryId);

        call.enqueue(new CallbackWithRetry<ApiResponse<List<Category>>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<List<Category>>> call, Response<ApiResponse<List<Category>>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    pcCategoryBySCategory.postValue(response.body().getData().get(0));
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Category>>> call, Throwable t) {
                onFailure(t);
            }
        });
    }

    private void getCategories(Context context) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Category>>> call = apiService.getCategories(Constants.API_KEY);

        call.enqueue(new CallbackWithRetry<ApiResponse<List<Category>>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<List<Category>>> call, Response<ApiResponse<List<Category>>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    categoryList.postValue(response.body().getData());

            }

            @Override
            public void onFailure(Call<ApiResponse<List<Category>>> call, Throwable t) {
                onFailure(t);
            }
        });
    }

    private void getProductDetail (Context context ,String  productId ) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Product>>> call = apiService.getProductDetails(Constants.API_KEY, productId);
        call.enqueue(new CallbackWithRetry<ApiResponse<List<Product>>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<List<Product>>> call, Response<ApiResponse<List<Product>>> response) {
                if (!response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toast.makeText(application, R.string.unexpected_api_error,Toast.LENGTH_SHORT).show();
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    productDetails.postValue(response.body().getData());
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Product>>> call, Throwable t) {
                onFailure(t);
            }
        });

    }

    public void addOrder(Context context , ArrayList<ProductP> products , int  customer_location_id , String  order_time , String  order_price){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<Order>> call=apiService.addOrder(Constants.API_KEY ,products,customerUtils.getString(Constants.PREF_TOKEN) , customer_location_id ,order_time,order_price );
        call.enqueue(new CallbackWithRetry<ApiResponse<Order>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<Order>> call, Response<ApiResponse<Order>> response) {
                if (! response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toasty.custom(application, R.string.internet_error, null,
                            application.getResources().getColor(R.color.colorPrimary), Constants.TOAST_TIME, false, true).show();
                    return ;
                }
                ProgressDialog.getInstance().cancel();
                ((MasterClass)application).getProductList().clear();
                ((PaymentActivity)context).next();
                return;
            }

            @Override
            public void onFailure(Call<ApiResponse<Order>> call, Throwable t) {
                onFailure(t);
            }
        });


    }

    public void rateShopFun(Context context , Shop  shop , int  rate , String  notes ){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<Shop>> call=apiService.rateShop(Constants.API_KEY ,customerUtils.getString(Constants.PREF_TOKEN),shop.getId(),rate , notes);
        call.enqueue(new CallbackWithRetry<ApiResponse<Shop>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<Shop>> call, Response<ApiResponse<Shop>> response) {
                if (! response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toasty.custom(application, R.string.rate_caneled, null,
                            application.getResources().getColor(R.color.colorPrimary), Constants.TOAST_TIME, false, true).show();
                    shop.setRate_status(0);
                    ratedShop.postValue(shop);
                    return;
                }
                ProgressDialog.getInstance().cancel();
                Toast.makeText(context, context.getResources().getString(R.string.rate_completed), Toast.LENGTH_SHORT).show();
                if (response.body().getData() != null) {
                    response.body().getData().setRate_status(1);
                    ratedShop.postValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Shop>> call, Throwable t) {
                ProgressDialog.getInstance().cancel();
                shop.setRate_status(0);
                ratedShop.postValue(shop);
                Toasty.custom(application, R.string.rate_caneled, null,
                        application.getResources().getColor(R.color.colorPrimary), Constants.TOAST_TIME, false, true).show();
            }
        });



    }

    public void addFavoriteFun(Context context ,Product product ){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<Favorite>> call=apiService.addFavorite(Constants.API_KEY ,customerUtils.getString(Constants.PREF_TOKEN), product.getId());
        call.enqueue(new CallbackWithRetry<ApiResponse<Favorite>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<Favorite>> call, Response<ApiResponse<Favorite>> response) {
                if (! response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toasty.custom(application, R.string.favorite_caneled, null,
                            application.getResources().getColor(R.color.colorPrimary), Constants.TOAST_TIME, false, true).show();
                    Favorite newFavorite = new Favorite();
                    newFavorite.setFavoriteStatus(0);
                    favorite.postValue(newFavorite);
                    return;
                }
                ProgressDialog.getInstance().cancel();
                Toast.makeText(context, context.getResources().getString(R.string.favorite_completed), Toast.LENGTH_SHORT).show();
                if (response.body().getData() != null) {
                    response.body().getData().setFavoriteStatus(1);
                    response.body().getData().setMyStatus(true);
                    favorite.postValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Favorite>> call, Throwable t) {
                ProgressDialog.getInstance().cancel();
                Favorite newFavorite = new Favorite();
                newFavorite.setFavoriteStatus(0);
                favorite.postValue(newFavorite);
                Toasty.custom(application, R.string.favorite_caneled, null,
                        application.getResources().getColor(R.color.colorPrimary), Constants.TOAST_TIME, false, true).show();
            }
        });



    }

    public void deleteFavoriteFun(Context context ,Product product ){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<Customer>> call=apiService.deleteFavorite(Constants.API_KEY ,customerUtils.getString(Constants.PREF_TOKEN), product.getId());

        call.enqueue(new CallbackWithRetry<ApiResponse<Customer>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<Customer>> call, Response<ApiResponse<Customer>> response) {
                if (! response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toasty.custom(application, R.string.de_favorite_caneled, null,
                            application.getResources().getColor(R.color.colorPrimary), Constants.TOAST_TIME, false, true).show();
                    Favorite newFavorite = new Favorite();
                    newFavorite.setFavoriteStatus(0);
                    favorite.postValue(newFavorite);
                    return;
                }
                ProgressDialog.getInstance().cancel();
                Toast.makeText(context, context.getResources().getString(R.string.re_favorite_completed), Toast.LENGTH_SHORT).show();
                    Favorite newFavorite = new Favorite();
                    newFavorite.setFavoriteStatus(1);
                    newFavorite.setMyStatus(false);
                    favorite.postValue(newFavorite);

            }

            @Override
            public void onFailure(Call<ApiResponse<Customer>> call, Throwable t) {
                ProgressDialog.getInstance().cancel();
                Favorite newFavorite = new Favorite();
                newFavorite.setFavoriteStatus(0);
                favorite.postValue(newFavorite);
                Toasty.custom(application, R.string.de_favorite_caneled, null,
                        application.getResources().getColor(R.color.colorPrimary), Constants.TOAST_TIME, false, true).show();
            }
        });



    }
    private void getSizesByProductApi(Context context, int productId) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Product>>> call=apiService.getSizes(Constants.API_KEY , productId );
        call.enqueue(new CallbackWithRetry<ApiResponse<List<Product>>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<List<Product>>> call, Response<ApiResponse<List<Product>>> response) {
                if (! response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toasty.custom(application, R.string.internet_error, null,
                            application.getResources().getColor(R.color.colorPrimary), Constants.TOAST_TIME, false, true).show();
                    return ;
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    sizesByProduct.postValue(response.body().getData());
                return;
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Product>>> call, Throwable t) {

            }
        });

    }
    private void getServicesApi(Context context) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse<List<Service>>> call=apiService.getServices(Constants.API_KEY );
        call.enqueue(new CallbackWithRetry<ApiResponse<List<Service>>>(call,context,3) {
            @Override
            public void onResponse(Call<ApiResponse<List<Service>>> call, Response<ApiResponse<List<Service>>> response) {
                if (! response.isSuccessful()){
                    ProgressDialog.getInstance().cancel();
                    Toasty.custom(application, R.string.internet_error, null,
                            application.getResources().getColor(R.color.colorPrimary), Constants.TOAST_TIME, false, true).show();
                    return ;
                }
                ProgressDialog.getInstance().cancel();
                if (response.body().getData() != null)
                    services.postValue(response.body().getData());
                return;
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Service>>> call, Throwable t) {

            }
        });


    }
}
