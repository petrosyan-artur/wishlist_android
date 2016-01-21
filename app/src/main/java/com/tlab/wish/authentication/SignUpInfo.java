package com.tlab.wish.authentication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by andranik on 1/21/16.
 */
public @Data class SignUpInfo {

    public static SignUpInfo newInstance(String username, String password, String password2) {
        SignUpInfo signUpInfo = new SignUpInfo();

        signUpInfo.setUsername(username);
        signUpInfo.setPassword(password);
        signUpInfo.setPassword2(password2);

        return signUpInfo;
    }

    @SerializedName("username")
    @Expose
    public String username;

    @SerializedName("password")
    @Expose
    public String password;

    @SerializedName("password2")
    @Expose
    public String password2;
}
