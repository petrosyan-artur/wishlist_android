package com.tlab.wish.authentication;

import com.tlab.wish.api_staff.WishesAPI;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by andranik on 1/21/16.
 */
public class AuthHelper {

    public static void getAuthInfo(final AuthPresenter authPresenter){

        WishesAPI.getInstanse().getAuthInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AuthInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(authPresenter.isViewAttached()){
                            authPresenter.getView().hideLoading();
                            authPresenter.getView().showUnknownError();
                        }
                    }

                    @Override
                    public void onNext(AuthInfo authInfo) {
                        if(authPresenter.isViewAttached()){
                            authPresenter.getView().hideLoading();
                            authPresenter.getView().showSignUpView(authInfo);
                        }
                    }
                });

    }

    public static void signUp(final AuthPresenter authPresenter, final SignUpInfo signUpInfo){
        //TODO test
        authPresenter.onLoginSuccess();
    }

    public static void signIn(final AuthPresenter authPresenter, final SignInInfo signInInfo){
        //TODO test
        authPresenter.onLoginSuccess();
    }
}
