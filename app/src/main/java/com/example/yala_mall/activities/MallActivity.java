package com.example.yala_mall.activities;

import android.app.Application;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.yala_mall.R;
import com.example.yala_mall.adapters.MySliderAdapter;
import com.example.yala_mall.adapters.RecyclerCategoryAdapter;
import com.example.yala_mall.adapters.RecyclerMallAdapter;
import com.example.yala_mall.adapters.RecyclerProductAdapter;
import com.example.yala_mall.adapters.RecyclerShopAdapter;
import com.example.yala_mall.fragments.FilterDialog;
import com.example.yala_mall.interfaces.OnClickFilterButton;
import com.example.yala_mall.interfaces.OnItemProductClicked;
import com.example.yala_mall.interfaces.OnItemRecyclerClicked;
import com.example.yala_mall.models.Category;
import com.example.yala_mall.models.Mall;
import com.example.yala_mall.models.Offer;
import com.example.yala_mall.models.Product;
import com.example.yala_mall.models.Shop;
import com.example.yala_mall.utils.Constants;
import com.example.yala_mall.viewModels.DataViewModel;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MallActivity extends AppCompatActivity implements OnItemRecyclerClicked, OnClickFilterButton, OnItemProductClicked, BaseSliderView.OnSliderClickListener {

    DataViewModel dataViewModel;
    RecyclerView recyclerView;
    RecyclerShopAdapter adapter;
    List<Product> productList;
    XRecyclerView productsRecyclerView;
    RecyclerProductAdapter productsAdapter;
    LinearLayout searchLayout;
    MaterialSearchView searchView;
    Mall mall;
    Button filterButton, filterCancelButton;
    TextView orderCount;
    Application master;
    RelativeLayout cartImage;
    TextView pageTitle;
    SliderLayout mDemoSlider;
    RelativeLayout rootRelativeLayout;
    LinearLayout filterLayout;
    RelativeLayout slideLayout;
    boolean filter_status =false;
    HashMap<String, Integer> gSpinnerMap;
    int productsCycle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall);
        assignUIReference();
        assignAction();
        Intent intent = getIntent();
        mall = (Mall) intent.getSerializableExtra("mall_id");
        pageTitle.setText(mall.getName());
        getOffers(mall.getId());

        // assignSlider();


    }

    private void getOffers(int mallId) {

        dataViewModel.getOffersByMall(this ,mallId).observe(this, new Observer<Mall>() {
            @Override
            public void onChanged(@Nullable Mall myMall) {

                List<Offer> offers = new ArrayList<>();
                for (int i=0;i<myMall.getShop().size();i++)
                {
                    if ( !myMall.getShop().get(i).getOffers().isEmpty())
                    {
                        for (int j=0;j<myMall.getShop().get(i).getOffers().size();j++)
                        {
                            offers.add(myMall.getShop().get(i).getOffers().get(j));
                        }
                    }
                }
                if (!offers.isEmpty())
                    assignSlider(offers);
                else
                    slideLayout.setVisibility(View.GONE);
                   getShops(mall.getId());

            }
        });
    }

    private void assignUIReference() {
        dataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        recyclerView = findViewById(R.id.mall_recycler_view);
        productsRecyclerView = findViewById(R.id.product_recycler_view);
        searchView = findViewById(R.id.search_view);
        searchLayout = findViewById(R.id.linearLayout_search);
        filterButton = findViewById(R.id.filter_button);
        orderCount = findViewById(R.id.cart_number);
        cartImage = findViewById(R.id.linearLayout_cart);
        filterCancelButton = findViewById(R.id.filter_cancel_button);
         slideLayout = findViewById(R.id.slide_layout);
        changeCartCount();
        pageTitle = findViewById(R.id.page_title);
        filterLayout = (LinearLayout) findViewById(R.id.filter_Layout);
        productList = new ArrayList<>();
        productsAdapter = new RecyclerProductAdapter(productList, MallActivity.this, MallActivity.this);
        productsRecyclerView.setAdapter(productsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MallActivity.this);
        layoutManager = new GridLayoutManager(MallActivity.this, 2);
        layoutManager.setItemPrefetchEnabled(false);
        productsRecyclerView.setLayoutManager(layoutManager);
        productsCycle =0;
        rootRelativeLayout = (RelativeLayout) findViewById(R.id.root_layout);
        final ViewTreeObserver observer = rootRelativeLayout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                searchView.getHeight();
                productsRecyclerView.setLayoutParams(new LinearLayout.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, rootRelativeLayout.getHeight() - (searchView.getHeight()) - recyclerView.getHeight() - filterLayout.getHeight()));

            }
        });



    }

    private void assignAction() {

        productsRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                productsCycle =0;
                productList =new ArrayList<>();
                if (filter_status )
                    getProductByFilter(true);
                else
                    getProduct(mall.getId() ,true);
            }

            @Override
            public void onLoadMore() {
                if (filter_status )
                    getProductByFilter(false);
                else
                    getProduct(mall.getId() ,false);
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
                startActivity(new Intent(MallActivity.this, SearchActivity.class).putExtra("mallId", String.valueOf(mall.getId())).putExtra("name", query));
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
                getProduct(mall.getId(),true);
                filterCancelButton.setVisibility(View.GONE);
            }
        });
    }


    private void onClickFilterButton(View view) {
        FilterDialog.getInstance(this, this).show();
    }


    private void getShops(int mallId) {

        dataViewModel.getShopsByMall(this, mallId).observe(this, new Observer<List<Mall>>() {
            @Override
            public void onChanged(@Nullable List<Mall> malls) {
                adapter = new RecyclerShopAdapter(malls.get(0).getShop(), MallActivity.this, MallActivity.this);
                recyclerView.setAdapter(adapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(MallActivity.this);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(layoutManager);
                getProduct(mall.getId() ,true);
            }
        });
    }

    private void getProduct(int mallId , boolean state) {
        if (state) {
            dataViewModel.getProductsByMall(this, mallId ,0).observe(this, new Observer<List<Product>>() {
                @Override
                public void onChanged(@Nullable List<Product> products) {
                    productList= products;
                    productsAdapter = new RecyclerProductAdapter(products, MallActivity.this, MallActivity.this);
                    productsRecyclerView.setAdapter(productsAdapter);
                    productsRecyclerView.refreshComplete();

                }
            });
        }
        else
        {
            productsCycle = productsCycle+10;
            dataViewModel.getProductsByMall(this, mallId ,productsCycle).observe(this, new Observer<List<Product>>() {
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
    public void onClickedRecyclerItem(Category category) {

    }

    @Override
    public void onClickedRecyclerMallItem(Mall current) {

    }

    @Override
    public void onClickedRecyclerShopItem(Shop current) {
        Intent intent = new Intent(MallActivity.this, ShopActivity.class);
        intent.putExtra("shop_id", current);
        startActivity(intent);
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
    public void onFilterButtonClicked(HashMap<String, Integer> spinnerMap) {

        if (spinnerMap.size()!=0) {
            gSpinnerMap = spinnerMap;
            gSpinnerMap.put("mall_id", mall.getId());
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
                    productsAdapter = new RecyclerProductAdapter(products, MallActivity.this, MallActivity.this);
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
    public void onProductClick(Product product) {
        startActivity(new Intent(this, ProductDetailsActivity.class).putExtra("productId", String.valueOf(product.getId())).putExtra("product", product));
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeCartCount();
    }

    private void changeCartCount() {
        master = (MasterClass) getApplication();
        if (!((MasterClass) master).getProductList().isEmpty())
            orderCount.setText(String.valueOf(((MasterClass) master).getProductList().size()));
        else
        {
            orderCount.setText("0");
        }
    }

    private void setOnClickCartImage(View view) {
        startActivity(new Intent(MallActivity.this, CartActivity.class));
    }


    private void assignSlider(List<Offer> offers) {
        mDemoSlider = findViewById(R.id.slider_offers);


        HashMap<String, String> file_maps = new HashMap<>();
        for (Offer offer : offers)
            file_maps.put("offer" + offer.getId(), Constants.IMG_URL + offer.getImage());

        int i = 0;
        for (String name : file_maps.keySet()) {
            MySliderAdapter textSliderView = new MySliderAdapter(this, offers.get(i));
            // initialize a SliderLayout
            textSliderView
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(MallActivity.this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
            i++;
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Fade);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        //mDemoSlider.setCustomIndicator((PagerIndicator) findViewById(R.id.custom_indicator));
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

}
