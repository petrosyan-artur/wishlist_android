package com.tlab.wish;

import android.content.Context;
import android.content.SharedPreferences;

import com.tlab.wish.utils.Utils;

/**
 * Created by andranik on 1/18/16.
 */
public class Preferences {
    private static final String PREFERENSES_NAME = "prefs";

    private static final String PIN_KEY = "pin_key";

    private SharedPreferences mPreferences;

    public Preferences(Context context) {
        mPreferences = context.getSharedPreferences(PREFERENSES_NAME, Context.MODE_PRIVATE);
    }

    public void resetAll() {
        final SharedPreferences.Editor editor = getEditor();

        editor.remove(PIN_KEY);

        editor.apply();
    }

    private SharedPreferences.Editor getEditor() {
        return mPreferences.edit();
    }

    public String getPin() {
        return mPreferences.getString(PIN_KEY, null);
    }

    public boolean hasPin(){
        String pin = App.getInstance().getPrefs().getPin();

        return pin != null && pin.length() > 0;
    }

    public void setPin(String value) {
        final SharedPreferences.Editor editor = getEditor();

        editor.putString(PIN_KEY, Utils.toSHA1(value));
        editor.apply();
    }
}
