package com.example.yala_mall.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.yala_mall.R;
import com.example.yala_mall.activities.StepperListener;
import com.example.yala_mall.helps.CustomerUtils;
import com.example.yala_mall.utils.Constants;
import com.example.yala_mall.utils.Utils;
import com.example.yala_mall.viewModels.LoginViewModel;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterFragment extends Fragment implements StepperListener {
    LoginViewModel loginViewModel;
    EditText phoneText;
    TextView phoneTextError ;
    CustomerUtils customerUtils;
    Utils utils;
    Boolean checkTimer = false;
    TextView timerView;

    public RegisterFragment() {
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_register, container,false);
         assignUIReference(view);
         setTimer();
        return  view;

    }

    public void assignUIReference(View view)
    {
        loginViewModel = ViewModelProviders.of(getActivity()).get(LoginViewModel.class);
        timerView =view.findViewById(R.id.timer);
        phoneText =view.findViewById(R.id.phone);
        phoneTextError =view.findViewById(R.id.phone_error);
        customerUtils = CustomerUtils.getInstance(getActivity());
        utils = Utils.getInstance(getActivity());

        phoneText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               phoneTextError.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public void onNextClicked() {
        if( !checkTimer) {
            if (isInputValidation()) {
                register(phoneText.getText().toString());
            }
        }
        else {
            Toast.makeText(getActivity(), "Wating timer", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackClicked() {

    }
    private boolean isInputValidation() {
        boolean check = true;
        if (!utils.notEmpty(phoneText)){
            phoneTextError.setText(getResources().getString(R.string.phoneEmpty));
            check =false;
        }else if(!((phoneText.getText().toString().length() == 10 ||phoneText.getText().toString().length() == 9)  &&
                isValidPhone(phoneText.getText().toString()) ) )
        {
            phoneTextError.setText(getResources().getString(R.string.phoneInvalidMessage));
            check =false;
        }

        return check;
    }
    private void register(String phone ){

        loginViewModel.registerUser(getActivity(),phone );
    }

    public static boolean isValidPhone(String phone)
    {
        String expression = "^(09|9)[0-9]{8}";
        CharSequence inputString = phone;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches())
        {
            return true;
        }
        else{
            return false;
        }
    }

    private void setTimer(){

        if (CustomerUtils.getInstance(getActivity()).getLong(Constants.PREF_TIM) != null) {
            long dd = CustomerUtils.getInstance(getActivity()).getLong(Constants.PREF_TIM);
            long timer = new Date(System.currentTimeMillis()).getTime() - CustomerUtils.getInstance(getActivity()).getLong(Constants.PREF_TIM);
            Log.d("TSTS",""+ String.valueOf(timer));
            if (timer < 300000) {
                checkTimer = true;
                timerView.setVisibility(View.VISIBLE);
                new CountDownTimer(300000-timer, 1000) {

                    public void onTick(long millisUntilFinished) {
                        int seconds = (int) (millisUntilFinished / 1000) % 60 ;
                        int minutes = (int) ((millisUntilFinished / (1000*60)) % 60);
                        timerView.setText(String.format("%d:%d",minutes,seconds));
                    }

                    public void onFinish() {
                        timerView.setVisibility(View.GONE);
                        checkTimer = false;
                    }

                }.start();
            } else{
                checkTimer = false;
                timerView.setVisibility(View.GONE);
            }

        }
    }
}
