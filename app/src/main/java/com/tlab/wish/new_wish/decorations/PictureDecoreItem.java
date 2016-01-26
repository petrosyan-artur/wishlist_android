package com.tlab.wish.new_wish.decorations;

import android.view.View;

import com.tlab.wish.configs.Decorations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andranik on 1/26/16.
 */
public class PictureDecoreItem extends DecorItem implements Serializable{
    static final long serialVersionUID =-1;

    public static List<DecorItem> getItems(Decorations decorations){
        ArrayList<DecorItem> decorItems = new ArrayList<>();

        //TODO

        return decorItems;
    }

    @Override
    public void initDecorViewHolder(DecorAdapter.DecorViewHolder holder) {

    }

    @Override
    public void decorateTheView(View view) {

    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null){ return false; }
        if(!(o instanceof PictureDecoreItem)){ return false; }
        if(o == this){ return true; }

        return false;
    }
}
