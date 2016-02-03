package com.tlab.wish.utils;

/**
 * Created by andranik on 2/3/16.
 */
public class AppOfflineException extends Exception{
    public AppOfflineException() {
    }

    public AppOfflineException(String detailMessage) {
        super(detailMessage);
    }
}
