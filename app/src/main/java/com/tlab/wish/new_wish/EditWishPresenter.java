package com.tlab.wish.new_wish;

import com.tlab.wish.new_wish.decorations.DecorItem;

/**
 * Created by andranik on 1/27/16.
 */
public class EditWishPresenter extends WishPresenter{

    private DecorItem decorItem;

    @Override
    public void onDecorItemSelected(DecorItem item) {
        super.onDecorItemSelected(item);

        this.decorItem = item;
    }

    @Override
    public boolean sendWish(String id, String content) {
        boolean send = super.sendWish(id, content);

        if(send) {
            sendWishSub = WishHelper.updateWish(this, id, content, decorItem);
        }

        return send;
    }
}
