package com.tlab.wish.main_view_staff.home;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.tlab.wish.App;
import com.tlab.wish.R;
import com.tlab.wish.api_staff.WishesAPI;
import com.tlab.wish.utils.AppOfflineException;
import com.tlab.wish.wishes.Wish;
import com.tlab.wish.wishes.Wishes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by andranik on 2/3/16.
 */
public class HomeFrPresenter extends MvpBasePresenter<HomeFrView>{

    private Set<Subscription> subscriptions = new HashSet<>();

    public void getWishes(final boolean pullToRefresh, List<Wish> existingWishes){
        if(!App.getInstance().isOnline()){
            if (isViewAttached()){
                getView().showError(new AppOfflineException(), pullToRefresh);
            }

            return;
        }

        if(isViewAttached()){
            getView().showLoading(pullToRefresh);
        }

        Subscription subscription;

        if(App.getInstance().getPrefs().isAuthenticated()){
            subscription = WishesAPI.getInstanse().getWishesAuthenticated()
                    .map(removeDuplicateWishes(existingWishes))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getSubscriber(pullToRefresh));
        } else {
            subscription = WishesAPI.getInstanse().getWishes()
                    .map(removeDuplicateWishes(existingWishes))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getSubscriber(pullToRefresh));
        }

        subscriptions.add(subscription);
    }

    public void loadMoreWishes(int currentPage, int loadedCount){
        if(!App.getInstance().isOnline()){
            return;
        }

        if(isViewAttached()){
            getView().showLoadMoreLoading();
        }

        Subscription subscription;

        if(App.getInstance().getPrefs().isAuthenticated()){
            subscription = WishesAPI.getInstanse().loadMoreWishesAuthenticated(String.valueOf(loadedCount))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getSubscriber(false));
        } else {
            subscription = WishesAPI.getInstanse().loadMoreWishes(String.valueOf(loadedCount))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(getSubscriber(false));
        }

        subscriptions.add(subscription);
    }

    public String getErrorMessage(Throwable e, boolean pullToRefresh) {
        String message = App.getInstance().getString(R.string.something_went_wrong);

        if(e instanceof AppOfflineException){
            message = App.getInstance().getString(R.string.no_internet_connection);
        }

        return message;
    }

    public void onDestroy(){
        for(Subscription sub : subscriptions){
            if(sub != null && !sub.isUnsubscribed()){
                sub.unsubscribe();
            }
        }
    }


    private Func1<Wishes, Wishes> removeDuplicateWishes(final List<Wish> existingWishes){
        return new Func1<Wishes, Wishes>() {
            @Override
            public Wishes call(Wishes wishes) {
                List<Wish> filtered = new ArrayList<>();

                for(Wish w : wishes.getWishes()){
                    if(existingWishes.contains(w))
                        continue;

                    filtered.add(w);
                }

                wishes.setWishes(filtered);

                return wishes;
            }
        };
    }

    private Subscriber<Wishes> getSubscriber(final boolean pullToRefresh){
        return new Subscriber<Wishes>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()){
                    getView().showError(e, pullToRefresh);
                }
            }

            @Override
            public void onNext(Wishes wishes) {
                if (isViewAttached()){
                    getView().setData(wishes.getWishes());
                    getView().showContent();
                }
            }
        };
    }

}
