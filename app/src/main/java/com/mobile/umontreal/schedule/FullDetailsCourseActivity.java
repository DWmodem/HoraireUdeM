package com.mobile.umontreal.schedule;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.mobile.umontreal.schedule.gui.ScheduleFragmentPagerAdapter;
import com.mobile.umontreal.schedule.misc.Callable;
import com.mobile.umontreal.schedule.misc.MenuHelper;
import com.mobile.umontreal.schedule.objects.CourseSectionSchedule;
import com.mobile.umontreal.schedule.parsing.UDMJsonData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Vector;


public class FullDetailsCourseActivity extends ActionBarActivity
        implements ActionBar.OnNavigationListener, Callable {

    // Log view class
    private String LOG_TAG = FullDetailsCourseActivity.class.getSimpleName();

    // Action bar
    private ActionBar actionBar;

    private ArrayList<String> sectionList;
    private ArrayList<CourseSectionSchedule> courseScheduleList;

    /** Called when the activity is first created. */
    private Context mContext;
    private Vector<View> pages;
    private ViewPager viewPager;

    private Integer callback_count = 0;
    private String[] tabs;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_details_course);

        mContext = this;
        pages = new Vector<View>();


        // Action Bar settings
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        InitializeTabStrip();


//        // Attach the page change listener to tab strip and **not** the view pager inside the activity
//        tabsStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            // This method will be invoked when a new page becomes selected.
//            @Override
//            public void onPageSelected(int position) {
//                Toast.makeText(FullDetailsCourseActivity.this,
//                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();
//            }
//
//            // This method will be invoked when the current page is scrolled
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                // Code goes here
//            }
//
//            // Called when the scroll state changes:
//            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                // Code goes here
//            }
//
//        });
    }

    private void InitializeTabStrip() {
        // Set title in ActionBar
        Bundle extras             = getIntent().getExtras();
        String tagAcronym         = extras.getString(Config.JSON_SIGLE);
        String tagCourseNum       = extras.getString(Config.JSON_COURSE_NUM);
        String tagSession         = extras.getString(Config.JSON_SESSION);
        actionBar.setTitle(tagAcronym + " " + tagCourseNum + "-" + tagSession);

//        // Get the ViewPager and set it's PagerAdapter so that it can display items
//        viewPager = (ViewPager) findViewById(R.id.schedule_viewer);
        sectionList = extras.getStringArrayList(Config.JSON_SECTIONS);

        if (sectionList.size() > 0) {

            courseScheduleList  = new ArrayList<CourseSectionSchedule>();

            // Build a the tabs
            tabs = new String[sectionList.size()];

            // Build a URL for json file
            String url;

            for (int i = 0; i < sectionList.size(); i++) {

                if (!sectionList.get(i).isEmpty()) {

                    tabs[i] = sectionList.get(i);

                    url = Config.URL_API_UDEM +
                            tagSession + "-" +
                            tagAcronym.toLowerCase() + "-" +
                            tagCourseNum + "-" +
                            tabs[i].toUpperCase() + ".json";

                    UDMJsonData data = new UDMJsonData(url);
                    data.execute(this);
                    callback_count ++;
                    pages.add(new ListView(mContext));
                }
            }
        }
    }

    private void InitializeListViews() {
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.schedule_viewer);
        viewPager.setAdapter(new ScheduleFragmentPagerAdapter(getSupportFragmentManager(), tabs, courseScheduleList));


        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabsStrip.setIndicatorColorResource(R.color.color_primary);
        tabsStrip.setDividerColorResource(R.color.white);
        tabsStrip.setViewPager(viewPager);

//        //        Attach the view pager to the tab strip
//        for (int i = 0; i < pages.size(); i++) {
//
//            ListView view = (ListView) pages.get(i);
//
//            view.setAdapter(
//                    new ScheduleContentAdapter(mContext, this,
//                            android.R.layout.simple_list_item_1,
//                            courseScheduleList.get(i).getSchedule()));
//
//            pages.setElementAt(view, i);
//        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Common create options menu code is in MenuHelper
        MenuInflater inflater = getMenuInflater();
        MenuHelper.onCreateOptionsMenu(menu, inflater);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {

        //Common prepare options menu code is in MenuHelper
        MenuHelper.onPrepareOptionsMenu(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return MenuHelper.onOptionsItemSelected(getApplicationContext(), item, this);
        }
    }

    @Override
    public void OnCallback(UDMJsonData data) {

        // Convert JSON to object. Sort the list.

        int i = 0;

        try {
            for (JSONObject csc : data.getItems()) {

                courseScheduleList.add(new CourseSectionSchedule(csc));
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        if (courseScheduleList.size() == 0) {

            Toast.makeText(getApplicationContext(), R.string.DATA_IS_NOT_AVAILABLE,
                    Toast.LENGTH_LONG).show();

        } else {

            Toast.makeText(getApplicationContext(), "" + courseScheduleList.size(),
                    Toast.LENGTH_LONG).show();
        }
        if ((-- callback_count)<= 0)
            InitializeListViews();
    }

    @Override
    public boolean onNavigationItemSelected(int i, long l) {
        return false;
    }
}