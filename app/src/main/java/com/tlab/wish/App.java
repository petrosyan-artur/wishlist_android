package com.tlab.wish;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tlab.wish.configs.ConfigurationManager;

/**
 * Created by andranik on 1/18/16.
 */
public class App extends Application{

    private static App mInstance;

    private Preferences mPreferences;

    private CustomTypeFace mCustomTypeFace;

    private Handler mUiHandler;

    private String deviceId;

    public App() {
        super();
    }

    public static App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        mUiHandler = new Handler(Looper.getMainLooper());

        mCustomTypeFace = new CustomTypeFace(getApplicationContext());

        mPreferences = new Preferences(getApplicationContext());

        deviceId = Settings.Secure.getString(App.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);

        initImageLoader(getApplicationContext());

        ConfigurationManager.getInstanse().updateConfiguration();
    }

    public void runTaskOnUiThread(Runnable uiTask){
        mUiHandler.post(uiTask);
    }

    public Handler getUiHandler(){
        return mUiHandler;
    }

    public Preferences getPrefs() {
        return mPreferences;
    }

    public Typeface getTypeface(CustomTypeFace.MyTypeFace typeFace){
        return mCustomTypeFace.getTypeFace(typeFace);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    public String getUserAgent(){
        return String.format("android; %s; %s; %s", Build.VERSION.RELEASE, Build.MODEL, BuildConfig.VERSION_NAME);
    }

    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }
}
