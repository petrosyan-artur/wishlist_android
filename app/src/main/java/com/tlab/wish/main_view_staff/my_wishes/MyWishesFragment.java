package com.tlab.wish.main_view_staff.my_wishes;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tlab.wish.R;
import com.tlab.wish.main_view_staff.wish_list_base.WishListBaseFragment;
import com.tlab.wish.main_view_staff.wish_list_base.WishListBasePresenter;
import com.tlab.wish.new_wish.EditWish;
import com.tlab.wish.new_wish.EditWishActivity;
import com.tlab.wish.new_wish.WishActivity;
import com.tlab.wish.wishes.Wish;

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

    @Override
    public void onWishItemClicked(Wish wish) {
        super.onWishItemClicked(wish);
        Intent intent = new Intent(getActivity(), EditWishActivity.class);
        intent.putExtra(EditWishActivity.EDIT_WISH_KEY, new EditWish(wish));


        getActivity().startActivityForResult(intent, WishActivity.REQUEST_CODE);
    }
}
