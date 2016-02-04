package com.tlab.wish.main_view_staff.my_wishes;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tlab.wish.R;
import com.tlab.wish.main_view_staff.wish_list_base.WishListBaseFragment;
import com.tlab.wish.main_view_staff.wish_list_base.WishListBasePresenter;

public class MyWishesFragment extends WishListBaseFragment {

    public MyWishesFragment() {
        // Required empty public constructor
    }

    public static MyWishesFragment newInstance() {
        MyWishesFragment fragment = new MyWishesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_wishes, container, false);
    }

    @Override
    public WishListBasePresenter createPresenter() {
        return new MyWishesPresenter();
    }

}
