package com.tlab.wish.main_view_staff;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.tlab.wish.App;
import com.tlab.wish.R;
import com.tlab.wish.authentication.AuthActivity;
import com.tlab.wish.authentication.AuthSuccessEvent;
import com.tlab.wish.main_view_staff.slidingTabs.SlidingTabLayout;
import com.tlab.wish.main_view_staff.slidingTabs.ViewPagerAdapter;
import com.tlab.wish.new_wish.NewWishActivity;
import com.tlab.wish.new_wish.WishActivity;
import com.tlab.wish.utils.DialogUtils;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class MainActivity extends MvpActivity<MainView, MainPresenter> implements MainView, FragmentInteractionListener {

    @Bind(R.id.pager)
    ViewPager pager;

    @Bind(R.id.tabs)
    SlidingTabLayout tabs;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    ViewPagerAdapter adapter;

    @BindColor(R.color.colorPrimaryDark)
    int colorTabDark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initTabs();
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }


    private void initTabs(){
        adapter =  new ViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);

        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return colorTabDark;
            }
        });

        tabs.setViewPager(pager);
        tabs.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                presenter.onPageSelected(position);
            }
        });
    }

    @OnClick(R.id.fab)
    public void onFabClicked(){
        if(!App.getInstance().isOnline()){
            DialogUtils.showOfflineDialog(this);
            return;
        }

        presenter.onFabClicked();
    }

    @Override
    public void showFab() {
        fab.show();
//        if(fab.getVisibility() != View.VISIBLE) {
//            new SlideInAnimation(fab)
//                    .setDirection(Animation.DIRECTION_RIGHT)
//                    .setDuration(250)
//                    .setInterpolator(new DecelerateInterpolator())
//                    .animate();
//        }
    }

    @Override
    public void hideFab() {
        fab.hide();
//        new SlideOutAnimation(fab)
//                .setDirection(Animation.DIRECTION_RIGHT)
//                .setDuration(250)
//                .setInterpolator(new AccelerateInterpolator())
//                .animate();
    }

    @Override
    public void openNewWish() {
        startActivityForResult(new Intent(this, NewWishActivity.class), WishActivity.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == AuthActivity.AUTH_REQUET_CODE && resultCode == RESULT_OK){
            EventBus.getDefault().postSticky(new AuthSuccessEvent());
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onScrollUp() {
        showFab();
    }

    @Override
    public void onScrollDown() {
        hideFab();
    }
}
