package com.tlab.wish.new_wish;

import android.app.Activity;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.tlab.wish.App;

/**
 * Created by andranik on 1/21/16.
 */
public class NewWishPresenter extends MvpBasePresenter<NewWishView> {

    public void onCreate(){
        if(!isViewAttached()){return;}

        if(App.getInstance().getPrefs().isAuthenticated()){
            getView().initNewWishActivity();
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

}
