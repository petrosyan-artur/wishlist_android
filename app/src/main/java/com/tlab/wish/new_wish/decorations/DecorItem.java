package com.tlab.wish.new_wish.decorations;

import android.view.View;

import java.io.Serializable;

/**
 * Created by andranik on 1/26/16.
 */
public abstract class DecorItem implements Serializable{
    static final long serialVersionUID =-1;

    private boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public abstract void initDecorViewHolder(DecorAdapter.DecorViewHolder holder);

    public abstract void decorateTheView(View view);

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object o);
}
