package com.tlab.wish.wishes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

public @Data class Wish {

    @SerializedName("_id")
    @Expose
    public String Id;
    @SerializedName("content")
    @Expose
    public String content;
    @SerializedName("createdDate")
    @Expose(serialize = false)
    public String createdDate;
    @SerializedName("decoration")
    @Expose
    public Decoration decoration;
    @SerializedName("userId")
    @Expose(serialize = false)
    public String userId;
    @SerializedName("username")
    @Expose(serialize = false)
    public String username;
    @SerializedName("isActive")
    @Expose(serialize = false)
    public boolean isActive;
    @SerializedName("likes")
    @Expose(serialize = false)
    public int likes;
    @SerializedName("liked")
    @Expose(serialize = false)
    public boolean liked;

}