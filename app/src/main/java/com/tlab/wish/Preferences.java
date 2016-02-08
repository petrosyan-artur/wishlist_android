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
    private static final String TOKEN_KEY = "token_key";
    private static final String USERNAME = "username";
    private static final String USER_ID = "user_id";

    private SharedPreferences mPreferences;

    public Preferences(Context context) {
        mPreferences = context.getSharedPreferences(PREFERENSES_NAME, Context.MODE_PRIVATE);
    }

    public void resetAll() {
        final SharedPreferences.Editor editor = getEditor();

        editor.remove(PIN_KEY);
        editor.remove(TOKEN_KEY);
        editor.remove(USERNAME);
        editor.remove(USER_ID);

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

    public String getToken() {
        return mPreferences.getString(TOKEN_KEY, null);
    }

    public boolean isAuthenticated(){
        return getToken() != null && getToken().length() > 0;
    }

    public void setToken(String value) {
        final SharedPreferences.Editor editor = getEditor();

        editor.putString(TOKEN_KEY, value);
        editor.apply();
    }

    public String getUsername() {
        return mPreferences.getString(USERNAME, null);
    }

    public void setUsername(String value) {
        final SharedPreferences.Editor editor = getEditor();

        editor.putString(USERNAME, value);
        editor.apply();
    }

    public String getUserId() {
        return mPreferences.getString(USER_ID, null);
    }

    public void setUserId(String value) {
        final SharedPreferences.Editor editor = getEditor();

        editor.putString(USER_ID, value);
        editor.apply();
    }
}
