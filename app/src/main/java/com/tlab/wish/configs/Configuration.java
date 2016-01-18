package com.tlab.wish.configs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

/**
 * Created by andranik on 1/18/16.
 */
public @Data class Configuration {

    @SerializedName("success")
    @Expose
    public boolean success;
    @SerializedName("configs")
    @Expose
    public Configs configs;
}
