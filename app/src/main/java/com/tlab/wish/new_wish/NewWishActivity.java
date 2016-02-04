package com.tlab.wish.new_wish;

import android.support.annotation.NonNull;

import com.tlab.wish.App;
import com.tlab.wish.R;
import com.tlab.wish.utils.DialogUtils;

import butterknife.OnClick;

public class NewWishActivity extends WishActivity {

    public static final int REQUEST_CODE = 0x001;

    @OnClick(R.id.new_wish_send)
    public void onWishSendClick(){
        if(!App.getInstance().isOnline()){
            DialogUtils.showOfflineDialog(this);
            return;
        }

        presenter.sendWish(null, newWishEt.getText().toString());
    }

    @NonNull
    @Override
    public WishPresenter createPresenter() {
        return new NewWishPresenter();
    }
}
