package com.tlab.wish.main_view_staff.my_wishes;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tlab.wish.R;
import com.tlab.wish.configs.ConfigurationManager;
import com.tlab.wish.main_view_staff.wish_list_base.WishListBaseFragment;
import com.tlab.wish.main_view_staff.wish_list_base.WishListBasePresenter;
import com.tlab.wish.new_wish.EditWish;
import com.tlab.wish.new_wish.EditWishActivity;
import com.tlab.wish.new_wish.WishActivity;
import com.tlab.wish.utils.DialogUtils;
import com.tlab.wish.wishes.Wish;
import com.tlab.wish.wishes.events.WishLikedEvent;
import com.tlab.wish.wishes.events.WishSentEvent;

import java.util.List;

public class MyWishesFragment extends WishListBaseFragment implements MyWishesView{

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

    @Override
    public void onWishItemClicked(Wish wish) {
        presenter.onWishItemClicked(wish);
    }

    @Override
    public void openEditWish(Wish wish) {
        Intent intent = new Intent(getActivity(), EditWishActivity.class);
        intent.putExtra(EditWishActivity.EDIT_WISH_KEY, new EditWish(wish));

        getActivity().startActivityForResult(intent, WishActivity.REQUEST_CODE);
    }

    @Override
    public void showEditError() {
        DialogUtils.showAlertDialog(getActivity(), ConfigurationManager.getInstanse()
                .getConfigs().getMessages().getWishEditAlert());
    }

    @Override
    public void showDeleteWishDialog(Wish wish) {
        DialogUtils.showAlertDialog(getActivity(),
                ConfigurationManager.getInstanse().getConfigs().getMessages().getWishDeleteAlert(),
                R.string.dialog_delete,
                R.string.dialog_cancel,
                (dialog, which) -> presenter.deleteWish(wish));
    }
}
