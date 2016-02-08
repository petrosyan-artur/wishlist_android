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
public abstract class WishPresenter extends MvpBasePresenter<WishView> {

    protected Subscription sendWishSub;

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

    public void onDecorItemSelected(DecorItem item){
        if(isViewAttached()){
            getView().decorateView(item);
        }
    }

    public boolean sendWish(String id, String content){
        if(content.length() == 0){return false;}

        if(!isViewAttached()){return false;}
        getView().showLoading();

        return true;
    }

    public void onWishSent(WishSentResponse response){
        if(!isViewAttached()){return;}

        if(response.isSuccess()){
            getView().hideLoading();
            getView().onWishSendSuccess(response);
        } else {
            getView().hideLoading();
            getView().showAuthError(response.getMessage());
        }
    }

    public void showUnknownError(boolean finsihActivity){
        if(!isViewAttached()){return;}
        getView().hideLoading();
        getView().showUnknownError(finsihActivity);
    }

    public void onDestroy(){
        if(sendWishSub != null && !sendWishSub.isUnsubscribed()){
            sendWishSub.unsubscribe();
        }
    }

}
