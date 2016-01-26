package com.tlab.wish.wishes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

public @Data class Wishes {

    @SerializedName("success")
    @Expose
    public boolean success;
    @SerializedName("wishes")
    @Expose
    public List<Wish> wishes = new ArrayList<Wish>();

}