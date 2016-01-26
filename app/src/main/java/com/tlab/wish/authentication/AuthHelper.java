package com.tlab.wish.authentication;

import com.tlab.wish.App;
import com.tlab.wish.api_staff.WishesAPI;
import com.tlab.wish.utils.ExceptionTracker;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by andranik on 1/21/16.
 */
public class AuthHelper {

    public static Subscription getAuthInfo(final AuthPresenter authPresenter){

        return WishesAPI.getInstanse().getAuthInfo()
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
                            authPresenter.getView().showUnknownError(true);
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

    public static Subscription signUp(final AuthPresenter authPresenter, final SignUpInfo signUpInfo){

        return WishesAPI.getInstanse().register(signUpInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AuthResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        authPresenter.showUnknownError(false);
                        ExceptionTracker.trackException(e);
                    }

                    @Override
                    public void onNext(AuthResponse authResponse) {
                        authPresenter.onAuthComplete(authResponse, signUpInfo.getUsername());
                    }
                });
    }

    public static Subscription signIn(final AuthPresenter authPresenter, final SignInInfo signInInfo){
        return WishesAPI.getInstanse().authenticate(signInInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AuthResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        authPresenter.showUnknownError(false);
                        ExceptionTracker.trackException(e);
                    }

                    @Override
                    public void onNext(AuthResponse authResponse) {
                        authPresenter.onAuthComplete(authResponse, signInInfo.getUsername());
                    }
                });
    }

    public static void updateUserInfo(){
        if(!App.getInstance().getPrefs().isAuthenticated()){return;}

        WishesAPI.getInstanse().updateUserInfo()
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        ExceptionTracker.trackException(e);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                    }
                });
    }
}
