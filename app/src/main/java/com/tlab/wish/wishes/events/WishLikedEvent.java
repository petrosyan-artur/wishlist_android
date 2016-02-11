package com.tlab.wish.wishes.events;

import com.tlab.wish.wishes.Wish;

import lombok.Getter;

/**
 * Created by andranik on 2/11/16.
 */
public class WishLikedEvent {

    @Getter
    private Wish wish;

    public WishLikedEvent(Wish wish) {
        this.wish = wish;
    }
}
