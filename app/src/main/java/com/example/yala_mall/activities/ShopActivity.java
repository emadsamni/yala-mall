package com.example.yala_mall.activities;

import android.app.Application;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.example.yala_mall.R;
import com.example.yala_mall.adapters.MySliderAdapter;
import com.example.yala_mall.adapters.RecyclerProductAdapter;
import com.example.yala_mall.adapters.RecyclerShopAdapter;
import com.example.yala_mall.fragments.FilterDialog;
import com.example.yala_mall.fragments.RatingDialog;
import com.example.yala_mall.interfaces.OnClickFilterButton;
import com.example.yala_mall.interfaces.OnClickRatingButton;
import com.example.yala_mall.interfaces.OnItemProductClicked;
import com.example.yala_mall.models.Offer;
import com.example.yala_mall.models.Product;
import com.example.yala_mall.models.Service;
import com.example.yala_mall.models.Shop;
import com.example.yala_mall.utils.Constants;
import com.example.yala_mall.viewModels.DataViewModel;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class ShopActivity extends AppCompatActivity implements OnItemProductClicked, OnClickFilterButton, BaseSliderView.OnSliderClickListener, OnClickRatingButton {

    DataViewModel dataViewModel;
    List<Product> productList;
    XRecyclerView productsRecyclerView;
    RecyclerProductAdapter productsAdapter;
    MaterialSearchView searchView;
    LinearLayout searchLayout;
    TextView orderCount , shopStatus;
    Application master;
    RelativeLayout cartImage;
    Button filterButton, filterCancelButton;
    TextView pageTitle , shopName;
    int shopId;
    SliderLayout mDemoSlider;
    Shop shop;
    RelativeLayout rootRelativeLayout;
    LinearLayout filterLayout ,shopLayout;
    RelativeLayout slideLayout;

    int productsCycle;
    boolean filter_status =false;
    HashMap<String, Integer> gSpinnerMap;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        assignUIReference();
        assignAction();
        Intent intent = getIntent();
        shop = (Shop)intent.getSerializableExtra("shop_id");
        shopId = shop.getId();
        pageTitle.setText(shop.getName());
        shopName.setText(shop.getName());
        shopStatus.setText(shop.getShop_status().getName());
        if (shop.getShop_status_id() == 2)
        {
            shopStatus.setTextColor(getResources().getColor(R.color.green));
        }
        dataViewModel.getServices(this).observe(this, new Observer<List<Service>>() {
            @Override
            public void onChanged(@Nullable List<Service> services) {
                 if (services.get(0).getActive()== 1)
                 {
                     ratingBar.setVisibility(View.VISIBLE);

                 }
                getOffers(shop.getId());
            }
        });

        shop.setMyRate(0);
        setRatingBar();

    }

    private void getOffers(int shopId) {

        dataViewModel.getOffersByShop(this ,shopId).observe(this, new Observer<List<Offer>>() {
            @Override
            public void onChanged(@Nullable List<Offer> offers) {
                if (!offers.isEmpty())
                    assignSlider(offers);
                else
                    slideLayout.setVisibility(View.GONE);
                    getProduct(shopId ,true);


            }
        });
    }


    private void assignSlider(List<Offer> offers ){
        mDemoSlider = findViewById(R.id.slider_offers);

        HashMap<String,String> file_maps = new HashMap<>();
        for (Offer offer : offers)
            file_maps.put("offer"+offer.getId(), Constants.IMG_URL +offer.getImage());

        int i  =0;
        for(String name : file_maps.keySet()){
            MySliderAdapter textSliderView = new MySliderAdapter(this,offers.get(i));
            // initialize a SliderLayout
            textSliderView
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(ShopActivity.this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
            i++;
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Fade);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        //mDemoSlider.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));
    }


    private void assignUIReference() {
        dataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        productsRecyclerView= findViewById(R.id.product_recycler_view);
        ratingBar= findViewById(R.id.ratingBar);
        searchView = findViewById(R.id.search_view);
        searchLayout = findViewById(R.id.linearLayout_search);
        orderCount = findViewById(R.id.cart_number);
        shopStatus = findViewById(R.id.status);
        filterButton =   findViewById(R.id.filter_button);
        cartImage = findViewById(R.id.linearLayout_cart);
        filterCancelButton = findViewById(R.id.filter_cancel_button);
        slideLayout = findViewById(R.id.slide_layout);
        changeCartCount();
        pageTitle = findViewById(R.id.page_title);
        shopName = findViewById(R.id.shop_name);
        filterLayout = (LinearLayout) findViewById(R.id.filter_Layout);
        shopLayout = (LinearLayout) findViewById(R.id.shop_layout);
        productList = new ArrayList<>();
        productsAdapter = new RecyclerProductAdapter(productList, ShopActivity.this, ShopActivity.this);
        productsRecyclerView.setAdapter(productsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ShopActivity.this);
        layoutManager = new GridLayoutManager(ShopActivity.this, 2);
        layoutManager.setItemPrefetchEnabled(false);

        productsRecyclerView.setLayoutManager(layoutManager);
        productsRecyclerView.setPullRefreshEnabled(false);
        productsCycle =0;
        rootRelativeLayout = (RelativeLayout) findViewById(R.id.root_layout);
        final ViewTreeObserver observer = rootRelativeLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

              productsRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, rootRelativeLayout.getHeight() - (searchView.getHeight()) -shopLayout.getHeight() -filterLayout.getHeight() ));

            }
        });




    }


    private void setRatingBar()
    {

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                RatingDialog.getInstance(ShopActivity.this,ShopActivity.this,ratingBar.getRating() ,shop).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void assignAction() {


        productsRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                productsCycle =0;
                productList =new ArrayList<>();
                if (filter_status )
                  getProductByFilter(true);
                    else
                getProduct(shop.getId(),true);
            }

            @Override
            public void onLoadMore() {
                if (filter_status )
                    getProductByFilter(false);
                else
                    getProduct(shop.getId() ,false);
            }
        });
        cartImage.setOnClickListener(this::setOnClickCartImage);

        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.showSearch(true);
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startActivity(new Intent(ShopActivity.this,SearchActivity.class).putExtra("shopId",String.valueOf(shopId)).putExtra("name",query));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
            }

            @Override
            public void onSearchViewClosed() {

            }
        });

        filterButton.setOnClickListener(this::onClickFilterButton);
        filterCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter_status= false;
                productsCycle=0;
                getProduct(shopId,true);
                filterCancelButton.setVisibility(View.GONE);
            }
        });
    }

    private void onClickFilterButton(View view){
        FilterDialog.getInstance(this,this).show();
    }

    private void getProduct(int shopId , boolean state) {
        if (state) {
            dataViewModel.getProductsByShop(this, shopId, 0).observe(this, new Observer<List<Product>>() {
                @Override
                public void onChanged(@Nullable List<Product> products) {
                    productList= products;
                    productsAdapter = new RecyclerProductAdapter(products, ShopActivity.this, ShopActivity.this);
                    productsRecyclerView.setAdapter(productsAdapter);
                    productsRecyclerView.refreshComplete();

                }
            });
        }
        else {
            productsCycle = productsCycle+10;
            dataViewModel.getProductsByShop(this, shopId, productsCycle).observe(this, new Observer<List<Product>>() {
                @Override
                public void onChanged(@Nullable List<Product> products) {
                    if (!products.isEmpty()) {
                        productList.addAll(products);
                        productsRecyclerView.notifyItemInserted(productList,productList.size()-1);
                    }
                    productsRecyclerView.loadMoreComplete();
                }
            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeCartCount();
    }

    @Override
    public void onProductClick(Product product) {
        startActivity(new Intent(this,ProductDetailsActivity.class).putExtra("productId",String.valueOf(product.getId())).putExtra("product",product));
    }

    private void changeCartCount(){
        master = (MasterClass) getApplication();
        if (!((MasterClass) master).getProductList().isEmpty())
            orderCount.setText(String.valueOf(((MasterClass) master).getProductList().size()));
        else
        {
            orderCount.setText("0");
        }
    }

    private void setOnClickCartImage(View view){
        startActivity(new Intent(ShopActivity.this,CartActivity.class));
    }

    @Override
    public void onFilterButtonClicked(HashMap<String, Integer> spinnerMap) {
        if (spinnerMap.size()!=0) {
            gSpinnerMap = spinnerMap;
            gSpinnerMap.put("shop_id", shopId);
            //productList.clear();
            productsCycle = 0;
            filter_status = true;
            getProductByFilter(true);
        }

    }
    public  void  getProductByFilter(boolean stat)
    {
            if (stat) {
                dataViewModel.getFilter(this, gSpinnerMap, 0).observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(@Nullable List<Product> products) {
                        productList= products;
                        productsAdapter = new RecyclerProductAdapter(products, ShopActivity.this, ShopActivity.this);
                        productsRecyclerView.setAdapter(productsAdapter);
                        productsRecyclerView.refreshComplete();
                        filterCancelButton.setVisibility(View.VISIBLE);
                    }
                });
            }
            else
            {
                productsCycle = productsCycle+10;
                dataViewModel.getFilter(this, gSpinnerMap, productsCycle).observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(@Nullable List<Product> products) {
                        if (!products.isEmpty()) {
                            productList.addAll(products);
                            productsRecyclerView.notifyItemInserted(productList,productList.size()-1);
                        }
                        productsRecyclerView.loadMoreComplete();
                    }
                });
            }


    }
    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public void onRatingButtonClicked(String notes, int rate , int oldRate) {
        if (rate == -1)
        {
            ratingBar.setOnRatingBarChangeListener(null);
            ratingBar.setRating(oldRate);
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    RatingDialog.getInstance(ShopActivity.this,ShopActivity.this ,ratingBar.getRating() ,shop).show();
                }
            });
            return;

        }
        else
        {
            ratingBar.setOnRatingBarChangeListener(null);
            dataViewModel.rateShop(this ,shop,rate ,notes).observe(this, new Observer<Shop>() {
                @Override
                public void onChanged(@Nullable Shop shop1) {
                    if (shop1.getRate_status()==1) {
                        shop.setMyRate(rate);
                        ratingBar.setRating(rate);
                    }
                   else
                       ratingBar.setRating(oldRate);
                    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                            RatingDialog.getInstance(ShopActivity.this,ShopActivity.this ,ratingBar.getRating() ,shop).show();

                        }
                    });
                 ;
                }
            });
        }


    }
}
