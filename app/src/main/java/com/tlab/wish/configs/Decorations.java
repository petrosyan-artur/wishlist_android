package com.tlab.wish.configs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Created by andranik on 1/26/16.
 */
public @Data class Decorations implements Serializable{
    static final long serialVersionUID =-1;

    @SerializedName("colors")
    @Expose
    public List<String> colors = new ArrayList<>();
    @SerializedName("images")
    @Expose
    public List<String> images = new ArrayList<>();
}
