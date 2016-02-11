package com.tlab.wish.main_view_staff.wish_list_base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by andranik on 2/9/16.
 */
public @Data class HasNewWishResponse {

    @SerializedName("success")
    @Expose
    public boolean success;
    @SerializedName("hasNew")
    @Expose
    public boolean hasNew;
    @SerializedName("count")
    @Expose
    public int count;
}
