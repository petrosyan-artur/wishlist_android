package com.tlab.wish.authentication;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.tlab.wish.App;

import java.util.HashSet;
import java.util.Set;

import rx.Subscription;

/**
 * Created by andranik on 1/21/16.
 */
public class AuthPresenter extends MvpBasePresenter<AuthView>{

    private Set<Subscription> subscriptions = new HashSet<>();

    public void onCreate() {
        Subscription subscription = AuthHelper.getAuthInfo(this);

        subscriptions.add(subscription);
    }

    public void onDestroy(){
        for(Subscription sub : subscriptions){
            if(sub != null && !sub.isUnsubscribed()){
                sub.unsubscribe();
            }
        }
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

        Subscription sub = AuthHelper.signUp(this, si);
        subscriptions.add(sub);
    }

    public void onSignInClick(SignInInfo si){
        if(si.getUsername().length() == 0 || si.getPassword().length() == 0){
            return;
        }

        if(!isViewAttached()){return;}
        getView().showLoading();

        Subscription sub = AuthHelper.signIn(this, si);
        subscriptions.add(sub);
    }

    public void onAuthComplete(AuthResponse authResponse, String username){
        if(!isViewAttached()){return;}

        if(authResponse.isSuccess()){
            App.getInstance().getPrefs().setToken(authResponse.getToken());
            App.getInstance().getPrefs().setUsername(username);
            getView().onLoginSuccess();
        } else {
            getView().hideLoading();
            getView().showAuthError(authResponse.getMessage());
        }
    }

    public void showUnknownError(boolean finsihActivity){
        if(!isViewAttached()){return;}
        getView().hideLoading();
        getView().showUnknownError(finsihActivity);
    }
}
