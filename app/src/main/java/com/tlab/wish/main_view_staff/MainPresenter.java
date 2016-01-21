package com.tlab.wish.main_view_staff;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

/**
 * Created by andranik on 1/20/16.
 */
public class MainPresenter extends MvpBasePresenter<MainView> {

    void onPageSelected(int position){
        if(!isViewAttached()){return;}

        if(position > 3){
            getView().hideFab();
        } else {
            getView().showFab();
        }
    }

    void onFabClicked(){
        if(!isViewAttached()){return;}

        getView().openNewWish();
    }
}
