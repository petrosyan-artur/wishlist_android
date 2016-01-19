package com.tlab.wish.startup;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.tlab.wish.App;

/**
 * Created by andranik on 1/18/16.
 */
public class StartupPresenter extends MvpBasePresenter<StartupView>{

    public void onCreate(){
        if(App.getInstance().getPrefs().hasPin()){
            if(isViewAttached()){
                getView().goToPinActivity();
            }
        } else {
            if(isViewAttached()){
                getView().goToMainActivity();
            }
        }
    }
}
