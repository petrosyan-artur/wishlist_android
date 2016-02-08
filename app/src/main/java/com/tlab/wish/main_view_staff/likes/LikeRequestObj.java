package com.tlab.wish.main_view_staff.likes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tlab.wish.wishes.Wish;

import lombok.Data;

/**
 * Created by andranik on 2/8/16.
 */
public @Data class LikeRequestObj {

    public static LikeRequestObj fromWish(Wish wish){
        LikeRequestObj obj = new LikeRequestObj();

        obj.setWishId(wish.getId());
        obj.setUserId(wish.getUserId());

        return obj;
    }

    @SerializedName("wishId")
    @Expose
    public String wishId;
    @SerializedName("userId")
    @Expose
    public String userId;
}
