package com.tlab.wish.pin_staff;

import com.tlab.wish.App;
import com.tlab.wish.R;

/**
 * Created by andranik on 1/19/16.
 */
public class SetPinPresenter extends PinPresenter{

    private String enteredPin;
    private boolean oldPinChecked;

    @Override
    public void onCreate() {
        if(!isViewAttached()){return;}

        if(App.getInstance().getPrefs().hasPin()){
            getView().setTitle(App.getInstance().getString(R.string.old_pin_title));
        } else {
            getView().setTitle(App.getInstance().getString(R.string.new_pin_title));
        }
    }

    @Override
    public void allDigitsEntered(String pin) {
        if(!isViewAttached()){return;}

        if(App.getInstance().getPrefs().hasPin() && !oldPinChecked){
            if(isPinRight(pin)){
                oldPinChecked = true;
                getView().clearDigits();
                getView().setTitle(App.getInstance().getString(R.string.new_pin_title));
            } else {
                oldPinChecked = false;
                getView().clearDigits();
                getView().showWrongPinError();
            }
        } else if (enteredPin == null){
            enteredPin = pin;
            getView().clearDigits();
            getView().setTitle(App.getInstance().getString(R.string.repeat_pin_title));
        } else {
            if(pin.equals(enteredPin)){
                App.getInstance().getPrefs().setPin(pin);
                getView().finishActivity();
            } else {
                enteredPin = null;
                getView().showWrongPinError();
                getView().clearDigits();
                getView().setTitle(App.getInstance().getString(R.string.new_pin_title));
            }
        }
    }
}
