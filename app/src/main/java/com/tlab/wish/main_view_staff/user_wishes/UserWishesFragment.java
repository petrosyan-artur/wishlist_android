package com.tlab.wish.main_view_staff.user_wishes;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tlab.wish.R;
import com.tlab.wish.main_view_staff.wish_list_base.WishListBaseFragment;
import com.tlab.wish.main_view_staff.wish_list_base.WishListBasePresenter;
import com.tlab.wish.utils.ViewUtils;
import com.tlab.wish.wishes.Wish;
import com.tlab.wish.wishes.events.WishLikedEvent;
import com.tlab.wish.wishes.events.WishSentEvent;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserWishesFragment extends WishListBaseFragment implements UserWishesView{

    private static final String USER_ID_KEY = "user_id_key";
    private static final String USER_NAME_KEY = "user_name_key";

    private String userId;
    private String userName;

    public static UserWishesFragment newInstanse(String userId, String userName){
        UserWishesFragment fragment = new UserWishesFragment();
        Bundle bundle = new Bundle();
        bundle.putString(USER_ID_KEY, userId);
        bundle.putString(USER_NAME_KEY, userName);
        fragment.setArguments(bundle);

        return fragment;
    }

    public UserWishesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        userId = getArguments().getString(USER_ID_KEY);
        userName = getArguments().getString(USER_NAME_KEY);

        configureToolbar();

        return inflater.inflate(R.layout.fragment_user_wishes, container, false);
    }

    private void configureToolbar() {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.main_toolbar);
        ViewUtils.configureToolbar((AppCompatActivity) getActivity(), toolbar, R.drawable.toolbar_close);
        toolbar.setTitle(userName);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        setupSecondaryLayout(activity);
    }

    @Override
    public void onDetach() {
        setupMainLayout(getActivity());
        super.onDetach();
    }

    @Override
    public WishListBasePresenter createPresenter() {
        return new UserWishesPresenter(userId);
    }

    @Override
    public void onEvent(WishSentEvent event) {
    }

    @Override
    public void onEvent(WishLikedEvent event) {
    }

    @Override
    public void openUserWishes(Wish wish) {
        // We don't need any action on this event here
    }
}
