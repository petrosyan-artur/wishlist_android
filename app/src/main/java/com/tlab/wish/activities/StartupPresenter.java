package com.tlab.wish.activities;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.tlab.wish.App;
import com.tlab.wish.utils.Utils;

/**
 * Created by andranik on 1/18/16.
 */
public class StartupPresenter extends MvpBasePresenter<StartupView>{

    public void onCreate(){
        final String pin = App.getInstance().getPrefs().getPin();

        if(pin != null && pin.length() > 0){
            if(isViewAttached()){
                getView().showPinLayout();
            }
        } else {
            if(isViewAttached()){
                getView().goToMainActivity();
            }
        }
    }

    public void chechPin(String pin){
        if(Utils.toSHA1(pin).equals(App.getInstance().getPrefs().getPin())){
            if(isViewAttached()){
                getView().goToMainActivity();
            }
        }
    }
}
