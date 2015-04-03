package com.mobile.umontreal.schedule.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mobile.umontreal.schedule.gui.ScheduleFragment;

/**
 * Created by Corneliu on 02-Apr-2015.
 */
public class ScheduleFragmentPagerAdapter extends FragmentPagerAdapter {

    private int tabNumber;

    private String[] tabTitles;

    public ScheduleFragmentPagerAdapter(FragmentManager fm, String[] tabTitles) {
        super(fm);

        this.tabTitles = tabTitles;
        this.tabNumber = tabTitles.length;
    }

    @Override
    public int getCount() {
        return tabNumber;
    }

    @Override
    public Fragment getItem(int position) {
        return ScheduleFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}
