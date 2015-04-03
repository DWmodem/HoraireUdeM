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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.mobile.umontreal.schedule.adapters.CustomPagerAdapter;
import com.mobile.umontreal.schedule.adapters.ScheduleFragmentPagerAdapter;
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
    private ListView scheduleViewList;

    private ArrayList<String> sectionList;
    // List view Adapter
    private ArrayAdapter<CourseSectionSchedule> scheduleAdapter;
    private ArrayList<CourseSectionSchedule> scheduleList;

    // Session name
     private CourseSectionSchedule courseSectionSchedule;

    /** Called when the activity is first created. */
    private Context mContext;
    private Vector<View> pages;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_details_course);

        mContext = this;
        pages = new Vector<View>();

        ListView listview1 = new ListView(mContext);
        ListView listview2 = new ListView(mContext);
        ListView listview3 = new ListView(mContext);

        pages.add(listview1);
        pages.add(listview2);
        pages.add(listview3);

        // Action Bar settings
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        // Set title in ActionBar
        Bundle extras = getIntent().getExtras();
        String tagAcronym = extras.getString(Config.JSON_SIGLE);
        String tagCourseNum = extras.getString(Config.JSON_COURSE_NUM);
        String tagSession = extras.getString(Config.JSON_SESSION);
        actionBar.setTitle(tagAcronym + " " + tagCourseNum + "-" + tagSession);

        scheduleViewList = (ListView) findViewById(R.id.list_view_courses);

//        if (savedInstanceState == null) {
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            SlidingTabsColorsFragment fragment = new SlidingTabsColorsFragment();
//            transaction.replace(R.id.schedule_content, fragment);
//            transaction.commit();
//        }

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.schedule_viewer);

        sectionList = extras.getStringArrayList(Config.JSON_SECTIONS);

        if (sectionList.size() > 0) {

            // Build a the tabs
            String[] tabs = new String[sectionList.size()];

            // Build a URL for json file
            String url;

            for (int i = 0; i < sectionList.size(); i++) {

                if (!sectionList.get(i).isEmpty()) {

                    tabs[i] = getResources().getString(R.string.course_section,"string",getResources()) + " " + sectionList.get(i);

                    url = Config.URL_API_UDEM +
                            tagSession + "-" +
                            tagAcronym.toLowerCase() + "-" +
                            tagCourseNum + "-" +
                            tabs[i].toUpperCase() + ".json";

//                    UDMJsonData data = new UDMJsonData(url);
//                    data.execute(this);

                }

            }

            viewPager.setAdapter(new ScheduleFragmentPagerAdapter(getSupportFragmentManager(),tabs));
            // Give the PagerSlidingTabStrip the ViewPager
        }

        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabsStrip.setIndicatorColorResource(R.color.color_primary);
        tabsStrip.setDividerColorResource(R.color.white);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);








        CustomPagerAdapter adapter = new CustomPagerAdapter(mContext,pages);
        viewPager.setAdapter(adapter);

        listview1.setAdapter(
                new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,
                new String[]{"2014-09-04  1340 Pav. Andre-Aisenstadt","jeudi","C1","D1"}));
        listview2.setAdapter(
                new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,
                new String[]{"2014-09-04","Philippe Langlais","C2","D2"}));
        listview3.setAdapter(
                new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1,
                new String[]{"Personnel","B3","C3","D3"}));

        // Attach the page change listener to tab strip and **not** the view pager inside the activity
        tabsStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                Toast.makeText(FullDetailsCourseActivity.this,
                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();
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
                courseSectionSchedule = new CourseSectionSchedule(csc);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        if (courseSectionSchedule == null) {

            Toast.makeText(getApplicationContext(), R.string.DATA_IS_NOT_AVAILABLE,
                    Toast.LENGTH_LONG).show();

        } else {

            Toast.makeText(getApplicationContext(), courseSectionSchedule.getSchedule().size(),
                    Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public boolean onNavigationItemSelected(int i, long l) {
        return false;
    }
}