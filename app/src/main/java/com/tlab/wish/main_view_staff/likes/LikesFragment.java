package com.tlab.wish.main_view_staff.likes;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tlab.wish.R;
import com.tlab.wish.main_view_staff.wish_list_base.WishListBaseFragment;
import com.tlab.wish.main_view_staff.wish_list_base.WishListBasePresenter;

public class LikesFragment extends WishListBaseFragment implements LikesView{

    public LikesFragment() {
        // Required empty public constructor
    }

    public static LikesFragment newInstance() {
        LikesFragment fragment = new LikesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_likes, container, false);
    }

    @Override
    public WishListBasePresenter createPresenter() {
        return new LikesPresenter();
    }

}
