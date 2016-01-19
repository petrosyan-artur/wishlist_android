package com.tlab.wish.pin_staff;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by andranik on 1/19/16.
 */
public interface PinView extends MvpView{

    void setTitle(String title);

    void clearDigits();

    void goToMainActivity();

    void finishActivity();

    void showWrongPinError();
}
