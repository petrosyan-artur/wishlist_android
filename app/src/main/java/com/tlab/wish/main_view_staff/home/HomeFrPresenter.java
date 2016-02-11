package com.tlab.wish.main_view_staff.home;

import com.tlab.wish.App;
import com.tlab.wish.api_staff.WishesAPI;
import com.tlab.wish.main_view_staff.wish_list_base.WishListBasePresenter;
import com.tlab.wish.wishes.Wishes;

import rx.Observable;

/**
 * Created by andranik on 2/3/16.
 */
public class HomeFrPresenter extends WishListBasePresenter<HomeFrView>{

    public HomeFrPresenter() {
        createNewWishTracker();
    }

    @Override
    public void onViewCreated() {
        if(isViewAttached()){
            getView().initViews();
        }
    }

    @Override
    public Observable<Wishes> getWishesObservable() {
        if(App.getInstance().getPrefs().isAuthenticated()){
            return WishesAPI.getInstanse().getWishesAuthenticated();
        } else {
            return WishesAPI.getInstanse().getWishes();
        }
    }

    @Override
    public Observable<Wishes> loadMoreWishesObservable(String loadedCount) {
        if(App.getInstance().getPrefs().isAuthenticated()){
            return WishesAPI.getInstanse().loadMoreWishesAuthenticated(loadedCount);
        } else {
            return WishesAPI.getInstanse().loadMoreWishes(loadedCount);
        }
    }
}
