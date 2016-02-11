package com.tlab.wish.wishes.events;

import com.tlab.wish.wishes.Wish;

import lombok.Getter;

/**
 * Created by andranik on 2/11/16.
 */
public class WishDeletedEvent {

    @Getter
    private Wish wish;

    public WishDeletedEvent(Wish wish) {
        this.wish = wish;
    }
}
