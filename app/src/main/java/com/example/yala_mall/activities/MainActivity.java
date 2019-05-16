package com.example.yala_mall.activities;

import android.app.Application;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.yala_mall.R;
import com.example.yala_mall.adapters.RecyclerCategoryAdapter;
import com.example.yala_mall.adapters.RecyclerMallAdapter;
import com.example.yala_mall.fragments.MessageDialog;
import com.example.yala_mall.helps.CustomerUtils;
import com.example.yala_mall.interfaces.OnItemRecyclerClicked;
import com.example.yala_mall.models.Category;
import com.example.yala_mall.models.Mall;
import com.example.yala_mall.models.Offer;
import com.example.yala_mall.models.Product;
import com.example.yala_mall.models.Shop;
import com.example.yala_mall.utils.Constants;
import com.example.yala_mall.viewModels.DataViewModel;
import com.example.yala_mall.viewModels.SearchViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, OnItemRecyclerClicked {

    DataViewModel dataViewModel;
    SliderLayout mDemoSlider;
    RecyclerView recyclerView;
    RecyclerCategoryAdapter adapter;
    RecyclerView mallsRecyclerView;
    RecyclerMallAdapter mallsAdapter;
    MaterialSearchView searchView;
    Toolbar toolbarSearch;
    LinearLayout linearSearch;
    TextView orderCount;
    Application master;
    RelativeLayout cartImage;
    NavigationView navigationView;
    CustomerUtils customerUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignUIReference();
        assignAction();
        getOffers();
        getCategories();
        navigation_config();
        getMalls();
    }

    private void assignUIReference(){
        dataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);
        recyclerView = findViewById(R.id.recycler_view);
        mallsRecyclerView = findViewById(R.id.mall_recycler_view);
        searchView = findViewById(R.id.search_view);
        toolbarSearch = findViewById(R.id.toolbar_search);
        toolbarSearch.setTitle("");
        orderCount = findViewById(R.id.cart_number);
        cartImage = findViewById(R.id.linearLayout_cart);
        setSupportActionBar(toolbarSearch);
        linearSearch = findViewById(R.id.linear_search);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        customerUtils =CustomerUtils.getInstance(this);

        // set counter for cart
        changeCartCount();
    }

    private void assignAction(){
        cartImage.setOnClickListener(this::setOnClickCartImage);

        linearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.showSearch(true);
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startActivity(new Intent(MainActivity.this,SearchActivity.class).putExtra("name",query));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
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
                //Do some magic
            }
        });
    }

    private void getOffers(){
        dataViewModel.getOffers(this).observe(this, new Observer<List<Offer>>() {
            @Override
            public void onChanged(@Nullable List<Offer> offers) {
                if (!offers.isEmpty())
                    assignSlider(offers);
            }
        });
    }

    private void assignSlider(List<Offer> offers){
        mDemoSlider = findViewById(R.id.slider_offers);

        HashMap<String,String> file_maps = new HashMap<>();
         for (Offer offer : offers)
            file_maps.put("offer"+offer.getId(), Constants.IMG_URL +offer.getImage());


        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(MainActivity.this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Fade);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    public void getCategories() {
       dataViewModel.getCategoryList(this).observe(this, new Observer<List<Category>>() {
           @Override
           public void onChanged(@Nullable List<Category> categories) {
               adapter = new RecyclerCategoryAdapter(categories ,MainActivity.this ,MainActivity.this );
               recyclerView.setAdapter(adapter);
               LinearLayoutManager layoutManager =new LinearLayoutManager( MainActivity.this);
               layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
               recyclerView.setLayoutManager(layoutManager);
           }
       });
    }


    public void getMalls() {
        dataViewModel.getMalls(this).observe(this, new Observer<List<Mall>>() {
            @Override
            public void onChanged(@Nullable List<Mall> malls) {
                mallsAdapter = new RecyclerMallAdapter(malls ,MainActivity.this ,MainActivity.this);
                mallsRecyclerView.setAdapter(mallsAdapter);
                LinearLayoutManager layoutManager =new LinearLayoutManager( MainActivity.this);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mallsRecyclerView.setLayoutManager(layoutManager);
            }
        });
    }

    @Override
    public void onClickedRecyclerItem(Category category) {
        Intent intent = new Intent(MainActivity.this, ProductsActivity.class);
        intent.putExtra("category" ,category.getId());
       startActivity(intent);
    }

    @Override
    public void onClickedRecyclerMallItem(Mall current) {
        Intent intent = new Intent(MainActivity.this, MallActivity.class);
        intent.putExtra("mall_id" ,current.getId());
        startActivity(intent);
    }

    @Override
    public void onClickedRecyclerShopItem(Shop current) {

    }

    @Override
    public void onBackPressed() {

        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        }else {
          if (!((MasterClass)getApplication()).getProductList().isEmpty())
             MessageDialog.getInstance(this,getResources().getString(R.string.exit_app)).show();
          else
              super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeCartCount();
    }

    private void changeCartCount(){
        master = (MasterClass) getApplication();
        if (!((MasterClass) master).getProductList().isEmpty())
            orderCount.setText(String.valueOf(((MasterClass) master).getProductList().size()));
    }

    private void setOnClickCartImage(View view){
        startActivity(new Intent(MainActivity.this,CartActivity.class));
    }

    void navigation_config()
    {
        View headerLayout = navigationView.getHeaderView(0);
        Button signInButton= headerLayout.findViewById(R.id.sign_in);
        if (!customerUtils.isFound(Constants.PREF_TOKEN)) {
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(intent);

                }
            });
        }
        else {
            signInButton.setText("خروج ");
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   customerUtils.clear();
                   signInButton.setText("دخول");
                   navigation_config();

                }
            });

        }

    }
}
