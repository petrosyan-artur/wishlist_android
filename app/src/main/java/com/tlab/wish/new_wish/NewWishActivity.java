package com.tlab.wish.new_wish;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.tlab.wish.R;
import com.tlab.wish.authentication.AuthActivity;

public class NewWishActivity extends MvpActivity<NewWishView, NewWishPresenter> implements NewWishView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_wish);

        presenter.onCreate();
    }

    @NonNull
    @Override
    public NewWishPresenter createPresenter() {
        return new NewWishPresenter();
    }

    @Override
    public void goToAuthentication() {
        startActivityForResult(new Intent(this, AuthActivity.class), AuthActivity.AUTH_REQUET_CODE);
    }

    @Override
    public void initNewWishActivity() {
        //TODO
    }

    @Override
    public void finishActivity() {
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == AuthActivity.AUTH_REQUET_CODE){
            presenter.onAuthActivityFinished(resultCode);
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
