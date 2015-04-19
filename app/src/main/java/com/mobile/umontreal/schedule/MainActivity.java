package com.mobile.umontreal.schedule;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.astuetz.PagerSlidingTabStrip;
import com.mobile.umontreal.schedule.db.UDMDatabaseManager;
import com.mobile.umontreal.schedule.googleI.GoogleIntegrationManager;
import com.mobile.umontreal.schedule.misc.ConnectionDetector;
import com.mobile.umontreal.schedule.misc.IterableCursor;
import com.mobile.umontreal.schedule.misc.MenuHelper;
import com.mobile.umontreal.schedule.objects.MyCourse;
import com.mobile.umontreal.schedule.schedule.ScheduleCursorAdapter;

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
    private UDMDatabaseManager dataBase;
    private SQLiteDatabase dbr;
    private SQLiteDatabase dbw;

    /** Called when the activity is first created. */
    private ArrayList<ArrayList<MyCourse>> myList;
    private Context mContext;
    private Vector<View> pages;
    private String[] tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connection DataBase
        dataBase = new UDMDatabaseManager(this);
        dbw = dataBase.getWritableDatabase();
        dbr = dataBase.getReadableDatabase();

        myList = new ArrayList<ArrayList<MyCourse>>();

        mContext = this;
        pages = new Vector<View>();

        if (dataBase.isEmpty(dbr)) {

            // Launching new Activity on selecting single List Item
            Intent intent = new Intent(getApplicationContext(), DepartmentsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        Cursor session = dataBase.getCourses(new String[]{
                        UDMDatabaseManager.C_TITRE,
                        UDMDatabaseManager.C_TRIMESTRE
                },
                null,                                   // The columns for the WHERE clause
                null,                                   // The values for the WHERE clause
                UDMDatabaseManager.C_TRIMESTRE,         // Group the rows
                null,                                   // Filter by row groups
                UDMDatabaseManager.C_ID + " DESC",      // The sort order
                null,
                dbr);                                   // The limit);


        // Build a the tabs
        int tabsNumber = session.getCount();
        int i = 0;

        if (tabsNumber > 0) {

            tabs = new String[tabsNumber];

            for (Cursor data : new IterableCursor(session)) {

                ArrayList<MyCourse> courseList = new ArrayList<MyCourse>();

                String sessionName = data.getString(data.getColumnIndex(UDMDatabaseManager.C_TRIMESTRE));
                tabs[i++] = sessionName;

                pages.add(new ListView(mContext));

                Cursor c = dataBase.getCourses(new String[]{
                                UDMDatabaseManager.C_ID,
                                UDMDatabaseManager.C_SIGLE,
                                UDMDatabaseManager.C_COURSNUM,
                                UDMDatabaseManager.C_SECTION,
                                UDMDatabaseManager.C_TITRE,
                                UDMDatabaseManager.C_TRIMESTRE,
                                UDMDatabaseManager.C_STATUS,
                                UDMDatabaseManager.C_ABANDON
                        },
                        UDMDatabaseManager.C_TRIMESTRE + " LIKE ?" , // The columns for the WHERE clause
                        new String[]{ sessionName },                // The values for the WHERE clause
                        null,                                       // Group the rows
                        null,                                       // Filter by row groups
                        UDMDatabaseManager.C_TITRE + " ASC",       // The sort order
                        null, dbr);

                c.moveToFirst();



                do {

                    // Add the course in the list
                    MyCourse course = new MyCourse();
                    course.set_id(c.getInt(c.getColumnIndex(UDMDatabaseManager.C_ID)));
                    course.setSigle(c.getString(c.getColumnIndex(UDMDatabaseManager.C_SIGLE)));
                    course.setCourseNumber(c.getInt(c.getColumnIndex(UDMDatabaseManager.C_COURSNUM)));
                    course.setSection(c.getString(c.getColumnIndex(UDMDatabaseManager.C_SECTION)));
                    course.setTitle(c.getString(c.getColumnIndex(UDMDatabaseManager.C_TITRE)));
                    course.setSession(c.getString(c.getColumnIndex(UDMDatabaseManager.C_TRIMESTRE)));
                    course.setStatus(c.getString(c.getColumnIndex(UDMDatabaseManager.C_STATUS)));
                    course.setDrop(c.getString(c.getColumnIndex(UDMDatabaseManager.C_ABANDON)));

                    courseList.add(course);

                } while (c.moveToNext());

                myList.add(courseList);

                c.close();
            }

            session.close();

            // Get the ViewPager and set it's PagerAdapter so that it can display items
            ViewPager viewPager = (ViewPager) findViewById(R.id.schedule_viewer);
            viewPager.setAdapter(new ScheduleCursorAdapter(getSupportFragmentManager(), tabs, myList));
//
            // Give the PagerSlidingTabStrip the ViewPager
            PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
            tabsStrip.setIndicatorColorResource(R.color.white);
            tabsStrip.setDividerColorResource(R.color.white);
            tabsStrip.setViewPager(viewPager);
            tabsStrip.setShouldExpand(true);

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