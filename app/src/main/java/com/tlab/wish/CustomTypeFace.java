package com.tlab.wish;

import android.content.Context;
import android.graphics.Typeface;

import com.tlab.wish.utils.ExceptionTracker;

/**
 * Created by andranik on 1/18/16.
 */
public class CustomTypeFace {
    private Context context;
    private Typeface[] typefaces;

    public CustomTypeFace(Context context) {
        this.context = context;
        typefaces = new Typeface[6];

        typefaces[0] = typefaceFromAssets("fonts/roboto_regular.ttf");
        typefaces[1] = typefaceFromAssets("fonts/roboto_light.ttf");
        typefaces[2] = typefaceFromAssets("fonts/roboto_bold.ttf");
        typefaces[3] = typefaceFromAssets("fonts/roboto_italic.ttf");
        typefaces[4] = typefaceFromAssets("fonts/roboto_medium.ttf");
        typefaces[5] = typefaceFromAssets("fonts/roboto-lightItalic.ttf");
    }

    private Typeface typefaceFromAssets(String path){
        Typeface theTypeface = Typeface.DEFAULT;

        try{
            theTypeface = Typeface.createFromAsset(context.getAssets(), path);
        } catch (Exception e){
            ExceptionTracker.trackException(e);
        }

        return theTypeface;
    }

    public Typeface getTypeFace(MyTypeFace typeFace){
        return typefaces[typeFace.getValue()];
    }


    public enum MyTypeFace {
        ROBOTO_REGULAR(0),
        ROBOTO_LIGHT(1),
        ROBOTO_BOLD(2),
        ROBOTO_ITALIC(3),
        ROBOTO_MEDIUM(4),
        ROBOTO_ITALIC_LIGHT(5);

        private int value;

        MyTypeFace(int value){
            this.value = value;
        }

        public int getValue(){
            return value;
        }
    };
}
