package com.tlab.wish.main_view_staff.wish_list_base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.tlab.wish.main_view_staff.user_wishes.UserWishesFragment;
import com.tlab.wish.utils.DialogUtils;
import com.tlab.wish.utils.MyRecyclerOnScrollListener;
import com.tlab.wish.wishes.Wish;
import com.tlab.wish.wishes.WishesAdapter;
import com.tlab.wish.wishes.events.WishDeletedEvent;
import com.tlab.wish.wishes.events.WishEditedEvent;
import com.tlab.wish.wishes.events.WishLikedEvent;
import com.tlab.wish.wishes.events.WishSentEvent;

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
        implements WishListBaseView, SwipeRefreshLayout.OnRefreshListener, WishesAdapter.WishItemClickListener,
                    NewWishsTracker.HasNewWishesListener{

    @Bind(R.id.authErrorView)
    View authErrorView;

    @Bind(R.id.authErrorTv)
    TextView authErrorTv;

    @Bind(R.id.wish_list_rv)
    RecyclerView recyclerView;

    @Bind(R.id.has_new_wishes)
    TextView hasNewWishesTv;

    protected WishesAdapter adapter;

    FragmentInteractionListener fragmentInteractionListener;

    MyRecyclerOnScrollListener myRecyclerOnScrollListener;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        presenter.onViewCreated();

        EventBus.getDefault().register(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        setupMainLayout(activity);

        try{
            fragmentInteractionListener = (FragmentInteractionListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException("Main activity must implement FragmentInteractionListener");
        }
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(adapter != null) {
            presenter.startTrackingForNewWishes(this, adapter.getData());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stopTrackingForNewWishes();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    protected void setupMainLayout(Activity activity){
        activity.findViewById(R.id.main_tabs_layout).setVisibility(View.VISIBLE);
        activity.findViewById(R.id.main_secondary_layout).setVisibility(View.GONE);
    }

    protected void setupSecondaryLayout(Activity activity){
        activity.findViewById(R.id.main_tabs_layout).setVisibility(View.GONE);
        activity.findViewById(R.id.main_secondary_layout).setVisibility(View.VISIBLE);
    }

    public abstract void onEvent(WishSentEvent event);

    public abstract void onEvent(WishLikedEvent event);

    public void onEvent(WishEditedEvent event){
        final List<Wish> wishes = adapter.getData();
        final Wish wish = event.getResponse().getWish();

        if(!wishes.contains(wish)){return;}

        final Wish oldWish = wishes.get(wishes.indexOf(wish));
        oldWish.setContent(wish.getContent());
        oldWish.setDecoration(wish.getDecoration());
        notifyDataSetChanged();
    }

    public void onEvent(WishDeletedEvent event){
        adapter.removeWish(event.getWish());
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
        hasNewWishesTv.setTypeface(App.getInstance().getTypeface(CustomTypeFace.MyTypeFace.ROBOTO_REGULAR));

        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);

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

        contentView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        adapter = new WishesAdapter(this, myRecyclerOnScrollListener);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);



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
    public void showToastMessage(int messageId) {
        showToastMessage(getString(messageId));
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return presenter.getErrorMessage(e, pullToRefresh);
    }

    @OnClick(R.id.has_new_wishes)
    public void onHasNewWishesClick(){
        onRefresh();
        hasNewWishesTv.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        if(myRecyclerOnScrollListener != null) {
            myRecyclerOnScrollListener.reset();
        }
        loadData(true);
    }

    @Override
    public void setData(List<Wish> data) {
        setData(data, false);
    }

    @Override
    public void setData(List<Wish> data, boolean fromBegining) {
        adapter.addData(data, fromBegining);
        presenter.updateWihesList(adapter.getData());
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
    public void openUserWishes(Wish wish) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.main_container, UserWishesFragment.newInstanse(wish.getUserId(), wish.getUsername()));
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.addToBackStack(null);

        ft.commit();

    }

    @Override
    public void onWishItemLongClicked(Wish wish) {
        presenter.onWishItemLongClicked(wish);
    }

    @Override
    public void onHasNewWishes(HasNewWishResponse response) {
        hasNewWishesTv.setVisibility(View.VISIBLE);
    }
}
