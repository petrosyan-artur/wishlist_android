package com.tlab.wish.new_wish;

import com.tlab.wish.new_wish.decorations.DecorItem;
import com.tlab.wish.new_wish.decorations.LastSelectedDecor;

/**
 * Created by andranik on 1/27/16.
 */
public class NewWishPresenter extends WishPresenter{


    @Override
    public boolean sendWish(String id, String content){
        boolean send = super.sendWish(id, content);

        if(send) {
            sendWishSub = WishHelper.sendNewWish(this, content);
        }

        return send;
    }

    @Override
    public void onDecorItemSelected(DecorItem item){
        super.onDecorItemSelected(item);

        LastSelectedDecor.getInstanse().updateSelectedDecor(item);
    }
}
