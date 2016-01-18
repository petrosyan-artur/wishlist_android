package com.tlab.wish.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by andranik on 1/18/16.
 */
public class Utils {

    public static String toSHA1(String convert) {
        try {
            final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();
            final MessageDigest md = MessageDigest.getInstance("SHA-1");

            byte[] buf = md.digest(convert.getBytes());
            char[] chars = new char[2 * buf.length];
            for (int i = 0; i < buf.length; ++i) {
                chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
                chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
            }
            return new String(chars);
        } catch (NoSuchAlgorithmException e){
            ExceptionTracker.trackException(e);
        }

        return convert;
    }
}
