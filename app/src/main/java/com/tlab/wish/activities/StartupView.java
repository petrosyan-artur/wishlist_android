package com.tlab.wish.activities;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by andranik on 1/18/16.
 */
public interface StartupView extends MvpView{

    void goToMainActivity();

    void showPinLayout();

}
