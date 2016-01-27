package com.tlab.wish.new_wish;

import com.tlab.wish.new_wish.decorations.ColorDecorItem;
import com.tlab.wish.new_wish.decorations.DecorItem;
import com.tlab.wish.new_wish.decorations.PictureDecoreItem;
import com.tlab.wish.wishes.Decoration;
import com.tlab.wish.wishes.Wish;

import java.io.Serializable;

/**
 * Created by andranik on 1/27/16.
 */
public class EditWish implements Serializable{
    static final long serialVersionUID =-1;

    private String id;
    private String content;
    private DecorItem decorItem;

    public EditWish(Wish wish) {
        id = wish.getId();
        content = wish.getContent();
        initDecorItem(wish);
    }

    private void initDecorItem(Wish wish){
        Decoration dec = wish.getDecoration();

        if(dec.getImage() == null || dec.getImage().length() == 0){
            decorItem = new ColorDecorItem(dec.getColor());
        } else {
            decorItem = new PictureDecoreItem();
            //TODO init as image decorator properly
        }
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public DecorItem getDecorItem() {
        return decorItem;
    }
}
