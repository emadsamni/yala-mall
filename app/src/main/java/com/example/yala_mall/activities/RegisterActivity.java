package com.example.yala_mall.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.yala_mall.R;
import com.example.yala_mall.fragments.FinishFragment;
import com.example.yala_mall.fragments.RegisterFragment;
import com.example.yala_mall.fragments.VerificationFragment;
import com.example.yala_mall.helps.CustomerUtils;
import com.example.yala_mall.utils.Constants;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class RegisterActivity extends AppCompatActivity {
    ImageButton mNextBtn ,mSkipBtn;
    Button  mFinishBtn;
    ImageView zero, one, two;
    ImageView[] indicators;
    CoordinatorLayout mCoordinator;
    int page = 0;
    String phone="";
    StepperListener listener;
    TextView titleTextView;
    CustomerUtils customerUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        customerUtils = CustomerUtils.getInstance(this);
        customerUtils.setLocalConfigration();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
         assignUIReference();
         assignAction();
    }

    private void assignUIReference() {
        mNextBtn = (ImageButton) findViewById(R.id.intro_btn_next);
        titleTextView = (TextView) findViewById(R.id.title);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP)
            mNextBtn.setImageDrawable(
                    tintMyDrawable(ContextCompat.getDrawable(this, R.drawable.ic_chevron_right_24dp), Color.WHITE)
            );
        mSkipBtn = (ImageButton) findViewById(R.id.intro_btn_prev);
        mFinishBtn = (Button) findViewById(R.id.intro_btn_finish);
        zero = (ImageView) findViewById(R.id.intro_indicator_0);
        one = (ImageView) findViewById(R.id.intro_indicator_1);
        two = (ImageView) findViewById(R.id.intro_indicator_2);
        titleTextView = (TextView)  findViewById(R.id.title);
        mCoordinator = (CoordinatorLayout) findViewById(R.id.main_content);
        indicators = new ImageView[]{zero, one, two};
        RegisterFragment fragment = new RegisterFragment();
        loadFragment(fragment);
        updateIndicators(page);
        titleTextView.setText(getResources().getString(R.string.sign_in));
        if (customerUtils.isFound(Constants.PREF_LANG)) {
            if (customerUtils.getString(Constants.PREF_LANG).equals("ar")) {
                mNextBtn.setRotation(180);
                mSkipBtn.setRotation(0);

            }
        }

    }
    private  void assignAction()
    {
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNextClicked();
            }
        });
        mSkipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page -= 1;
                switch (page)
                {
                    case 0:
                        RegisterFragment fragment1 = new RegisterFragment();
                        loadFragment(fragment1);
                        updateIndicators(page);
                        titleTextView.setText(getResources().getString(R.string.sign_in));
                        return;
                    case 1:
                        VerificationFragment fragment2 = new VerificationFragment();
                        loadFragment(fragment2);
                        updateIndicators(page);
                        titleTextView.setText("التأكيد");
                        return;
                    case 2:
                        FinishFragment fragment3 = new FinishFragment(1);
                        loadFragment(fragment3);
                        updateIndicators(page);
                        titleTextView.setText("إنهاء");
                        return;

                }
                updateIndicators(page);
            }
        });

        mFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMain = new Intent(RegisterActivity.this, MainActivity.class);
                toMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(toMain);
                finish();
            }
        });
    }
    public void  next()
    {
        page += 1;
        updateIndicators(page);
        switch (page)
        {
            case 0:
                RegisterFragment fragment1 = new RegisterFragment();
                loadFragment(fragment1);
                updateIndicators(page);
                titleTextView.setText("تسجيل الدخول");
                return;
            case 1:
                VerificationFragment fragment2 = new VerificationFragment();
                loadFragment(fragment2);
                updateIndicators(page);
                titleTextView.setText("التأكيد");
                return;
            case 2:
                FinishFragment fragment3 = new FinishFragment(1);
                loadFragment(fragment3);
                updateIndicators(page);
                titleTextView.setText("إنهاء");
                return;
        }
    }
    public static Drawable tintMyDrawable(Drawable drawable, int color) {
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, color);
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN);
        return drawable;
    }
    void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? R.drawable.indicator_selected : R.drawable.indicator_unselected
            );
        }
        mNextBtn.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
        mFinishBtn.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
        mSkipBtn.setVisibility(position == 1 ? View.VISIBLE : View.GONE);
    }
    private  boolean loadFragment(Fragment fragment)
    {
        listener = (StepperListener) fragment;
        if (fragment  != null )
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container,fragment).commitNowAllowingStateLoss();
            return true;
        }
        return false;
    }

    public void setPhone(String phone) {

        this.phone =phone;
    }

    public String getPhone() {

       return this.phone;
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }



}
