package com.tlab.wish.main_view_staff.wish_list_base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceFragment;
import com.tlab.wish.App;
import com.tlab.wish.CustomTypeFace;
import com.tlab.wish.R;
import com.tlab.wish.authentication.AuthActivity;
import com.tlab.wish.authentication.AuthSuccessEvent;
import com.tlab.wish.main_view_staff.FragmentInteractionListener;
import com.tlab.wish.utils.DialogUtils;
import com.tlab.wish.utils.MyRecyclerOnScrollListener;
import com.tlab.wish.wishes.Wish;
import com.tlab.wish.wishes.WishSentEvent;
import com.tlab.wish.wishes.WishesAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by andranik on 2/4/16.
 */
public abstract class WishListBaseFragment
        extends MvpLceFragment<SwipeRefreshLayout, List<Wish>, WishListBaseView, WishListBasePresenter<WishListBaseView>>
        implements WishListBaseView, SwipeRefreshLayout.OnRefreshListener, WishesAdapter.WishItemClickListener{

    @Bind(R.id.authErrorView)
    View authErrorView;

    @Bind(R.id.authErrorTv)
    TextView authErrorTv;

    @Bind(R.id.wish_list_rv)
    RecyclerView recyclerView;

    WishesAdapter adapter;

    FragmentInteractionListener fragmentInteractionListener;

    MyRecyclerOnScrollListener myRecyclerOnScrollListener;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        presenter.onViewCreated();
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

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().registerSticky(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onEvent(WishSentEvent event){
        adapter.updateWish(event.getResponse().getWish());
        loadData(true);
        EventBus.getDefault().removeStickyEvent(event);
    }

    public void onEvent(AuthSuccessEvent event){
        initViews();
    }

    @Override
    public void initViews() {
        authErrorView.setVisibility(View.GONE);

        contentView.setOnRefreshListener(this);
        contentView.setColorSchemeColors(R.color.colorPrimary);

        errorView.setTypeface(App.getInstance().getTypeface(CustomTypeFace.MyTypeFace.ROBOTO_ITALIC));

        contentView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        adapter = new WishesAdapter(this);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);

        myRecyclerOnScrollListener = new MyRecyclerOnScrollListener(llm) {
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
        };

        recyclerView.addOnScrollListener(myRecyclerOnScrollListener);

        loadData(false);
    }

    @OnClick(R.id.errorView)
    public void onErrorViewClick(){
        adapter.resetData();
        loadData(false);
    }

    @OnClick(R.id.authErrorBtn)
    public void onAuthErrorBtnClick(){
        openAuthActivity();
    }

    @Override
    public void openAuthActivity() {
        getActivity().startActivityForResult
                (new Intent(getActivity(), AuthActivity.class),
                        AuthActivity.AUTH_REQUET_CODE);
    }

    @Override
    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showLikeError() {
        Toast.makeText(getActivity(), R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return presenter.getErrorMessage(e, pullToRefresh);
    }

    @Override
    public void onRefresh() {
        myRecyclerOnScrollListener.reset();
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
    public void showAuthError() {
        contentView.setVisibility(View.GONE);

        authErrorTv.setTypeface(App.getInstance().getTypeface(CustomTypeFace.MyTypeFace.ROBOTO_ITALIC));
        authErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading(boolean pullToRefresh) {
        super.showLoading(pullToRefresh);
        contentView.setRefreshing(pullToRefresh);
    }

    @Override
    public void showOfflineError() {
        DialogUtils.showOfflineDialog(getActivity());
    }

    @Override
    public void onWishItemClicked(Wish wish) {
        presenter.onWishItemClicked(wish);
    }

    @Override
    public void onWishLikeClicked(Wish wish) {
        presenter.onWishLikeClicked(wish);
    }

    @Override
    public void onWishUserNameClicked(Wish wish) {
        presenter.onWishUserNameClicked(wish);
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
