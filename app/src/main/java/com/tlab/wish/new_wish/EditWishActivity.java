package com.tlab.wish.new_wish;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.tlab.wish.App;
import com.tlab.wish.R;
import com.tlab.wish.utils.DialogUtils;

import butterknife.OnClick;

public class EditWishActivity extends WishActivity {

    public static final String EDIT_WISH_KEY = "com.tlab.wish.new_wish_edit_wish_key";

    private EditWish editWish;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editWish = (EditWish) getIntent().getSerializableExtra(EDIT_WISH_KEY);
        newWishEt.setText(editWish.getContent());
        presenter.onDecorItemSelected(editWish.getDecorItem());
    }

    @OnClick(R.id.new_wish_send)
    public void onWishSendClick(){
        if(!App.getInstance().isOnline()){
            DialogUtils.showOfflineDialog(this);
            return;
        }

        presenter.sendWish(editWish.getId(), newWishEt.getText().toString());
    }

    @NonNull
    @Override
    public WishPresenter createPresenter() {
        return new EditWishPresenter();
    }
}
