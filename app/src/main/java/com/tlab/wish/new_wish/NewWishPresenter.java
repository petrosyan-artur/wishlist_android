package com.tlab.wish.new_wish;

import com.tlab.wish.new_wish.decorations.DecorItem;
import com.tlab.wish.new_wish.decorations.LastSelectedDecor;

/**
 * Created by andranik on 1/27/16.
 */
public class NewWishPresenter extends WishPresenter{


    @Override
    public void sendWish(String id, String content){
        super.sendWish(id, content);

        sendWishSub = WishHelper.sendNewWish(this, content);
    }

    @Override
    public void onDecorItemSelected(DecorItem item){
        super.onDecorItemSelected(item);

        LastSelectedDecor.getInstanse().updateSelectedDecor(item);
    }
}
