package com.sd.nytarticles;


import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements ActionBar.TabListener {

    private static final String TAG = "MainActivity";

    private ViewPager mViewPager;
    private ActionBar mActionBar;

    // Tab titles
    private String[] tabs = { "Most Emailed", "Most Shared", "Most Viewed", "Favourite" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initialization
        mActionBar = getSupportActionBar();
        mViewPager = (ViewPager) findViewById(R.id.activity_main_pager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        // Most Emailed fragment activity
                        return MostEmailedFragment.newInstance(R.layout.fragment_most_mailed, R.id.most_mailed_recycler_view, 1);
                    case 1:
                        // Most Shared fragment activity

                        return MostSharedFragment.newInstance(R.layout.fragment_most_shared, R.id.most_shared_recycler_view, 2);
                    case 2:
                        // Most Viewed fragment activity

                        return MostViewedFragment.newInstance(R.layout.fragment_most_viewed, R.id.most_viewed_recycler_view, 0);
                    case 3:
                        // Favourite fragment activity
                        return new FavouriteFragment();
                }

                return null;
            }

            @Override
            public int getCount() {
                // get item count - equal to number of tabs
                return 4;
            }

        });

        mActionBar.setHomeButtonEnabled(false);
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            mActionBar.addTab(mActionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

        /**
         * on swiping the viewpager make respective tab selected
         * */
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                mActionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

}
