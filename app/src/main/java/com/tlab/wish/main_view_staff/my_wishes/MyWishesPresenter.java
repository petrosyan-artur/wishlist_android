package com.tlab.wish.main_view_staff.my_wishes;

import com.tlab.wish.App;
import com.tlab.wish.api_staff.WishesAPI;
import com.tlab.wish.main_view_staff.wish_list_base.WishListBasePresenter;
import com.tlab.wish.wishes.Wishes;

import rx.Observable;

/**
 * Created by andranik on 2/4/16.
 */
public class MyWishesPresenter extends WishListBasePresenter{

    @Override
    public void onViewCreated() {
        if(!isViewAttached()){return;}

        if (App.getInstance().getPrefs().isAuthenticated()){
            getView().initViews();
        } else {
            getView().showAuthError();
        }
    }

    @Override
    public Observable<Wishes> getWishesObservable() {
        return WishesAPI.getInstanse().getWishesAuthenticated(); // TODO replace with right method
    }

    @Override
    public Observable<Wishes> loadMoreWishesObservable(String loadedCount) {
        return WishesAPI.getInstanse().loadMoreWishesAuthenticated(loadedCount); // TODO replace with right method
    }
}
