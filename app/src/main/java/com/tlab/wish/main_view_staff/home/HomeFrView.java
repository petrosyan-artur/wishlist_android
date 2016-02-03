package com.tlab.wish.main_view_staff.home;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;
import com.tlab.wish.wishes.Wish;

import java.util.List;

/**
 * Created by andranik on 2/3/16.
 */
public interface HomeFrView extends MvpLceView<List<Wish>>{

    void setData(List<Wish> data, boolean fromBegining);

    void showLoadMoreLoading();

}
