package com.tlab.wish.new_wish;

import android.app.Activity;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.tlab.wish.App;
import com.tlab.wish.new_wish.decorations.DecorItem;
import com.tlab.wish.new_wish.decorations.LastSelectedDecor;

import rx.Subscription;

/**
 * Created by andranik on 1/21/16.
 */
public class NewWishPresenter extends MvpBasePresenter<NewWishView> {

    private Subscription sendWishSub;

    public void onCreate(){
        if(!isViewAttached()){return;}

        if(App.getInstance().getPrefs().isAuthenticated()){
            getView().initNewWishActivity();
            getView().decorateView(LastSelectedDecor.getInstanse().getSelectedDecodeItem());
        } else {
            getView().goToAuthentication();
        }
    }

    public void onAuthActivityFinished(int resultCode){
        if(!isViewAttached()){return;}

        if(resultCode == Activity.RESULT_OK){
            getView().initNewWishActivity();
        } else {
            getView().finishActivity();
        }
    }

    public void onDecorItemClick(DecorItem item){
        if(isViewAttached()){
            getView().decorateView(item);
        }

        LastSelectedDecor.getInstanse().updateSelectedDecor(item);
    }

    public void sendNewWish(String content){
        sendWishSub = WishHelper.sendNewWish(this, content);
    }

    public void onDestroy(){
        if(sendWishSub != null && !sendWishSub.isUnsubscribed()){
            sendWishSub.unsubscribe();
        }
    }

}
