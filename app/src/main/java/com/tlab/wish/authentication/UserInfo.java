package com.tlab.wish.authentication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by andranik on 1/22/16.
 */
public @Data class UserInfo {

    @SerializedName("username")
    @Expose
    public String username;

}
