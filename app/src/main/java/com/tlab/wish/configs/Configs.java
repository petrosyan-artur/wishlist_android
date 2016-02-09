package com.tlab.wish.configs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by andranik on 1/18/16.
 */
public @Data class Configs implements Serializable{
    static final long serialVersionUID =-1;

    @SerializedName("max_symbols")
    @Expose
    private int maxSymbols = 512; // Default value

    @SerializedName("decorations")
    @Expose
    public Decorations decorations;

    @SerializedName("wish_limit")
    @Expose
    public int wishLimit = 12; // Default value

    @SerializedName("messages")
    @Expose
    public Messages messages;

}
