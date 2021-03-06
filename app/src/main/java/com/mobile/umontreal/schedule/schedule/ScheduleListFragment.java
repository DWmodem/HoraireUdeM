package com.mobile.umontreal.schedule.schedule;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.mobile.umontreal.schedule.adapters.ScheduleContentAdapter;
import com.mobile.umontreal.schedule.objects.Schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Corneliu on 06-Apr-2015.
 */
public class ScheduleListFragment extends ListFragment {

    private List<Schedule> scheduleList;

    private ScheduleListFragment(List<Schedule> scheduleList){
        this.scheduleList = new ArrayList<Schedule>();
        this.scheduleList.addAll(scheduleList);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * Create a new instance of our fragment, providing "num"
     * as an argument - it determines which page to display
     */
    static ScheduleListFragment createNewFragmentToDisplay(int num, List<Schedule> scheduleList) {
        ScheduleListFragment displayFragment = new ScheduleListFragment(scheduleList);
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        displayFragment.setArguments(args);
        return displayFragment;
    }

    /*called each time the fragment's activity is created - which is
    each time a new page is displayed*/
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new ScheduleContentAdapter(getActivity().getApplicationContext(),getActivity(),
                android.R.layout.simple_list_item_1, scheduleList));

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
//        Toast.makeText(getActivity(), "List " + (mNum + 1) + " selected item: " + (position + 1), Toast.LENGTH_SHORT).show();
    }
}
