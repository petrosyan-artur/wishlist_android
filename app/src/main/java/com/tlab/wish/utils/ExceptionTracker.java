package com.tlab.wish.utils;

import com.tlab.wish.BuildConfig;

/**
 * Created by andranik on 1/18/16.
 */
public class ExceptionTracker {
    public static void trackException(Throwable t){
        if(BuildConfig.DEBUG){ t.printStackTrace(); }
        //TODO may be we will need to log our exceptions latter
    }
}
