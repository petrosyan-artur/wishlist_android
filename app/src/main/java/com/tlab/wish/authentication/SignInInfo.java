package com.tlab.wish.authentication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by andranik on 1/21/16.
 */
public @Data class SignInInfo {

    public static SignInInfo newInstance(String username, String password) {
        SignInInfo signInInfo = new SignInInfo();

        signInInfo.setUsername(username);
        signInInfo.setPassword(password);

        return signInInfo;
    }

    @SerializedName("username")
    @Expose
    public String username;

    @SerializedName("password")
    @Expose
    public String password;

}
