package com.example.yala_mall.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.yala_mall.R;
import com.example.yala_mall.activities.MallActivity;
import com.example.yala_mall.interfaces.OnClickFilterButton;
import com.example.yala_mall.models.Category;
import com.example.yala_mall.models.Size;
import com.example.yala_mall.viewModels.DataViewModel;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;



import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class FilterDialog   {
    private Activity context;
    private AlertDialog dialog;
    private OnClickFilterButton listener;
    private Spinner jobCategorySpinner,jobTypeSpinner,citySpinner,locationSpinner;
    private HashMap<String,Integer> spinnerSelectedMap;
    private List<Category> categoryList;
    private List<Category> subCategoryList;
    private List<Size> sizeList;
    int temp;
    private int screenSize;
    DataViewModel dataViewModel;
    LinearLayout sizeLinearLayout ,catLinearLayout ,subCatLinearLayout;
    LinearLayout sizeButton , catButton ,subCatButton;
    TextView sizeText , catText ,subCatText;

    private FilterDialog(Activity context, OnClickFilterButton listener) {
        this.context = context;
        this.listener = listener;
        spinnerSelectedMap = new HashMap<>();
        screenSize = context.getResources().getDisplayMetrics().densityDpi;
    }

    public static FilterDialog getInstance(Activity context,OnClickFilterButton listener){
        return new FilterDialog(context ,listener);
    }

    public void show(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        assignUIReferenceDialog(builder);
        dialog = builder.create();
        dialog.show();
        getCategories();
        assignAction();
        //designDialog();
    }

    private void getCategories() {
        dataViewModel.getCategoryList(context).observe((MallActivity)context, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                 categoryList = categories;
            }
        });
    }


    private void assignUIReferenceDialog (AlertDialog.Builder builder) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        dataViewModel = ViewModelProviders.of((MallActivity) context).get(DataViewModel.class);
        View view = inflater.inflate(R.layout.layout_dialog_filter, null, false);
        builder.setPositiveButton("تطبيق", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (spinnerSelectedMap.containsKey("Cat_id"))
                    spinnerSelectedMap.put("Cat_id" ,categoryList.get(spinnerSelectedMap.get("Cat_id")).getId());
                if (spinnerSelectedMap.containsKey("sub_id"))
                spinnerSelectedMap.put("sub_id" ,subCategoryList.get(spinnerSelectedMap.get("sub_id")).getId());
                if (spinnerSelectedMap.containsKey("size_id"))
                spinnerSelectedMap.put("size_id" ,sizeList.get(spinnerSelectedMap.get("size_id")).getId());
                listener.onFilterButtonClicked(spinnerSelectedMap);

            }
        });

        builder.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        sizeLinearLayout =view.findViewById(R.id.size_layout);
        catLinearLayout =view.findViewById(R.id.cat_layout);
        subCatLinearLayout =view.findViewById(R.id.sub_cat_layout);
        sizeLinearLayout.setVisibility(View.GONE);
        subCatLinearLayout.setVisibility(View.GONE);

        catButton =view.findViewById(R.id.cat_cat_filter);
        sizeButton =view.findViewById(R.id.size_filter);
        subCatButton =view.findViewById(R.id.sub_cat_filter);

        sizeText =view.findViewById(R.id.size_text_type);
        catText =view.findViewById(R.id.cat_text_type);
        subCatText =view.findViewById(R.id.sub_cat_text_type);


        builder.setView(view);
    }

    private  void assignAction()
    {
        catButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp=0;
                AlertDialog.Builder adb = new AlertDialog.Builder(context);
                String[] categories = new String[categoryList.size()];
                for (int i=0;i<categoryList.size();i++)
                {
                    categories[i] =categoryList.get(i).getName();
                }
                adb.setSingleChoiceItems(categories, 0, new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        temp = which;

                    }
                });
                adb.setNegativeButton("الغاء", null);

                adb.setPositiveButton("تعيين", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        spinnerSelectedMap.put("Cat_id" ,temp);
                         catText.setText(categoryList.get(spinnerSelectedMap.get("Cat_id")).getName());
                         subCatLinearLayout.setVisibility(View.VISIBLE);
                        sizeLinearLayout.setVisibility(View.GONE);
                        spinnerSelectedMap.remove("sub_id");
                        spinnerSelectedMap.remove("size_id");
                        subCatText.setText("النوع الفرعي");
                        dataViewModel.getPcCategoryBySCategory(context ,categoryList.get(spinnerSelectedMap.get("Cat_id")).getId()).observe((MallActivity) context, new Observer<Category>() {
                            @Override
                            public void onChanged(@Nullable Category category) {
                                subCategoryList =category.getP_category();
                            }
                        });

                    }});
                adb.setTitle(context.getResources().getString(R.string.select_cat));
                adb.show();
            }
        });

        subCatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp=0;
                AlertDialog.Builder adb = new AlertDialog.Builder(context);
                String[] categories = new String[subCategoryList.size()];
                for (int i=0;i<subCategoryList.size();i++)
                {
                    categories[i] =subCategoryList.get(i).getName();
                }
                adb.setSingleChoiceItems(categories, 0, new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        temp = which;

                    }
                });
                adb.setNegativeButton("الغاء", null);

                adb.setPositiveButton("تعيين", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        spinnerSelectedMap.put("sub_id" ,temp);
                        subCatText.setText(subCategoryList.get(spinnerSelectedMap.get("sub_id")).getName());
                        sizeLinearLayout.setVisibility(View.VISIBLE);
                        spinnerSelectedMap.remove("size_id");
                        sizeText.setText("القياس");
                        dataViewModel.getSizeByPCategory(context ,subCategoryList.get(spinnerSelectedMap.get("sub_id")).getId()).observe((MallActivity) context, new Observer<List<Size>>() {
                            @Override
                            public void onChanged(@Nullable List<Size> sizes) {
                                sizeList =sizes;
                            }
                        });

                    }});
                adb.setTitle(context.getResources().getString(R.string.select_cat));
                adb.show();



            }
        });

        sizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = 0;
                AlertDialog.Builder adb = new AlertDialog.Builder(context);
                String[] categories = new String[sizeList.size()];
                for (int i = 0; i < sizeList.size(); i++) {
                    categories[i] = sizeList.get(i).getName();
                }
                adb.setSingleChoiceItems(categories, 0, new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        temp = which;

                    }
                });
                adb.setNegativeButton("الغاء", null);

                adb.setPositiveButton("تعيين", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        spinnerSelectedMap.put("size_id", temp);
                        sizeText.setText(sizeList.get(spinnerSelectedMap.get("size_id")).getName());

                    }
                });
                adb.setTitle(context.getResources().getString(R.string.select_cat));
                adb.show();
            }
        });
    }
}
