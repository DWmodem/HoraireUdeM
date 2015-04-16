package com.mobile.umontreal.schedule;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.mobile.umontreal.schedule.db.UDMDatabaseManager;
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
        implements ActionBar.OnNavigationListener, Callable{

    // Log view class
    private String LOG_TAG = FullDetailsCourseActivity.class.getSimpleName();

    // Action bar
    private ActionBar actionBar;

    // Data Base
    UDMDatabaseManager dbh;
    SQLiteDatabase db;

    private ArrayList<String> sectionList;
    private ArrayList<CourseSectionSchedule> courseScheduleList;

    /** Called when the activity is first created. */
    private Context mContext;
    private Vector<View> pages;
    private TextView courseCurrentSection;
    private TextView courseTitle;

    private Integer callback_count = 0;
    private String[] tabs;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_details_course);
//
//        if (savedInstanceState == null) {
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            FloatingActionButtonBasicFragment fragment = new FloatingActionButtonBasicFragment();
//            transaction.replace(R.id.sample_content_fragment, fragment);
//            transaction.commit();
//        }

        mContext = this;
        pages = new Vector<View>();

        // Action Bar settings
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        // Set name of course
        courseTitle = (TextView) findViewById(R.id.course_title);
        courseCurrentSection = (TextView) findViewById(R.id.course_section);

        InitializeTabStrip();
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
        courseTitle.setText(extras.getString(Config.JSON_COURSE_TITLE));

        if (sectionList.size() > 0) {

            courseScheduleList  = new ArrayList<CourseSectionSchedule>();
            courseCurrentSection.setText(getString(R.string.course_section) + ": " +
                    sectionList.get(0));

            // Build a the tabs
            tabs = new String[sectionList.size()];

            // Build a URL for json file
            String url;
//            String tabTitle = this.getResources().getString(R.string.course_section);

            for (int i = 0; i < sectionList.size(); i++) {

                if (!sectionList.get(i).isEmpty()) {

                    tabs[i] = sectionList.get(i);

                    //Get the file containing the relevant data
                    url = Config.URL_API_UDEM +
                            tagSession + "-" +                      //Eg: H15
                            tagAcronym.toLowerCase() + "-" +        //Eg: ift
                            tagCourseNum + "-" +                    //Eg: 1015
                            tabs[i].toUpperCase() + ".json";        //Eg: A102      ----> H15-ift-1015-A102.json


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
        tabsStrip.setIndicatorColorResource(R.color.white);
        tabsStrip.setDividerColorResource(R.color.white);
        tabsStrip.setViewPager(viewPager);
        tabsStrip.setShouldExpand(true);

        // Attach the page change listener to tab strip and **not** the view pager inside the activity
        tabsStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                    courseCurrentSection.setText(getString(R.string.course_section) + ": " +
                            sectionList.get(position).substring(0, 1));
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }

        });
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
        try {
            for (JSONObject csc : data.getItems())
                courseScheduleList.add(new CourseSectionSchedule(csc));

            if ((-- callback_count)<= 0)
                InitializeListViews();

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        if (callback_count == 0 && courseScheduleList.size() == 0) {

            Toast.makeText(getApplicationContext(), R.string.DATA_IS_NOT_AVAILABLE,
                    Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public boolean onNavigationItemSelected(int i, long l) {
        return false;
    }

    public void buttonClick(View v) {

       dbh = new  UDMDatabaseManager(mContext);
        Toast.makeText(getApplicationContext(), "You have clicked \"add the course\"  ",
                Toast.LENGTH_SHORT).show();





    }


}