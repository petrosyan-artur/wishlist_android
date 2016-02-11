package com.tlab.wish.main_view_staff.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tlab.wish.R;
import com.tlab.wish.main_view_staff.wish_list_base.WishListBaseFragment;
import com.tlab.wish.main_view_staff.wish_list_base.WishListBasePresenter;
import com.tlab.wish.wishes.Wish;
import com.tlab.wish.wishes.events.WishLikedEvent;
import com.tlab.wish.wishes.events.WishSentEvent;

import java.util.List;

public class HomeFragment extends WishListBaseFragment implements HomeFrView{

    public HomeFragment() {
        super();
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public WishListBasePresenter createPresenter() {
        return new HomeFrPresenter();
    }

    @Override
    public void onEvent(WishSentEvent event) {
        onRefresh();
    }

    @Override
    public void onEvent(WishLikedEvent event) {
        final List<Wish> wishes = adapter.getData();
        final Wish wish = event.getWish();

        if(!wishes.contains(wish)){return;}

        final Wish oldWish = wishes.get(wishes.indexOf(wish));
        oldWish.setLiked(wish.isLiked());
        oldWish.setLikes(wish.getLikes());
        notifyDataSetChanged();
    }
}
