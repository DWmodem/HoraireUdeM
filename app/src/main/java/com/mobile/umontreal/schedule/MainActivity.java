package com.mobile.umontreal.schedule;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.mobile.umontreal.schedule.db.UDMDatabaseManager;
import com.mobile.umontreal.schedule.googleI.GoogleIntegrationManager;
import com.mobile.umontreal.schedule.misc.ConnectionDetector;
import com.mobile.umontreal.schedule.misc.MenuHelper;
import com.mobile.umontreal.schedule.objects.CourseSectionSchedule;

import java.util.ArrayList;
import java.util.Vector;


/**
 * Main Activity
 * */
public class MainActivity extends ActionBarActivity {

    private String LOG_TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;

    // Connection Internet Detector
    private ConnectionDetector connection;

    // Data Base
    UDMDatabaseManager dataBase;

    /** Called when the activity is first created. */
    private ArrayList<CourseSectionSchedule> courseList;
    private Context mContext;
    private Vector<View> pages;
    private String[] tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Connection DataBase
        dataBase = new UDMDatabaseManager(this);
        mContext = this;
        pages = new Vector<View>();

        if (!dataBase.isEmpty()) {

            // IF DataBase is not empty
            setContentView(R.layout.activity_main);

            Cursor session = dataBase.getCourses(new String[]{
                            UDMDatabaseManager.C_TITRE,
                            UDMDatabaseManager.C_TRIMESTRE
                    },
                    null,                                   // The columns for the WHERE clause
                    null,                                   // The values for the WHERE clause
                    UDMDatabaseManager.C_TRIMESTRE,         // Group the rows
                    null,                                   // Filter by row groups
                    UDMDatabaseManager.C_ID + " DESC",      // The sort order
                    null);                                  // The limit);

            // Build a the tabs
            int tabsNumber = session.getCount();
            int i = 0;

            if (tabsNumber > 0) {

                tabs = new String[tabsNumber];
                courseList = new ArrayList<CourseSectionSchedule>();


                if (session.moveToFirst()) {
                    do {

                        String sessionName = session.getString(session.getColumnIndexOrThrow(UDMDatabaseManager.C_TRIMESTRE));
                        tabs[i] = sessionName;

                        session.moveToNext();
                        pages.add(new ListView(mContext));

                        Cursor course = dataBase.getCourses(new String[]{
                            UDMDatabaseManager.C_SIGLE,
                            UDMDatabaseManager.C_COURSNUM,
                            UDMDatabaseManager.C_SECTION,
                            UDMDatabaseManager.C_TITRE,
                            UDMDatabaseManager.C_TRIMESTRE,
                            UDMDatabaseManager.C_STATUS,
                            UDMDatabaseManager.C_ABANDON
                        },
                            UDMDatabaseManager.C_TRIMESTRE + " LIKE ?", // The columns for the WHERE clause
                            new String[]{ sessionName },                // The values for the WHERE clause
                            null,                                       // Group the rows
                            null,                                       // Filter by row groups
                            UDMDatabaseManager.C_TITRE + " ASC",        // The sort order
                            null);

                        Toast.makeText(getApplicationContext(),
                                "" + course.getCount() + "courses in DB...",
                                Toast.LENGTH_LONG).show();
                    } while (session.moveToNext());
                }
                session.close();


                // Get the ViewPager and set it's PagerAdapter so that it can display items
//                ViewPager viewPager = (ViewPager) findViewById(R.id.schedule_viewer);
//                viewPager.setAdapter(new ScheduleMyCoursesAdapter(getSupportFragmentManager(), tabs, courseList));
////
//                // Give the PagerSlidingTabStrip the ViewPager
//                PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
//                tabsStrip.setIndicatorColorResource(R.color.white);
//                tabsStrip.setDividerColorResource(R.color.white);
//                tabsStrip.setViewPager(viewPager);
//                tabsStrip.setShouldExpand(true);

            }
        }
        else {
            // Launching new Activity on selecting single List Item
            Intent intent = new Intent(getApplicationContext(), DepartmentsActivity.class);
            startActivity(intent);
        }
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

        return MenuHelper.onOptionsItemSelected(getApplicationContext(), item, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        GoogleIntegrationManager.onActivityResult(requestCode, resultCode, data, this);
    }

    //Starts the getUsername asynctask
    protected void getUsername(){
        GoogleIntegrationManager.getUsername(this);
    }


}