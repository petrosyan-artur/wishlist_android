package com.tlab.wish.new_wish;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.tlab.wish.App;
import com.tlab.wish.CustomTypeFace;
import com.tlab.wish.R;
import com.tlab.wish.authentication.AuthActivity;
import com.tlab.wish.configs.ConfigurationManager;
import com.tlab.wish.new_wish.decorations.ColorDecorItem;
import com.tlab.wish.new_wish.decorations.DecorAdapter;
import com.tlab.wish.new_wish.decorations.DecorItem;
import com.tlab.wish.utils.DialogUtils;
import com.tlab.wish.utils.ViewUtils;
import com.tlab.wish.wishes.events.WishEditedEvent;
import com.tlab.wish.wishes.events.WishSentEvent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public abstract class WishActivity extends MvpActivity<WishView, WishPresenter> implements WishView, DecorAdapter.OnDecorItemClickListener{

    public static final int REQUEST_CODE = 0x001;

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

    @Bind(R.id.new_wish_decor_title)
    TextView decorTitle;

    @Bind(R.id.new_wish_colors_btn)
    View colorBtn;

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

    @Override
    public void goToAuthentication() {
        startActivityForResult(new Intent(this, AuthActivity.class), AuthActivity.AUTH_REQUET_CODE);
    }

    @Override
    public void initNewWishActivity() {
        final Typeface ROBOTO_REGULAR = App.getInstance().getTypeface(CustomTypeFace.MyTypeFace.ROBOTO_REGULAR);
        newWishEt.setTypeface(ROBOTO_REGULAR);
        WishHelper.limitTextSymbols(newWishEt);

        decorTitle.setTypeface(ROBOTO_REGULAR);

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
            colorBtn.setSelected(false);
            hideDecorView();
        } else {
            recyclerView.setAdapter(decorColorAdapter);
            colorBtn.setSelected(true);
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
    public void showAuthError(String message) {
        DialogUtils.showAlertDialog(this, message);
    }

    @Override
    public void showUnknownError(boolean finishActivity) {
        DialogUtils.showSomethingWentWrong(this, finishActivity);
    }

    @Override
    public void onWishSendSuccess(WishSentResponse response) {
        Toast.makeText(this, R.string.wish_sent_toast_msg, Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new WishSentEvent(response));
        finish();
    }

    @Override
    public void onWishEditSuccess(WishSentResponse response) {
        Toast.makeText(this, R.string.wish_edited_toast_msg, Toast.LENGTH_SHORT).show();
        EventBus.getDefault().post(new WishEditedEvent(response));
        finish();
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
        presenter.onDecorItemSelected(item);
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
