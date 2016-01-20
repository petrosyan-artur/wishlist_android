package com.tlab.wish.main_view_staff.my_wishes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tlab.wish.R;

public class MyWishesFragment extends Fragment {

    public MyWishesFragment() {
        // Required empty public constructor
    }

    public static MyWishesFragment newInstance() {
        MyWishesFragment fragment = new MyWishesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_wishes, container, false);
    }

}
