package com.tlab.wish.main_view_staff.wish_list_base;

import com.tlab.wish.App;
import com.tlab.wish.api_staff.WishesAPI;
import com.tlab.wish.configs.ConfigurationManager;
import com.tlab.wish.wishes.Wish;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by andranik on 2/9/16.
 */
public class NewWishsTracker {

    private int interval;
    private Subscription subscription;
    private Observable<HasNewWishResponse> observable;
    private HasNewWishesListener listener;
    private List<Wish> wishs;

    public NewWishsTracker() {
        this.interval = ConfigurationManager.getInstanse().getConfigs().getWishCheckInterval();

        observable = Observable.interval(interval, TimeUnit.SECONDS)
                .filter(aLong -> App.getInstance().isOnline())
                .filter(aLong1 -> listener != null)
                .filter(aLong2 -> wishs != null)
                .flatMap(aLong -> WishesAPI.getInstanse().checkHasNewWishes(wishs.isEmpty() ? "" : wishs.get(0).getId()))
                .retry()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void startTrackingForNewWishes(HasNewWishesListener listener, List<Wish> wishs){
        this.listener = listener;
        this.wishs = wishs;

//        subscription = observable.subscribe(new Subscriber<HasNewWishResponse>() {
//            @Override
//            public void onCompleted() {
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                ExceptionTracker.trackException(e);
//            }
//
//            @Override
//            public void onNext(HasNewWishResponse response) {
//                if (listener != null && response.isHasNew()) {
//                    listener.onHasNewWishes(response);
//                }
//            }
//        });


    }

    public void updateWihesList(List<Wish> wishs){
        this.wishs = wishs;
    }

    public void stopTrackingForNewWishes(){
        listener = null;
        wishs = null;

        if(subscription != null && !subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }

    public interface HasNewWishesListener{
        void onHasNewWishes(HasNewWishResponse response);
    }
}
