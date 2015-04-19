package com.mobile.umontreal.schedule.schedule;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.mobile.umontreal.schedule.adapters.ScheduleMyCoursesAdapter;
import com.mobile.umontreal.schedule.objects.MyCourse;

import java.util.List;

/**
 * Created by Corneliu on 06-Apr-2015.
 */
public class ScheduleCursorFragment extends ListFragment {

    private List<MyCourse> courseList;

    private ScheduleCursorFragment(List<MyCourse> courseList){
        this.courseList = courseList;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Create a new instance of our fragment, providing "num"
     * as an argument - it determines which page to display
     */
    static ScheduleCursorFragment createNewFragmentToDisplay(int num, List<MyCourse> courseList) {
        ScheduleCursorFragment displayFragment = new ScheduleCursorFragment(courseList);

        Bundle args = new Bundle();
        displayFragment.setArguments(args);
        return displayFragment;
    }

    /*called each time the fragment's activity is created - which is
    each time a new page is displayed*/
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new ScheduleMyCoursesAdapter(getActivity().getApplicationContext(),getActivity(),
                android.R.layout.simple_list_item_1, courseList));

    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
//        Toast.makeText(getActivity(), "List " + (mNum + 1) + " selected item: " + (position + 1), Toast.LENGTH_SHORT).show();
    }
}
