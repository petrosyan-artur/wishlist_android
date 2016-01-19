package com.tlab.wish.pin_staff;

import com.tlab.wish.App;
import com.tlab.wish.R;

/**
 * Created by andranik on 1/19/16.
 */
public class CheckPinPresenter extends PinPresenter{

    @Override
    public void onCreate() {
        if(isViewAttached()){
            getView().setTitle(App.getInstance().getString(R.string.pin_title));
        }
    }

    @Override
    public void allDigitsEntered(String pin) {
        if(!isViewAttached()){return;}

        if(isPinRight(pin)){
            getView().goToMainActivity();
        } else {
            getView().clearDigits();
            getView().showWrongPinError();
        }
    }
}
