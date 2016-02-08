package com.tlab.wish.new_wish;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tlab.wish.wishes.Wish;

import lombok.Data;

/**
 * Created by andranik on 1/27/16.
 */
public @Data class WishSentResponse {

    @SerializedName("success")
    @Expose
    public boolean success;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("wish")
    @Expose
    public Wish wish;
}
