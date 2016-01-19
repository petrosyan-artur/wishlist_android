package com.tlab.wish.pin_staff;

import android.support.annotation.NonNull;

/**
 * Created by andranik on 1/19/16.
 */
public class SetPinActivity extends PinActivity{
    @NonNull
    @Override
    public PinPresenter createPresenter() {
        return new SetPinPresenter();
    }
}
