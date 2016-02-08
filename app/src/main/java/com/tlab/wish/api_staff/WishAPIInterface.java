package com.tlab.wish.api_staff;

import com.tlab.wish.authentication.AuthInfo;
import com.tlab.wish.authentication.AuthResponse;
import com.tlab.wish.authentication.SignInInfo;
import com.tlab.wish.authentication.SignUpInfo;
import com.tlab.wish.configs.Configuration;
import com.tlab.wish.main_view_staff.likes.LikeRequestObj;
import com.tlab.wish.new_wish.WishResponse;
import com.tlab.wish.wishes.Wish;
import com.tlab.wish.wishes.Wishes;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
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

    @GET("wishes")
    Observable<Wishes> loadMoreWishes(@Query("limit") String limit);

    @GET("private/wishes")
    Observable<Wishes> getWishesAuthenticated();

    @GET("private/wishes")
    Observable<Wishes> getWishesAuthenticated(@Query("content") String content);

    @GET("private/wishes")
    Observable<Wishes> loadMoreWishesAuthenticated(@Query("limit") String limit);

    @POST("private/wishes")
    Observable<WishResponse> sendNewWish(@Body Wish wish);

    @PUT("private/wishes")
    Observable<WishResponse> updateWish(@Body Wish wish);

    @GET("users")
    Observable<AuthInfo> getAuthInfo();

    @POST("register")
    Observable<AuthResponse> register(@Body SignUpInfo signUpInfo);

    @POST("authenticate")
    Observable<AuthResponse> authenticate(@Body SignInInfo signInInfo);

    @PUT("private/users")
    Observable<ResponseBody> updateUserInfo();

    @POST("private/rates")
    Observable<GeneralResponse> likeWish(@Body LikeRequestObj likeRequestObj);

    @DELETE("private/rates/{userId}/{wishId}")
    Observable<GeneralResponse> unlikeWish(@Path("userId") String userId, @Path("wishId") String wishId);
}
