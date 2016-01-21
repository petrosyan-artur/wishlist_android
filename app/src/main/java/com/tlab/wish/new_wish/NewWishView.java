package com.tlab.wish.new_wish;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by andranik on 1/21/16.
 */
public interface NewWishView extends MvpView{

    void goToAuthentication();

    void initNewWishActivity();

    void finishActivity();
}
