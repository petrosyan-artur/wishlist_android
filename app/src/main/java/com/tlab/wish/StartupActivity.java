package com.tlab.wish;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tlab.wish.activities.BaseActivity;
import com.tlab.wish.api_staff.WishesAPI;
import com.tlab.wish.wishes.Wish;
import com.tlab.wish.wishes.Wishes;

import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class StartupActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
    }

    public void test(View v){
//        WishesAPI.getInstanse().getWishes("as", wishesCallback);

        Observable<Wishes> observable = WishesAPI.getInstanse().getWishes();

        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<Wishes, Observable<Wish>>() {
                    @Override
                    public Observable<Wish> call(Wishes wishes) {
                        return Observable.from(wishes.getWishes());
                    }
                }).subscribe(new Subscriber<Wish>() {
            @Override
            public void onCompleted() {
                Log.d("testt", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("testt", "onError");
                e.printStackTrace();
            }

            @Override
            public void onNext(Wish wish) {
                Log.d("testt", wish.toString());
            }
        });
    }

    private Callback<Wishes> wishesCallback = new Callback<Wishes>() {
        @Override
        public void onResponse(Response<Wishes> response) {
            for (Wish wish : response.body().getWishes()){
                Log.d("testt", wish.toString());
            }
        }

        @Override
        public void onFailure(Throwable t) {

        }
    };
}
