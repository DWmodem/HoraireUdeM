package com.mobile.umontreal.schedule.gui;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mobile.umontreal.schedule.R;
import com.mobile.umontreal.schedule.objects.CourseSectionSchedule;
import com.mobile.umontreal.schedule.objects.SectionType;

import java.util.List;

/**
 * Created by Corneliu on 02-Apr-2015.
 */
public class ScheduleFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private int tabNumber;
    private String[] tabTitles;
    private Context context;
    private int tabIcons[] = {R.mipmap.ic_lab, R.mipmap.ic_exam, R.mipmap.ic_theory};

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

    public CharSequence getPageTitle(int position) {

        // Generate title based on item position
        if (scheduleList.get(position).getCourseSection().getSectionType() == SectionType.Theory) {
            return tabTitles[position];
        } else {
            return tabTitles[position];
        }
    }

}
