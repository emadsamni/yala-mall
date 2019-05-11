package com.example.yala_mall.utils;

import android.content.Context;
import android.widget.EditText;


import com.example.yala_mall.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import es.dmoral.toasty.Toasty;


public class Utils {
    private Context mContext;


    public Utils(Context mContext) {
        this.mContext = mContext;
    }



    public static Utils getInstance(Context context){
        return new Utils(context);
    }


    boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean notEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return true;

        return false;
    }
    public boolean notEmpty(MaterialEditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return true;

        return false;
    }
    public void problemWithConnection(Context activity){
        ProgressDialog.getInstance().cancel();
        Toasty.custom(activity,R.string.internet_error,null,
                activity.getResources().getColor(R.color.colorPrimary),Constants.TOAST_TIME,false,true).show();

    }




    public void setFont(){
       // Calligrapher calligrapher = new Calligrapher(mContext);
        //calligrapher.setFont((Activity) mContext,Constants.FONT_STYLE,true);
    }
}
