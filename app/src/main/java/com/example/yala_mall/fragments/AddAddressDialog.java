package com.example.yala_mall.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.yala_mall.R;
import com.example.yala_mall.adapters.SpinnerAdapter;
import com.example.yala_mall.models.City;
import com.example.yala_mall.models.Location;
import com.example.yala_mall.utils.Utils;
import com.example.yala_mall.viewModels.DataViewModel;
import com.example.yala_mall.viewModels.LoginViewModel;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AddAddressDialog extends AppCompatDialogFragment {
    EditText  addressName;
    Spinner citiesSpinner, locationSpinner;
    SpinnerAdapter citiesSpinnerAdapter ,locationsSpinnerAdapter;
    List<City> cityList;
    Context context;
    LinearLayout errorLayout;
    Utils utils;
    private TextView headerTextView;
    int location_id=-1;
    int city_id =-1;
    DataViewModel dataViewModel;
    LoginViewModel loginViewModel;
    DeliveryInfoFragment fragment;
    int size;

    public  void  init(Context context ,DeliveryInfoFragment fragment , int size)
    {
        this.context= context;
        this.fragment =fragment;
        this.size =size;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater= getActivity().getLayoutInflater();
        View view  =inflater.inflate(R.layout.layout_address_add_dialog,null);
        builder.setView(view)
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton(getResources().getString(R.string.Add), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        assignUIReference(view);
        assignUiAction();
        return  builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        utils= Utils.getInstance(getActivity());
        final AlertDialog d = (AlertDialog)getDialog();
        if(d != null)
        {
            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    if (!checkError())
                    {
                        loginViewModel.AddCustomerLocation(getActivity() ,location_id , addressName.getText().toString());
                        d.dismiss();



                    }



                }
            });
        }
    }

    private void assignUiAction() {
        citiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                City citySelected = (City) citiesSpinner.getSelectedItem();
                if (!citySelected.getName().equals("المدينة")){
                    city_id =citySelected.getId();
                    if (!citySelected.getLocations().isEmpty()) {
                        if (!citySelected.getLocations().get(0).getName().equals("الموقع"))
                            citySelected.getLocations().add(0,new Location(-1,"الموقع"));
                        locationSpinner.setVisibility(View.VISIBLE);
                        locationsSpinnerAdapter = new SpinnerAdapter(citySelected.getLocations(),context);
                        locationSpinner.setAdapter(locationsSpinnerAdapter);
                        location_id =-1;
                        addressName.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        location_id =-1;
                        locationSpinner.setVisibility(View.GONE);
                        addressName.setVisibility(View.INVISIBLE);
                    }
                }
                else
                {
                    location_id =-1;
                    city_id =-1;
                    locationSpinner.setVisibility(View.GONE);
                    addressName.setVisibility(View.INVISIBLE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Location selectedLocation = (Location) locationSpinner.getSelectedItem();
                if (!selectedLocation.getName().equals("الموقع") )
                {
                         location_id =selectedLocation.getId();
                         addressName.setVisibility(View.VISIBLE);
                }
                else
                    {
                        location_id =-1;
                        addressName.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    private void assignUIReference(View view) {
        dataViewModel = ViewModelProviders.of(getActivity()).get(DataViewModel.class);
        loginViewModel =ViewModelProviders.of(getActivity()).get(LoginViewModel.class);
        citiesSpinner = view.findViewById(R.id.spinnerCities);
        dataViewModel.getCities(getActivity()).observe(getActivity(), new Observer<List<City>>() {
            @Override
            public void onChanged(@Nullable List<City> cities) {
                cityList =cities;
                if (!cityWordContains())
                    cityList.add(0,new City(-1,"المدينة"));
                citiesSpinnerAdapter = new SpinnerAdapter(cityList ,context);
                citiesSpinner.setAdapter(citiesSpinnerAdapter);

            }
        });
        headerTextView = view.findViewById(R.id.headerText);
        headerTextView.setText(getResources().getString(R.string.Add));
        utils= Utils.getInstance(getActivity());
        locationSpinner = view.findViewById(R.id.spinnerLocations);

        addressName = view.findViewById(R.id.address_name);

        errorLayout = view.findViewById(R.id.layout_errors);


    }

    private boolean cityWordContains()
    {

        for(City city : cityList)
        {
            if (city.getName().equals("المدينة"))
            {
                return true;
            }
        }
        return  false;
    }

    public boolean checkError()
    {
        errorLayout.removeAllViews();
        Boolean check=false;
        if (!utils.notEmpty(addressName))
        {
            if (addressName.getVisibility() ==View.VISIBLE)
            addTextError(R.string.addressEmpty);
            check=true;
        }

        if (city_id == -1)
        {
            addTextError(R.string.cityEmpty);
            check=true;
        }
        if (location_id == -1)
        {
            if (locationSpinner.getVisibility() ==View.VISIBLE)
              addTextError(R.string.locationEmpty);
            check=true;
        }

        return check;
    }
    private void addTextError (int errorText){

        TextView errorField = new TextView(errorLayout.getContext());
        errorField.setText(errorText);
        //errorField.setTypeface(font);
        errorField.setTextColor(getResources().getColor(R.color.color_red));
        errorLayout.addView(errorField);
    }




}

