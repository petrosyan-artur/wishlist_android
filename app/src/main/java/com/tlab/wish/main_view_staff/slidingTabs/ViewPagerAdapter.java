package com.tlab.wish.main_view_staff.slidingTabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tlab.wish.R;
import com.tlab.wish.main_view_staff.home.HomeFragment;
import com.tlab.wish.main_view_staff.likes.LikesFragment;
import com.tlab.wish.main_view_staff.my_wishes.MyWishesFragment;
import com.tlab.wish.main_view_staff.search.SearchFragment;
import com.tlab.wish.main_view_staff.settings.SettingsFragment;

/**
 * Created by andranik on 1/19/16.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public static int TAB_MAIN = 0;
    public static int TAB_SEARCH = 1;
    public static int TAB_LIKES = 2;
    public static int TAB_MY_WISHES = 3;
    public static int TAB_MY_SETTINGS = 4;

    private static int NumbOfTabs = 5;
    private static int[] icons = {
            R.drawable.tab_home,
            R.drawable.tab_search,
            R.drawable.tab_likes,
            R.drawable.tab_my_wishes,
            R.drawable.tab_settings };

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == TAB_MAIN){
            return HomeFragment.newInstance();
        } else if(position == TAB_SEARCH){
            return SearchFragment.newInstance();
        } else if(position == TAB_LIKES){
            return LikesFragment.newInstance();
        } else if(position == TAB_MY_WISHES){
            return MyWishesFragment.newInstance();
        } else if(position == TAB_MY_SETTINGS){
            return SettingsFragment.newInstance();
        }

        return null;
    }

    public int getIconRes(int position){
        return icons[position];
    }

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
