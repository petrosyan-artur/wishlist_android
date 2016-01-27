package com.tlab.wish.new_wish;

import com.tlab.wish.api_staff.WishesAPI;
import com.tlab.wish.new_wish.decorations.ColorDecorItem;
import com.tlab.wish.new_wish.decorations.DecorItem;
import com.tlab.wish.new_wish.decorations.LastSelectedDecor;
import com.tlab.wish.utils.ExceptionTracker;
import com.tlab.wish.wishes.Decoration;
import com.tlab.wish.wishes.Wish;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by andranik on 1/26/16.
 */
public class WishHelper {

    public static Subscription sendNewWish(final NewWishPresenter presenter, String content){
        return WishesAPI.getInstanse().sendNewWish(getWish(content))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewWishResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        presenter.showUnknownError(false);
                        ExceptionTracker.trackException(e);
                    }

                    @Override
                    public void onNext(NewWishResponse newWishResponse) {
                        presenter.onWishSent(newWishResponse);
                    }
                });
    }

    private static Wish getWish(String content){
        Wish wish = new Wish();

        wish.setContent(content);
        wish.setDecoration(getDecoration());

        return wish;
    }

    private static Decoration getDecoration(){
        Decoration decoration = new Decoration();

        DecorItem di = LastSelectedDecor.getInstanse().getSelectedDecodeItem();

        if(di instanceof ColorDecorItem){
            decoration.setColor(((ColorDecorItem) di).getColorStr());
            decoration.setImage("");
        } else {
            //TODO here we will init the PictureDecoration
        }

        return decoration;
    }
}
