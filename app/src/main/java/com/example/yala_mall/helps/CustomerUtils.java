package com.example.yala_mall.helps;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;

import com.example.yala_mall.R;
import com.example.yala_mall.models.Product;
import com.example.yala_mall.utils.Constants;
import com.example.yala_mall.utils.LocaleHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;


public class CustomerUtils {
    private static CustomerUtils prefManager;
    private SharedPreferences sharedPref;
    private Context context;

    private CustomerUtils(Context context) {
        sharedPref = context.getSharedPreferences(Constants.APP_SHARED_PREFERENCE_KEY, context.MODE_PRIVATE);
    }

    public static CustomerUtils getInstance(Context context) {
        if (prefManager == null) {
            prefManager = new CustomerUtils(context);
        }
        prefManager.context = context;
        return prefManager;
    }

    public void addString(String key, String value) {
        value = EncryptionManager.getInstance().encrypt(value);
        sharedPref.edit().putString(key, value).apply();
    }

    public void addInt(String key, int value) {
        addString(key, value + "");
    }

    public void addLong(String key, long value) {
        addString(key, value + "");
    }
    public void addBoolean(String key, boolean value) {
        addString(key, Boolean.toString(value));
    }

    public String getString(String key) {
        String value = sharedPref.getString(key, null);
        if (value != null) {
            return EncryptionManager.getInstance().decrypt(value);
        }
        return null;
    }

    public Long getLong(String key) {
        String strValue = getString(key);
        if (strValue == null) {
            return 0L;
        }
        return Long.parseLong(strValue);    }

    public int getInt(String key) {
        String strValue = getString(key);
        if (strValue == null) {
            return 0;
        }
        return Integer.parseInt(strValue);
    }

    public boolean getBoolean(String key) {
        String strValue = getString(key);
        if (strValue == null) {
            return false;
        }
        return Boolean.parseBoolean(strValue);
    }

    public void remove(String key) {
        sharedPref.edit().remove(key).apply();
    }

    public void clear() {
        sharedPref.edit().clear().apply();
        sharedPref.edit().clear().commit();
    }

    public boolean isFound(String key) {
        return sharedPref.contains(key);
    }

    public  void  setLocalConfigration ()
    {
        if (isFound(Constants.PREF_LANG))
        {
            Configuration configuration = context.getResources().getConfiguration();
            configuration.setLayoutDirection(new Locale(getString(Constants.PREF_LANG)));

            LocaleHelper.setLocale(context,getString(Constants.PREF_LANG));
            context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        }
        else
        {
           addString(Constants.PREF_LANG,context.getResources().getString(R.string.local));
        }
    }
    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public void addList(String key, List<Product> products) {
        Gson gson = new Gson();
        String jsonCars = gson.toJson(products);
        Log.d("TSTS","jsonCars = " + jsonCars);


        // retrive list

        Type type = new TypeToken<List<Product>>(){}.getType();
        List<Product> carsList = gson.fromJson(jsonCars, type);
        for (Product product : carsList)
            Log.d("TSTS","product = " + product.getName());
    }

}
