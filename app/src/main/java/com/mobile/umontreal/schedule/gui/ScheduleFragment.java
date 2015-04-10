package com.mobile.umontreal.schedule.gui;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobile.umontreal.schedule.R;

import java.util.ArrayList;

/**
 * Created by Corneliu on 02-Apr-2015.
 */
// In this case, the fragment displays simple text based on the page
public class ScheduleFragment extends ListFragment {

    private String LOG_TAG = ScheduleFragment.class.getSimpleName();
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    private static ArrayList<String> sectionsList;

//    public static ScheduleFragment newInstance(int page) {
//        Bundle args = new Bundle();
//        args.putInt(ARG_PAGE, page);
//        ScheduleFragment fragment = new ScheduleFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }

    /**
     * Create a new instance of our fragment, providing "num"
     * as an argument - it determines which page to display
     */
    public static ScheduleFragment createNewFragmentToDisplay(int num) {
        ScheduleFragment displayFragment = new ScheduleFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        displayFragment.setArguments(args);
        return displayFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_schedule, container, false);

        TextView scheduleTitle = (TextView) view.findViewById(R.id.schedule_prof);
        scheduleTitle.setText("Title plus dddddddddd" + getArguments().toString());
        return view;
    }


}