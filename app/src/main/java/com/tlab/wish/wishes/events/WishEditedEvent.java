package com.tlab.wish.wishes.events;

import com.tlab.wish.new_wish.WishSentResponse;

import lombok.Getter;

/**
 * Created by andranik on 2/4/16.
 */
public class WishEditedEvent {

    @Getter
    private WishSentResponse response;

    public WishEditedEvent(WishSentResponse response) {
        this.response = response;
    }
}
