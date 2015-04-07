package com.mobile.umontreal.schedule.gui;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mobile.umontreal.schedule.objects.CourseSectionSchedule;

import java.util.List;

/**
 * Created by Corneliu on 02-Apr-2015.
 */
public class ScheduleFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private int tabNumber;
    private String[] tabTitles;
    private Context context;
    List<CourseSectionSchedule> scheduleList;

    public ScheduleFragmentPagerAdapter(FragmentManager fragmentManager, String[] tabTitles, List<CourseSectionSchedule> scheduleList) {
        super(fragmentManager);

        this.tabTitles = tabTitles;
        this.tabNumber = tabTitles.length;
        this.scheduleList = scheduleList;
    }

    public int getCount() {
        return tabNumber;
    }


    public Fragment getItem(int position) {
        return ArrayListFragment.createNewFragmentToDisplay(position, scheduleList.get(position).getSchedule());
    }

//    @Override
//    public ListFragment getItem(int position) {
//        return ScheduleFragment.createNewFragmentToDisplay(position);
//    }

//    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

}
