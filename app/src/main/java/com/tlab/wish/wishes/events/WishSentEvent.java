package com.tlab.wish.wishes.events;

import com.tlab.wish.new_wish.WishSentResponse;

import lombok.Getter;

/**
 * Created by andranik on 2/4/16.
 */
public class WishSentEvent {

    @Getter
    private WishSentResponse response;

    public WishSentEvent(WishSentResponse response) {
        this.response = response;
    }
}
