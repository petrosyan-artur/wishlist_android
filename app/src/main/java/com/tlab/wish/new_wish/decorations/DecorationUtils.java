package com.tlab.wish.new_wish.decorations;

import android.graphics.Color;

/**
 * Created by andranik on 2/2/16.
 */
public class DecorationUtils {

    public static int getColor(String colorStr){
        String[] colorStrs = colorStr.split(",");

        int[] rgb = new int[]{
                Integer.parseInt(colorStrs[0]),
                Integer.parseInt(colorStrs[1]),
                Integer.parseInt(colorStrs[2])
        };

        return Color.rgb(rgb[0], rgb[1], rgb[2]);
    }
}
