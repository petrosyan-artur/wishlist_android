package com.tlab.wish.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.tlab.wish.R;
import com.tlab.wish.main_view_staff.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StartupActivity extends MvpActivity<StartupView, StartupPresenter> implements StartupView{

    @Bind(R.id.startup_pin_layout)
    View pinLayout;

    @Bind(R.id.startup_pin_edittext)
    EditText pinEdittext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        ButterKnife.bind(this);

        presenter.onCreate();
    }

    @NonNull
    @Override
    public StartupPresenter createPresenter() {
        return new StartupPresenter();
    }

    @Override
    public void goToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void showPinLayout() {
        pinLayout.setVisibility(View.VISIBLE);
        pinEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.chechPin(s.toString());
            }
        });
    }

}
