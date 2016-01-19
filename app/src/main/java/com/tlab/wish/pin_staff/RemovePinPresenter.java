package com.tlab.wish.pin_staff;

import com.tlab.wish.App;
import com.tlab.wish.R;

/**
 * Created by andranik on 1/19/16.
 */
public class RemovePinPresenter extends PinPresenter{
    @Override
    public void onCreate() {
        if(!isViewAttached()){return;}

        getView().setTitle(App.getInstance().getString(R.string.old_pin_title));
    }

    @Override
    public void allDigitsEntered(String pin) {
        if(!isViewAttached()){return;}

        if(isPinRight(pin)){
            App.getInstance().getPrefs().setPin(null);
            getView().finishActivity();
        } else {
            getView().clearDigits();
            getView().showWrongPinError();
        }
    }
}
