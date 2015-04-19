package com.mobile.umontreal.schedule.schedule;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mobile.umontreal.schedule.objects.CourseSectionSchedule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Corneliu on 02-Apr-2015.
 */
public class ScheduleListAdapter extends FragmentStatePagerAdapter {

    private int tabNumber;
    private String[] tabTitles;

    List<CourseSectionSchedule> scheduleList;

    public ScheduleListAdapter(FragmentManager fragmentManager,
                               String[] tabTitles, List<CourseSectionSchedule> scheduleList) {
        super(fragmentManager);

        this.tabTitles = tabTitles;
        this.tabNumber = tabTitles.length;
        this.scheduleList = new ArrayList<CourseSectionSchedule>();
        this.scheduleList.addAll(scheduleList);
    }

    public int getCount() {
         return tabNumber;
    }


    public Fragment getItem(int position) {
         return ScheduleListFragment.createNewFragmentToDisplay(position, scheduleList.get(position).getSchedule());
    }

    public CharSequence getPageTitle(int position) {

        // Generate title based on item position
        // scheduleList.get(position).getCourseSection().getSectionType() == SectionType.Theory
        if (tabTitles[position].length() == 1) {
            return tabTitles[position];
        } else {
            return scheduleList.get(position).getSchedule().get(0).getDescription() +
                    " ( " + tabTitles[position] + " )";
        }
    }

}
