package com.tlab.wish.configs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by andranik on 2/9/16.
 */
public @Data class Messages implements Serializable{
    static final long serialVersionUID =-1;

    @SerializedName("wish_edit_alert")
    @Expose
    public String wishEditAlert;
}
