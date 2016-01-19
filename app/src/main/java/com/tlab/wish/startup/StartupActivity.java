package com.tlab.wish.startup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.tlab.wish.R;
import com.tlab.wish.main_view_staff.MainActivity;
import com.tlab.wish.pin_staff.CheckPinActivity;

import butterknife.ButterKnife;

public class StartupActivity extends MvpActivity<StartupView, StartupPresenter> implements StartupView{

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
    public void goToPinActivity() {
        startActivity(new Intent(this, CheckPinActivity.class));
    }

}
