package com.tlab.wish.startup;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by andranik on 1/18/16.
 */
public interface StartupView extends MvpView{

    void goToMainActivity();

    void goToPinActivity();
}
