package com.mobile.umontreal.schedule.schedule;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mobile.umontreal.schedule.objects.CourseSectionSchedule;

import java.util.List;

/**
 * Created by Corneliu on 18-Apr-2015.
 */
public class ScheduleMyCoursesAdapter extends FragmentStatePagerAdapter {

    private int tabNumber;
    private String[] tabTitles;

    List<CourseSectionSchedule> scheduleList;

    public ScheduleMyCoursesAdapter(FragmentManager fragmentManager, String[] tabTitles, List<CourseSectionSchedule> scheduleList) {
        super(fragmentManager);

        this.tabTitles = tabTitles;
        this.tabNumber = tabTitles.length;
        this.scheduleList = scheduleList;
    }

    public int getCount() {
        return tabNumber;
    }


    public Fragment getItem(int position) {
            return ScheduleListFragment.createNewFragmentToDisplay(position, scheduleList.get(position).getSchedule());
    }

    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}
