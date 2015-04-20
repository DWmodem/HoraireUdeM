package com.mobile.umontreal.schedule.schedule;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mobile.umontreal.schedule.objects.MyCourse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Corneliu on 18-Apr-2015.
 */
public class ScheduleCursorAdapter extends FragmentStatePagerAdapter {

    private int tabNumber;
    private String[] tabTitles;

    List<ArrayList<MyCourse>> courseList;

    public ScheduleCursorAdapter(FragmentManager fragmentManager, String[] tabTitles, List<ArrayList<MyCourse>> courseList) {
        super(fragmentManager);

        this.tabTitles = tabTitles;
        this.tabNumber = tabTitles.length;

        this.courseList = new ArrayList<ArrayList<MyCourse>>();
        this.courseList.addAll(courseList);
    }

    public int getCount() {
        return tabNumber;
    }

    public Fragment getItem(int position) {
        return ScheduleCursorFragment.createNewFragmentToDisplay(position, courseList.get(position));
    }

    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}
