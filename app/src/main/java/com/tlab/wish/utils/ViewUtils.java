package com.tlab.wish.utils;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.tlab.wish.App;
import com.tlab.wish.CustomTypeFace;

/**
 * Created by andranik on 1/21/16.
 */
public class ViewUtils {

    public static Toolbar configureToolbar(final AppCompatActivity activity, Toolbar toolbar, int navIcon){
        activity.setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(navIcon);

        toolbar.setTitleTextColor(Color.WHITE);

        for(int i = 0; i < toolbar.getChildCount(); i++){
            View child = toolbar.getChildAt(i);
            if(child instanceof TextView){
                ((TextView)child).setTypeface(App.getInstance().getTypeface(CustomTypeFace.MyTypeFace.ROBOTO_MEDIUM));
                break;
            }
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });

        return toolbar;
    }
}
