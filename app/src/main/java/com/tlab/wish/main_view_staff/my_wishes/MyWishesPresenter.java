package com.tlab.wish.main_view_staff.my_wishes;

import com.tlab.wish.App;
import com.tlab.wish.api_staff.WishesAPI;
import com.tlab.wish.main_view_staff.wish_list_base.WishListBasePresenter;
import com.tlab.wish.wishes.Wish;
import com.tlab.wish.wishes.Wishes;

import rx.Observable;

/**
 * Created by andranik on 2/4/16.
 */
public class MyWishesPresenter extends WishListBasePresenter<MyWishesView>{

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
        return WishesAPI.getInstanse().getUserWishesAuthenticated(App.getInstance().getPrefs().getUserId());
    }

    @Override
    public Observable<Wishes> loadMoreWishesObservable(String loadedCount) {
        return WishesAPI.getInstanse()
                .loadMoreUserWishesAuthenticated(loadedCount, App.getInstance().getPrefs().getUserId());
    }

    @Override
    public void onWishItemClicked(Wish wish) {
        if(!App.getInstance().isOnline()){
            if(isViewAttached()){ getView().showOfflineError(); }
            return;
        }

        if(wish.getLikes() > 0){
            if(isViewAttached()){getView().showEditError();}
            return;
        }

        if(isViewAttached()){getView().openEditWish(wish);}
    }
}
