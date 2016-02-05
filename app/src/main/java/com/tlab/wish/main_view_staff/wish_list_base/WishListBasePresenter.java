package com.tlab.wish.main_view_staff.wish_list_base;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.tlab.wish.App;
import com.tlab.wish.R;
import com.tlab.wish.utils.AppOfflineException;
import com.tlab.wish.wishes.DateFormater;
import com.tlab.wish.wishes.Wish;
import com.tlab.wish.wishes.Wishes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by andranik on 2/4/16.
 */
public abstract class WishListBasePresenter extends MvpBasePresenter<WishListBaseView> {

    private Set<Subscription> subscriptions = new HashSet<>();

    public abstract void onViewCreated();

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

        Subscription subscription = getWishesObservable()
                .flatMap(wishes -> Observable.from(wishes.getWishes()))
                .filter(wish -> !existingWishes.contains(wish))
                .doOnNext(wish -> wish.setFormatedDate(getFormatedDate(wish)))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSubscriber(pullToRefresh));

        subscriptions.add(subscription);
    }


    public void loadMoreWishes(int currentPage, int loadedCount){
        if(!App.getInstance().isOnline()){
            return;
        }

        if(isViewAttached()){
            getView().showLoadMoreLoading();
        }

        Subscription subscription = loadMoreWishesObservable(String.valueOf(loadedCount))
                .flatMap(wishes -> Observable.from(wishes.getWishes()))
                .doOnNext(wish -> wish.setFormatedDate(getFormatedDate(wish)))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSubscriber(false));

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

    private String getFormatedDate(Wish wish){
        return new DateFormater(wish.getCreatedDate()).getFormatedDate();
    }


    private Subscriber<List<Wish>> getSubscriber(final boolean pullToRefresh){
        return new Subscriber<List<Wish>>() {
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
            public void onNext(List<Wish> wishs) {
                if (isViewAttached()){
                    getView().setData(wishs, pullToRefresh);
                    getView().showContent();
                }
            }
        };
    }


    public abstract Observable<Wishes> getWishesObservable();

    public abstract Observable<Wishes> loadMoreWishesObservable(String loadedCount);
}
