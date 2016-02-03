package com.tlab.wish.main_view_staff.home;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceFragment;
import com.tlab.wish.App;
import com.tlab.wish.CustomTypeFace;
import com.tlab.wish.R;
import com.tlab.wish.main_view_staff.FragmentInteractionListener;
import com.tlab.wish.utils.MyRecyclerOnScrollListener;
import com.tlab.wish.wishes.Wish;
import com.tlab.wish.wishes.WishesAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment
        extends MvpLceFragment<SwipeRefreshLayout, List<Wish>, HomeFrView, HomeFrPresenter>
        implements HomeFrView, SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.home_rv)
    RecyclerView recyclerView;

    WishesAdapter adapter;

    FragmentInteractionListener fragmentInteractionListener;

    public HomeFragment() {
        // Required empty public constructor
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        contentView.setOnRefreshListener(this);
        contentView.setColorSchemeColors(R.color.colorPrimary);

        errorView.setTypeface(App.getInstance().getTypeface(CustomTypeFace.MyTypeFace.ROBOTO_ITALIC));

        recyclerView.setHasFixedSize(true);
        adapter = new WishesAdapter();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new MyRecyclerOnScrollListener(llm) {
            @Override
            public void onLoadMore(int current_page) {
                presenter.loadMoreWishes(current_page, adapter.getItemCount());
            }

            @Override
            public void onScrollUp() {
                fragmentInteractionListener.onScrollUp();
            }

            @Override
            public void onScrollDown() {
                fragmentInteractionListener.onScrollDown();
            }
        });

        loadData(false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            fragmentInteractionListener = (FragmentInteractionListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException("Main activity must implement FragmentInteractionListener");
        }
    }

    @OnClick(R.id.errorView)
    public void onErroeViewClick(){
        adapter.resetData();
        loadData(false);
    }

    @Override
    public HomeFrPresenter createPresenter() {
        return new HomeFrPresenter();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return presenter.getErrorMessage(e, pullToRefresh);
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    @Override
    public void setData(List<Wish> data) {
        adapter.addData(data, false);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setData(List<Wish> data, boolean fromBegining) {
        adapter.addData(data, fromBegining);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showLoadMoreLoading() {
        adapter.setFooter(new WishesAdapter.Footer());
        adapter.showFooter();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.getWishes(pullToRefresh, adapter.getData());
    }

    @Override
    public void showContent() {
        super.showContent();
        contentView.setRefreshing(false);
        adapter.hideFooter();
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);
        contentView.setRefreshing(false);
        adapter.hideFooter();
        errorView.setText(getErrorMessage(e, pullToRefresh));
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        super.showLoading(pullToRefresh);
        contentView.setRefreshing(pullToRefresh);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
