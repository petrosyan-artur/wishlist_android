package com.tlab.wish.authentication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by andranik on 1/21/16.
 */
public @Data class AuthInfo {

    @SerializedName("success")
    @Expose
    public boolean success;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("hint")
    @Expose
    public String hint;

}
