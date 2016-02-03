package com.tlab.wish.api_staff;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tlab.wish.App;
import com.tlab.wish.BuildConfig;
import com.tlab.wish.authentication.AuthInfo;
import com.tlab.wish.authentication.AuthResponse;
import com.tlab.wish.authentication.SignInInfo;
import com.tlab.wish.authentication.SignUpInfo;
import com.tlab.wish.configs.Configuration;
import com.tlab.wish.new_wish.WishResponse;
import com.tlab.wish.wishes.Wish;
import com.tlab.wish.wishes.Wishes;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import retrofit2.http.Query;
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
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(getHttpClient())
                .build();

        apiService = retrofit.create(WishAPIInterface.class);
    }

    private Gson getGson(){
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    private OkHttpClient getHttpClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(
                new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request original = chain.request();

                        // Request customization: add request headers
                        Request.Builder requestBuilder = original.newBuilder()
                                .header("my-user-agent", App.getInstance().getUserAgent());

                        if(App.getInstance().getPrefs().isAuthenticated()){
                            requestBuilder.header("x-access-token", App.getInstance().getPrefs().getToken());
                        }

                        requestBuilder.method(original.method(), original.body());

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                });

        if(BuildConfig.DEBUG){
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        return builder.build();
    }

    @Override
    public Observable<Configuration> getConfiguration() {
        return apiService.getConfiguration();
    }

    @Override
    public Observable<Wishes> getWishes() {
        return apiService.getWishes();
    }

    @Override
    public Observable<Wishes> getWishes(String content) {
        return apiService.getWishes(content);
    }

    @Override
    public Observable<Wishes> loadMoreWishes(@Query("limit") String limit) {
        return apiService.loadMoreWishes(limit);
    }

    @Override
    public Observable<Wishes> getWishesAuthenticated() {
        return apiService.getWishesAuthenticated();
    }

    @Override
    public Observable<Wishes> getWishesAuthenticated(String content) {
        return apiService.getWishesAuthenticated(content);
    }

    @Override
    public Observable<Wishes> loadMoreWishesAuthenticated(@Query("limit") String limit) {
        return apiService.loadMoreWishes(limit);
    }

    @Override
    public Observable<WishResponse> sendNewWish(Wish wish) {
        return apiService.sendNewWish(wish);
    }

    @Override
    public Observable<WishResponse> updateWish(Wish wish) {
        return apiService.updateWish(wish);
    }

    @Override
    public Observable<AuthInfo> getAuthInfo() {
        return apiService.getAuthInfo();
    }

    @Override
    public Observable<AuthResponse> register(SignUpInfo signUpInfo) {
        return apiService.register(signUpInfo);
    }

    @Override
    public Observable<AuthResponse> authenticate(SignInInfo signInInfo) {
        return apiService.authenticate(signInInfo);
    }

    @Override
    public Observable<ResponseBody> updateUserInfo() {
        return apiService.updateUserInfo();
    }
}
