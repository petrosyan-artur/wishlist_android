package com.tlab.wish.main_view_staff;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by andranik on 1/20/16.
 */
public interface MainView extends MvpView{

    void showFab();

    void hideFab();

    void openNewWish();
}
