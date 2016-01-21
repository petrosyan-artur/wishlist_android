package com.tlab.wish.authentication;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by andranik on 1/21/16.
 */
public interface AuthView extends MvpView{

    void showLoading();

    void hideLoading();

    void showSignUpView(AuthInfo authInfo);

    void showSignInView();

    void showPasswordsDontMatchError();

    void showAuthError();

    void showUnknownError();

    void onLoginSuccess();
}
