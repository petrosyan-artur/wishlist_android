package com.tlab.wish.new_wish;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.tlab.wish.new_wish.decorations.DecorItem;

/**
 * Created by andranik on 1/21/16.
 */
public interface WishView extends MvpView{

    void goToAuthentication();

    void initNewWishActivity();

    void finishActivity();

    void showLoading();

    void hideLoading();

    void showAuthError(String message);

    void showUnknownError(boolean finishActivity);

    void onWishSendSuccess(WishSentResponse wishSentResponse);

    void onWishEditSuccess(WishSentResponse wishSentResponse);

    void decorateView(DecorItem item);
}
