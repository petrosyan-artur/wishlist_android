package com.tlab.wish.main_view_staff.user_wishes;

import com.tlab.wish.App;
import com.tlab.wish.api_staff.WishesAPI;
import com.tlab.wish.main_view_staff.wish_list_base.WishListBasePresenter;
import com.tlab.wish.wishes.Wish;
import com.tlab.wish.wishes.Wishes;

import rx.Observable;

/**
 * Created by andranik on 2/20/16.
 */
public class UserWishesPresenter extends WishListBasePresenter<UserWishesView> {

    private String userId;

    public UserWishesPresenter(String userId) {
        super();
        this.userId = userId;
    }

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
    public void onWishItemClicked(Wish wish) {
    }

    @Override
    public void onWishItemLongClicked(Wish wish) {
    }

    @Override
    public Observable<Wishes> getWishesObservable() {
        return WishesAPI.getInstanse().getUserWishesAuthenticated(userId);
    }

    @Override
    public Observable<Wishes> loadMoreWishesObservable(String loadedCount) {
        return WishesAPI.getInstanse()
                .loadMoreUserWishesAuthenticated(loadedCount, userId);
    }
}
