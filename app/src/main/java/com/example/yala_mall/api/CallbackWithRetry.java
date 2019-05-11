package com.example.yala_mall.api;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.yala_mall.activities.MainActivity;
import com.example.yala_mall.R;
import com.example.yala_mall.utils.ProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;

public abstract class CallbackWithRetry<T> implements Callback<T> {

    private int maxRetries;
    private final String TAG = CallbackWithRetry.class.getSimpleName();
    private final Call<T> call;
    private int retryCount = 0;
    private Context context;

    public CallbackWithRetry(Call<T> call, Context context, int maxRetries) {
        this.call = call;
        this.maxRetries = maxRetries;
        this.context = context;
    }

    public void onFailure(Throwable t) {
        if (retryCount++ < maxRetries) {
            retry();
        }


        if (retryCount == maxRetries) {
            ProgressDialog.getInstance().cancel();
                ViewGroup main = null;

                LayoutInflater inflater = LayoutInflater.from(context);
                View layout = inflater.inflate(R.layout.snack_bar_layout, null );

                ViewGroup viewGroup = (ViewGroup) ((ViewGroup) ((Activity)context)
                    .findViewById(android.R.id.content)).getChildAt(0);

                if (context instanceof MainActivity)
                    main = (ViewGroup) viewGroup.getChildAt(0);
                final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                if (context instanceof MainActivity)
                    main.addView(layout,params);
                else
                    viewGroup.addView(layout,params);

                Button button = layout.findViewById(R.id.snack_bar_action);
                button.setOnClickListener(v -> {
                    ((ViewGroup) layout.getParent()).removeView(layout);
                        ProgressDialog.getInstance().show(context);

                    retryCount=0;
                    retry();
                });


        }
    }

    private void retry() {
        call.clone().enqueue(this);
    }
}
