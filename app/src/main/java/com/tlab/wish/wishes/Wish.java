package com.tlab.wish.wishes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public @ToString class Wish implements Comparable<Wish>{

    @SerializedName("_id")
    @Expose
    @Getter @Setter
    public String Id;
    @SerializedName("content")
    @Expose
    @Getter @Setter
    public String content;
    @SerializedName("createdDate")
    @Expose(serialize = false)
    @Getter @Setter
    public String createdDate;
    @SerializedName("timestamp")
    @Expose(serialize = false)
    @Getter @Setter
    public Integer timestamp;
    @SerializedName("decoration")
    @Expose
    @Getter @Setter
    public Decoration decoration;
    @SerializedName("userId")
    @Expose(serialize = false)
    @Getter @Setter
    public String userId;
    @SerializedName("username")
    @Expose(serialize = false)
    @Getter @Setter
    public String username;
    @SerializedName("isActive")
    @Expose(serialize = false)
    @Getter @Setter
    public boolean isActive;
    @SerializedName("likes")
    @Expose(serialize = false)
    @Getter @Setter
    public int likes;
    @SerializedName("liked")
    @Expose(serialize = false)
    @Getter @Setter
    public boolean liked;

    @Getter @Setter
    public String formatedDate;

    @Override
    public int hashCode() {
        return Id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Wish))
            return false;
        if (o == this)
            return true;

        return getId().equals(((Wish)o).getId());
    }

    @Override
    public int compareTo(Wish another) {
        if(getTimestamp() > another.getTimestamp()){
            return -1;
        } else if(getTimestamp() < another.getTimestamp()){
            return 1;
        }

        return 0;
    }
}