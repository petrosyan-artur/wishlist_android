package com.tlab.wish.new_wish;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.tlab.wish.new_wish.decorations.DecorItem;

/**
 * Created by andranik on 1/21/16.
 */
public interface NewWishView extends MvpView{

    void goToAuthentication();

    void initNewWishActivity();

    void finishActivity();

    void showLoading();

    void hideLoading();

    void decorateView(DecorItem item);
}
