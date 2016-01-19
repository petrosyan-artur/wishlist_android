package com.tlab.wish.pin_staff;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.tlab.wish.App;
import com.tlab.wish.utils.Utils;

/**
 * Created by andranik on 1/19/16.
 */
public abstract class PinPresenter extends MvpBasePresenter<PinView> {

    public abstract void onCreate();
    public abstract void allDigitsEntered(String pin);

    protected boolean isPinRight(String pin){
        return Utils.toSHA1(pin).equals(App.getInstance().getPrefs().getPin());
    }
}
