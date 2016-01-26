package com.tlab.wish.new_wish;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.tlab.wish.R;
import com.tlab.wish.authentication.AuthActivity;
import com.tlab.wish.configs.ConfigurationManager;
import com.tlab.wish.new_wish.decorations.ColorDecorItem;
import com.tlab.wish.new_wish.decorations.DecorAdapter;
import com.tlab.wish.new_wish.decorations.DecorItem;
import com.tlab.wish.utils.ViewUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewWishActivity extends MvpActivity<NewWishView, NewWishPresenter> implements NewWishView, DecorAdapter.OnDecorItemClickListener{

    @Bind(R.id.new_wish_toolbar)
    Toolbar toolbar;

    @Bind(R.id.new_wish_decor_layout)
    View newWishDecorLayout;

    @Bind(R.id.new_wish_decor_rw)
    RecyclerView recyclerView;

    @Bind(R.id.new_wish_et)
    EditText newWishEt;

    @Bind(R.id.new_wish_progress)
    View progress;

    private DecorAdapter decorColorAdapter;
    private DecorAdapter decorPictureAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_wish);

        ButterKnife.bind(this);

        ViewUtils.configureToolbar(this, toolbar, R.drawable.toolbar_close);

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
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(llm);
    }

    @OnClick(R.id.new_wish_colors_btn)
    public void onNewWishColorsBtnClick(){
        if(decorColorAdapter == null){
            decorColorAdapter = new DecorAdapter(ColorDecorItem.getItems(
                    ConfigurationManager.getInstanse().getConfigs().getDecorations()
            ), this);
        }

        if(isDecorViewVisible()){
            hideDecorView();
        } else {
            recyclerView.setAdapter(decorColorAdapter);
            showDecorView();
        }
    }

    private boolean isDecorViewVisible(){
        return newWishDecorLayout.getVisibility() == View.VISIBLE;
    }

    private void showDecorView(){
        //May be we will need animations here
        newWishDecorLayout.setVisibility(View.VISIBLE);
    }

    private void hideDecorView(){
        //May be we will need animations here
        newWishDecorLayout.setVisibility(View.GONE);
    }

    @Override
    public void finishActivity() {
        this.finish();
    }

    @Override
    public void showLoading() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void decorateView(DecorItem item){
        item.decorateTheView(newWishEt);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == AuthActivity.AUTH_REQUET_CODE){
            presenter.onAuthActivityFinished(resultCode);
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDecorItemClick(DecorItem item) {
        presenter.onDecorItemClick(item);
    }

    @Override
    public void onBackPressed() {
        if(isDecorViewVisible()){
            hideDecorView();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
