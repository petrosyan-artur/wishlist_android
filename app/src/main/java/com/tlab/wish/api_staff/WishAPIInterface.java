package com.tlab.wish.api_staff;

import com.tlab.wish.configs.Configuration;
import com.tlab.wish.wishes.Wishes;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by andranik on 1/16/16.
 */
public interface WishAPIInterface {
    String BASE_URL = "http://37.48.84.64:201/api/v1/";

    @GET("configuration")
    Observable<Configuration> getConfiguration();

    @GET("wishes")
    Observable<Wishes> getWishes();

    @GET("wishes")
    Observable<Wishes> getWishes(@Query("content") String content);
}
