package com.tlab.wish.api_staff;

import com.tlab.wish.wishes.Wishes;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Observable;

/**
 * Created by andranik on 1/17/16.
 */
public class WishesAPI implements WishAPIInterface{


    private static WishesAPI instanse = new WishesAPI();

    public static WishesAPI getInstanse(){
        return instanse;
    }

    private WishAPIInterface apiService;

    private WishesAPI(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WishAPIInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        apiService = retrofit.create(WishAPIInterface.class);
    }

    @Override
    public Observable<Wishes> getWishes() {
        return apiService.getWishes();
    }

    @Override
    public Observable<Wishes> getWishes(String content) {
        return apiService.getWishes(content);
    }

//    public void getWishes(Callback<Wishes> callback) {
//        apiService.getWishes().enqueue(callback);
//    }
//
//    public void getWishes(String content, Callback<Wishes> callback) {
//        apiService.getWishes(content).enqueue(callback);
//    }
}
