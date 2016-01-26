package com.tlab.wish.new_wish;

import com.tlab.wish.new_wish.decorations.ColorDecorItem;
import com.tlab.wish.new_wish.decorations.DecorItem;
import com.tlab.wish.new_wish.decorations.LastSelectedDecor;
import com.tlab.wish.wishes.Decoration;
import com.tlab.wish.wishes.Wish;

import rx.Subscription;

/**
 * Created by andranik on 1/26/16.
 */
public class WishHelper {


    public static Subscription sendNewWish(NewWishPresenter presenter, String content){
        return null; //TODO implement
    }

    private static Wish getWish(String content){
        Wish wish = new Wish();

        wish.setContent(content);
        wish.setDecoration(getDecoration());

        return wish;
    }

    private static Decoration getDecoration(){
        Decoration decoration = new Decoration();

        DecorItem di = LastSelectedDecor.getInstanse().getSelectedDecodeItem();

        if(di instanceof ColorDecorItem){
            decoration.setColor(((ColorDecorItem) di).getColorStr());
        } else {
            //TODO here we will init the PictureDecoration
        }

        return decoration;
    }
}
