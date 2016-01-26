package com.tlab.wish.new_wish.decorations;

import android.graphics.Color;
import android.view.View;

import com.tlab.wish.configs.Decorations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andranik on 1/26/16.
 */
public class ColorDecorItem extends DecorItem implements Serializable{
    static final long serialVersionUID =-1;

    public static List<DecorItem> getItems(Decorations decorations){
        ArrayList<DecorItem> decorItems = new ArrayList<>();

        for(String color : decorations.getColors()){
            DecorItem selectedItem = LastSelectedDecor.getInstanse().getSelectedDecodeItem();
            ColorDecorItem item = new ColorDecorItem(color);

            if(item.equals(selectedItem)){
                item.setSelected(true);
            }

            decorItems.add(item);
        }

        return decorItems;
    }

    private String colorStr;
    private int color;

    public ColorDecorItem(String colorStr) {
        this.colorStr = colorStr;
        this.color = getColor(colorStr);
    }

    @Override
    public void initDecorViewHolder(DecorAdapter.DecorViewHolder holder) {
        holder.imageView.setBackgroundColor(color);
    }

    @Override
    public void decorateTheView(View view) {
        view.setBackgroundColor(color);
    }

    @Override
    public int hashCode() {
        return getColorStr().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == null){ return false; }
        if(!(o instanceof ColorDecorItem)){ return false; }
        if(o == this){ return true; }

        ColorDecorItem other = (ColorDecorItem)o;

        return other.getColorStr().equals(getColorStr());
    }


    public String getColorStr() {
        return colorStr;
    }

    private int getColor(String colorStr){
        String[] colorStrs = colorStr.split(",");

        int[] rgb = new int[]{
                Integer.parseInt(colorStrs[0]),
                Integer.parseInt(colorStrs[1]),
                Integer.parseInt(colorStrs[2])
        };

        return Color.rgb(rgb[0], rgb[1], rgb[2]);
    }
}
