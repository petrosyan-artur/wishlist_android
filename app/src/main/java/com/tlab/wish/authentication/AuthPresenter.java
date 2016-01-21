package com.tlab.wish.authentication;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

/**
 * Created by andranik on 1/21/16.
 */
public class AuthPresenter extends MvpBasePresenter<AuthView>{

    public void onCreate() {
        AuthHelper.getAuthInfo(this);
    }

    public void onSignUpClick(SignUpInfo si){
        if(si.getUsername().length() == 0
                || si.getPassword().length() == 0
                || si.getPassword2().length() == 0){
            return;
        }

        if(!si.getPassword().equals(si.getPassword2())){
            if(isViewAttached()){
                getView().showPasswordsDontMatchError();
            }
            return;
        }

        if(!isViewAttached()){return;}
        getView().showLoading();
        AuthHelper.signUp(this, si);
    }

    public void onSignInClick(SignInInfo si){
        if(si.getUsername().length() == 0 || si.getPassword().length() == 0){
            return;
        }

        if(!isViewAttached()){return;}
        getView().showLoading();
        AuthHelper.signIn(this, si);
    }

    public void onLoginSuccess(){
        if(!isViewAttached()){return;}
        getView().onLoginSuccess();
    }
}
