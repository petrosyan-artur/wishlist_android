package com.tlab.wish.wishes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by andranik on 1/26/16.
 */
public @Data class Decoration {

    @SerializedName("image")
    @Expose
    public String image;
    @SerializedName("color")
    @Expose
    public String color;

}
