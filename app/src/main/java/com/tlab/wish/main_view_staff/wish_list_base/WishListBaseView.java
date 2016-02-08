package com.tlab.wish.main_view_staff.wish_list_base;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;
import com.tlab.wish.wishes.Wish;

import java.util.List;

/**
 * Created by andranik on 2/4/16.
 */
public interface WishListBaseView extends MvpLceView<List<Wish>> {

    void initViews();

    void setData(List<Wish> data, boolean fromBegining);

    void showLoadMoreLoading();

    void showAuthError();

    void showOfflineError();

    void openAuthActivity();

    void notifyDataSetChanged();

    void showLikeError();
}
