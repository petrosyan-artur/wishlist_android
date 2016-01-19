package com.tlab.wish.main_view_staff;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.tlab.wish.R;
import com.tlab.wish.slidingTabs.SlidingTabLayout;
import com.tlab.wish.slidingTabs.ViewPagerAdapter;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.pager)
    ViewPager pager;

    @Bind(R.id.tabs)
    SlidingTabLayout tabs;

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
    }


}
